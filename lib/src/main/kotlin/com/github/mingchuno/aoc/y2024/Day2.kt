package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.parseInts
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day2 : Problem<Int> {

    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return inputs.map { if (part1IsSafe(it.parseInts())) 1 else 0 }.sum()
    }

    private fun part1IsSafe(report: List<Int>): Boolean {
        val diff = report.zipWithNext { a, b -> a - b }
        val isSafe = diff.all { it in 1..3 } || diff.all { it in -3..-1 }
        return isSafe
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
