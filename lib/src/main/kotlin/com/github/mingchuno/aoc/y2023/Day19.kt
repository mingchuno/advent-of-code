package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day19 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val (workflows, parts) = parseInputs(inputs)
        return parts
            .filter { part -> part.passWorkflows(workflows) == EndState.ACCEPTED }
            .sumOf { it.score() }
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        TODO("Not yet implemented")
    }
}

private typealias Workflows = Map<String, Workflow>

private fun MPart.passWorkflows(workflows: Workflows, nextWorkflow: String = "in"): EndState {
    val w = workflows[nextWorkflow]!!
    var nextTarget: String? = null
    loop@ for (rule in w.rules) {
        val pass =
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
        if (pass) {
            nextTarget = rule.target
            break@loop
        }
    }
    nextTarget = nextTarget ?: w.defaultTarget
    return when (nextTarget) {
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
private val ruleRgex = """([xmas])([>|<])(\d+):(.+)""".toRegex()

private fun String.parseWorkflow(): Workflow {
    val (key, workflowsStr) = workflowRegex.find(this)?.groupValues?.drop(1)!!
    val ws = workflowsStr.split(",")
    val defaultTarget = ws.last()
    val rules =
        ws.dropLast(1).map {
            val (cat, operator, value, target) = ruleRgex.find(it)?.groupValues?.drop(1)!!
            val op =
                when (operator) {
                    ">" -> Operator.gt
                    "<" -> Operator.lt
                    else -> throw Exception("Should not happens")
                }
            Rule(cat, op, value.toInt(), target)
        }

    return Workflow(key = key, rules = rules, defaultTarget = defaultTarget)
}

private val partRegexp = """\{x=(\d+),m=(\d+),a=(\d+),s=(\d+)\}""".toRegex()

private fun String.parsePart(): MPart {
    val (x, m, a, s) = partRegexp.find(this)?.groupValues?.drop(1)?.map { it.toInt() }!!
    return MPart(x = x, m = m, a = a, s = s)
}

private data class MPart(val x: Int, val m: Int, val a: Int, val s: Int) {
    fun score(): Int = x + m + a + s
}

private data class Rule(
    val cat: String,
    val operator: Operator,
    val value: Int,
    val target: String
)

private data class Workflow(val key: String, val rules: List<Rule>, val defaultTarget: String)

private enum class EndState {
    ACCEPTED,
    REJECTED
}

private enum class Operator {
    gt,
    lt
}
