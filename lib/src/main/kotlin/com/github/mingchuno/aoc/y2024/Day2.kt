package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.parseInts
import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlin.math.abs

object Day2 : Problem<Int> {

    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return inputs.map { if (part1IsSafe(it.parseInts())) 1 else 0 }.sum()
    }

    private fun part1IsSafe(report: List<Int>): Boolean {
        println("Running part1IsSafe for:$report")
        val stack = ArrayDeque<Int>()
        val isDesc = report.first() > report.last()
        report.forEach { current ->
            val shouldPush =
                stack.firstOrNull()?.let { prev ->
                    val isMonotonic = if (isDesc) prev > current else current > prev
                    val diff = abs(prev - current)
                    val isLevelDiffOk = diff in 1..3
                    isMonotonic && isLevelDiffOk
                } ?: true
            if (shouldPush) {
                stack.addFirst(current)
            }
        }
        return stack.size == report.size
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return inputs.map { if (part2IsSafe(it.parseInts())) 1 else 0 }.sum()
    }

    private fun part2IsSafe(report: List<Int>): Boolean {
        val isFullySafe = part1IsSafe(report)
        if (isFullySafe) return true
        for (i in report.indices) {
            val isSafe = part1IsSafe(report.filterIndexed { index, _ -> index != i })
            if (isSafe) {
                return true
            }
        }
        return false
    }
}
