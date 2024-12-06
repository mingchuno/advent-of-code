package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.parseInts
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day5 : Problem<Int> {

    override fun computePart1(inputFile: String): Int {
        val input = parseInputs(inputFile.readFileFromResource())
        var sum = 0
        input.updates.forEach { update ->
            val valid = input.isUpdateValid(update)
            if (valid) {
                sum += update[update.size / 2]
            }
        }
        return sum
    }

    override fun computePart2(inputFile: String): Int {
        val input = parseInputs(inputFile.readFileFromResource())
        var sum = 0
        input.updates.forEach { update ->
            val valid = input.isUpdateValid(update)
            if (!valid) {
                sum += input.sortInvalidAndGetScore(update)
            }
        }
        return sum
    }

    private fun parseInputs(inputs: List<String>): Input {
        val divider = inputs.indexOfFirst { it.isEmpty() }
        val rules = mutableMapOf<Int, MutableSet<Int>>()
        (0..<divider).forEach { i ->
            val (a, b) = inputs[i].parseRule()
            rules.compute(a) { _, s ->
                if (s == null) {
                    mutableSetOf(b)
                } else {
                    s.add(b)
                    s
                }
            }
        }
        val updates = (divider + 1..<inputs.size).map { i -> inputs[i].parseInts() }
        return Input(rules, updates)
    }

    private fun String.parseRule(): List<Int> {
        return this.split("|").map { it.toInt() }
    }
}

private data class Input(val rules: Map<Int, Set<Int>>, val updates: List<List<Int>>) {
    fun isUpdateValid(update: List<Int>): Boolean {
        var valid = true
        update.forEachIndexed { index, i ->
            val validPageAfter = rules.getOrDefault(i, setOf())
            for (j in index + 1..<update.size) {
                if (!validPageAfter.contains(update[j])) {
                    valid = false
                    break
                }
            }
        }
        return valid
    }

    fun sortInvalidAndGetScore(update: List<Int>): Int {
        val updateSet = update.toSet()
        return update
            .sortedWith { a, b ->
                val scoreA = rules.getOrDefault(a, setOf()).intersect(updateSet).size
                val scoreB = rules.getOrDefault(b, setOf()).intersect(updateSet).size
                scoreA - scoreB
            }[update.size / 2]
    }
}
