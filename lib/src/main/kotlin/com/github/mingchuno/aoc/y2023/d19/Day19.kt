package com.github.mingchuno.aoc.y2023.d19

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day19 : Problem<Long> {
    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val (workflows, parts) = parseInputs(inputs)
        return parts
            .filter { part -> part.passWorkflows(workflows) == EndState.ACCEPTED }
            .sumOf { it.values.sum() }
            .toLong()
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val (workflows, _) = parseInputs(inputs)
        return revereRulesTracking(workflows).sumOf { it.computeRange() }
    }
}

private fun revereRulesTracking(workflows: Workflows): List<List<BackTrackingRule>> {
    val reversedWorkflows = workflows.mapValues { (k, v) -> v.copy(rules = v.rules.reversed()) }
    val backtrackingIndex = findKeyAndHitIndex(reversedWorkflows)
    val results = mutableListOf<List<BackTrackingRule>>()
    reversedWorkflows.forEach { (key, workflow) ->
        workflow.rules.forEachIndexed { idx, rule ->
            if (rule.target == "A") {
                results.add(
                    buildBackTrackingList(
                        reversedWorkflows,
                        backtrackingIndex,
                        currentKey = key,
                        hitIdx = idx,
                    )
                )
            }
        }
    }
    return results
}

private val MIN = 1
private val MAX = 4000

private fun List<BackTrackingRule>.computeRange(): Long {
    val initRange =
        mutableMapOf(
            "x" to MIN..MAX,
            "m" to MIN..MAX,
            "a" to MIN..MAX,
            "s" to MIN..MAX,
        )
    val finalRange =
        this.fold(initRange) { prevRange, brule ->
            val rule = brule.rule
            val truthful = brule.truthful
            when (rule) {
                is DefaultRule -> prevRange
                is Rule -> {
                    val oldRange = prevRange[rule.cat]!!
                    when (rule.operator) {
                        Operator.gt -> {
                            if (truthful) {
                                if (rule.value > oldRange.first) {
                                    prevRange[rule.cat] = rule.value + 1..oldRange.last
                                }
                            } else {
                                if (oldRange.last >= rule.value) {
                                    prevRange[rule.cat] = oldRange.first..rule.value
                                }
                            }
                        }
                        Operator.lt -> {
                            if (truthful) {
                                if (oldRange.last >= rule.value) {
                                    prevRange[rule.cat] = oldRange.first ..< rule.value
                                }
                            } else {
                                if (rule.value >= oldRange.first) {
                                    prevRange[rule.cat] = rule.value..oldRange.last
                                }
                            }
                        }
                    }
                    prevRange
                }
            }
        }
    // compute all possible distinct combinations
    return finalRange.values.map { it.count().toLong() }.reduce { acc, i -> acc * i }
}

private fun findKeyAndHitIndex(
    workflows: Workflows, // reversed!
): Map<String, List<Pair<String, Int>>> {
    return workflows.keys.associateWith { currentKey ->
        (workflows.flatMap { (key, workflow) ->
            workflow.rules
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
    hitIdx: Int,
): List<BackTrackingRule> {
    val currentWorkflow = workflows[currentKey]!!
    val size = currentWorkflow.rules.size
    val xs = (hitIdx ..< size).map { BackTrackingRule(currentWorkflow.rules[it], it == hitIdx) }
    val ys =
        if (currentKey == "in") listOf()
        else
            (backtrackingIndex[currentKey] ?: listOf()).flatMap { (nextKey, nextIdx) ->
                buildBackTrackingList(workflows, backtrackingIndex, nextKey, nextIdx)
            }
    return xs + ys
}

private fun Part.passWorkflows(workflows: Workflows, nextWorkflow: String = "in"): EndState {
    val workflow = workflows[nextWorkflow]!!
    var nextTarget: String? = null
    loop@ for (rule in workflow.rules) {
        val pass =
            when (rule) {
                is Rule ->
                    when (rule.operator) {
                        Operator.gt -> this[rule.cat]!! > rule.value
                        Operator.lt -> this[rule.cat]!! < rule.value
                    }
                is DefaultRule -> true
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
