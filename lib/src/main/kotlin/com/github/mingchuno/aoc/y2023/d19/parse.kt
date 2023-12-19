package com.github.mingchuno.aoc.y2023.d19

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

private fun String.parsePart(): Part {
    val (x, m, a, s) = partRegexp.find(this)?.groupValues?.drop(1)?.map { it.toInt() }!!
    return mapOf("x" to x, "m" to m, "a" to a, "s" to s)
}

fun parseInputs(inputs: List<String>): Pair<Workflows, List<Part>> {
    val divider = inputs.indexOfFirst { it.isEmpty() }
    val workflows = (0 ..< divider).map { i -> inputs[i].parseWorkflow() }
    val parts = (divider + 1 ..< inputs.size).map { i -> inputs[i].parsePart() }
    return workflows.associateBy { it.key } to parts
}
