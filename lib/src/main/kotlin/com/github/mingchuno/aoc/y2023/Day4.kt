package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlin.math.pow

object Day4 {
    fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return inputs.sumOf {
            val commonCount = it.findWinningCount()
            if (commonCount > 0) {
                2.0.pow((commonCount - 1).toDouble()).toInt()
            } else 0
        }
    }

    private fun String.findWinningCount(): Int {
        val (_, numbers) = this.split(":")
        val (winning, my) = numbers.split("|")
        val winningSet = winning.parseNumbers()
        val mySet = my.parseNumbers()
        return winningSet.intersect(mySet).size
    }

    private fun String.parseNumbers(): Set<Int> =
        split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()

    fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        // initially you will have 1 card each
        val cardCount = IntArray(inputs.size) { 1 }.toMutableList()
        inputs.forEachIndexed { index, s ->
            val winningCount = s.findWinningCount()
            val thisCurrentCount = cardCount[index]
            IntRange(index + 1, index + winningCount).forEach { idx ->
                cardCount[idx] += thisCurrentCount
            }
        }
        return cardCount.sum()
    }
}
