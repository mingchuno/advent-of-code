package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day19 : Problem<Long> {
    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val (workflows, parts) = parseInputs(inputs)
        return parts
            .filter { part -> part.passWorkflows(workflows) == EndState.ACCEPTED }
            .sumOf { it.score() }
            .toLong()
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val (workflows, _) = parseInputs(inputs)
        val ruless = revereRulesTracking(workflows)
        return ruless.sumOf { it.computeRange() }
    }
}

private typealias Workflows = Map<String, Workflow>

private data class BackTrackingRule(val rule: RuleBase, var truthful: Boolean)

private fun revereRulesTracking(workflows: Workflows): List<List<BackTrackingRule>> {
    val reversedWorkflows = workflows.mapValues { (k, v) -> v.copy(rules = v.rules.reversed()) }
    val backtrackingIndex = findKeyAndHitIndex(reversedWorkflows)

    //    reversedWorkflows.onEach { println(it) }
    //    backtrackingIndex.onEach { println(it) }

    val results = mutableListOf<List<BackTrackingRule>>()
    reversedWorkflows.forEach { (key, workflow) ->
        workflow.rules.forEachIndexed { idx, rule ->
            if (rule.target == "A") {
                results.add(
                    buildBackTrackingList(
                            reversedWorkflows,
                            backtrackingIndex,
                            currentKey = key,
                            hitIdx = idx
                        )
                        // First item in the list (last item in the rules) should be true
                        .also { it[0].truthful = true }
                )
            }
        }
    }
    results.onEach { println(it) }
    return results
}

private val MIN = 1
private val MAX = 4000

private fun initRange(): MutableMap<String, IntRange> =
    mutableMapOf(
        "x" to MIN..MAX,
        "m" to MIN..MAX,
        "a" to MIN..MAX,
        "s" to MIN..MAX,
    )

private fun List<BackTrackingRule>.computeRange(): Long {
    val finalRange =
        this.fold(initRange()) { prevRange, brule ->
            val rule = brule.rule
            val truthful = brule.truthful
            when (rule) {
                is DefaultRule -> prevRange
                is Rule -> {
                    val oldRange = prevRange[rule.cat]!!
                    when (rule.operator) {
                        Operator.gt -> {
                            if (truthful) {
                                if (rule.value < oldRange.first) {
                                    prevRange
                                } else if (oldRange.last < rule.value) {
                                    prevRange[rule.cat] = MAX..MIN
                                    prevRange
                                } else {
                                    prevRange[rule.cat] = rule.value..oldRange.last
                                    prevRange
                                }
                            } else {
                                if (rule.value < oldRange.first) {
                                    prevRange[rule.cat] = MAX..MIN
                                    prevRange
                                } else if (oldRange.last < rule.value) {
                                    prevRange
                                } else {
                                    prevRange[rule.cat] = oldRange.first..rule.value
                                    prevRange
                                }
                            }
                        }
                        Operator.lt -> {
                            if (truthful) {
                                if (rule.value < oldRange.first) {
                                    prevRange[rule.cat] = MAX..MIN
                                    prevRange
                                } else if (oldRange.last < rule.value) {
                                    prevRange
                                } else {
                                    prevRange[rule.cat] = oldRange.first..rule.value
                                    prevRange
                                }
                            } else {

                                if (rule.value < oldRange.first) {
                                    prevRange
                                } else if (oldRange.last < rule.value) {
                                    prevRange[rule.cat] = MAX..MIN
                                    prevRange
                                } else {
                                    prevRange[rule.cat] = rule.value..oldRange.last
                                    prevRange
                                }
                            }
                        }
                    }
                }
            }
        }
    return finalRange.values.map { it.count().toLong() }.reduce { acc, i -> acc * i }
}

private fun findKeyAndHitIndex(
    workflows: Workflows, // reversed!
): Map<String, List<Pair<String, Int>>> {
    return workflows.keys.associateWith { currentKey ->
        (workflows.flatMap { (key, wf) ->
            wf.rules
                .withIndex()
                .filter { (_, rule) -> rule.target == currentKey }
                .map { (idx, _) -> key to idx }
        })
    }
}

private fun buildBackTrackingList(
    workflows: Workflows, // reversed!
    backtrackingIndex: Map<String, List<Pair<String, Int>>>,
    currentKey: String,
    hitIdx: Int
): List<BackTrackingRule> {
    val currentWorkflow = workflows[currentKey]!!
    val size = currentWorkflow.rules.size
    val xs = (hitIdx ..< size).map { BackTrackingRule(currentWorkflow.rules[it], false) }
    val ys =
        if (currentKey == "in") listOf()
        else
            (backtrackingIndex[currentKey] ?: listOf())
                .map { (nextKey, nextIdx) ->
                    buildBackTrackingList(workflows, backtrackingIndex, nextKey, nextIdx)
                }
                .flatten()
    return xs + ys
}

private fun MPart.passWorkflows(workflows: Workflows, nextWorkflow: String = "in"): EndState {
    val w = workflows[nextWorkflow]!!
    var nextTarget: String? = null
    loop@ for (rule in w.rules) {
        val pass =
            when (rule) {
                is Rule -> {
                    when (rule.cat to rule.operator) {
                        "x" to Operator.gt -> this.x > rule.value
                        "x" to Operator.lt -> this.x < rule.value
                        "m" to Operator.gt -> this.m > rule.value
                        "m" to Operator.lt -> this.m < rule.value
                        "a" to Operator.gt -> this.a > rule.value
                        "a" to Operator.lt -> this.a < rule.value
                        "s" to Operator.gt -> this.s > rule.value
                        "s" to Operator.lt -> this.s < rule.value
                        else -> false
                    }
                }
                is DefaultRule -> {
                    true
                }
            }
        if (pass) {
            nextTarget = rule.target
            break@loop
        }
    }
    return when (nextTarget) {
        null -> throw Exception("Should not happens")
        "A" -> EndState.ACCEPTED
        "R" -> EndState.REJECTED
        else -> this.passWorkflows(workflows, nextTarget)
    }
}

private fun parseInputs(inputs: List<String>): Pair<Workflows, List<MPart>> {
    val divider = inputs.indexOfFirst { it.isEmpty() }
    val workflows = (0 ..< divider).map { i -> inputs[i].parseWorkflow() }
    val parts = (divider + 1 ..< inputs.size).map { i -> inputs[i].parsePart() }
    return workflows.toWorkflowMap() to parts
}

private fun List<Workflow>.toWorkflowMap(): Workflows = associateBy { it.key }

private val workflowRegex = """(.*)\{(.*)\}""".toRegex()
private val ruleRegex = """([xmas])([>|<])(\d+):(.+)""".toRegex()

private fun String.parseWorkflow(): Workflow {
    val (key, workflowsStr) = workflowRegex.find(this)?.groupValues?.drop(1)!!
    val ws = workflowsStr.split(",")
    val defaultTarget = ws.last()
    val rules =
        ws.dropLast(1).map {
            val (cat, operator, value, target) = ruleRegex.find(it)?.groupValues?.drop(1)!!
            val op =
                when (operator) {
                    ">" -> Operator.gt
                    "<" -> Operator.lt
                    else -> throw Exception("Should not happens")
                }
            Rule(cat, op, value.toInt(), target)
        }

    return Workflow(key = key, rules = rules + DefaultRule(defaultTarget))
}

private val partRegexp = """\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)\}""".toRegex()

private fun String.parsePart(): MPart {
    val (x, m, a, s) = partRegexp.find(this)?.groupValues?.drop(1)?.map { it.toInt() }!!
    return MPart(x = x, m = m, a = a, s = s)
}

private data class MPart(val x: Int, val m: Int, val a: Int, val s: Int) {
    fun score(): Int = x + m + a + s
}

sealed interface RuleBase {
    val target: String
}

private data class Rule(
    val cat: String,
    val operator: Operator,
    val value: Int,
    override val target: String
) : RuleBase

private data class DefaultRule(override val target: String) : RuleBase

private data class Workflow(val key: String, val rules: List<RuleBase>)

private enum class EndState {
    ACCEPTED,
    REJECTED
}

private enum class Operator {
    gt,
    lt
}
