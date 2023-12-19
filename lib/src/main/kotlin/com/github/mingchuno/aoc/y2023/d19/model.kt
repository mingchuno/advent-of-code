package com.github.mingchuno.aoc.y2023.d19

typealias Workflows = Map<String, Workflow>

data class Workflow(val key: String, val rules: List<RuleBase>)

typealias Part = Map<String, Int>

data class BackTrackingRule(val rule: RuleBase, var truthful: Boolean)

sealed interface RuleBase {
    val target: String
}

data class Rule(
    val cat: String,
    val operator: Operator,
    val value: Int,
    override val target: String
) : RuleBase

data class DefaultRule(override val target: String) : RuleBase

enum class EndState {
    ACCEPTED,
    REJECTED
}

enum class Operator {
    gt,
    lt
}
