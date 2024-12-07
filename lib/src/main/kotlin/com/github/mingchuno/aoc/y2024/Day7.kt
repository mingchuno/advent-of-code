package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.*

object Day7 : Problem<Long> {

    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        return inputs.sumOf { input ->
            val (testValue, nums) = parseInput(input)
            val possibility =
                computePossibility(
                    part = 1,
                    testValueTarget = testValue,
                    currentValue = nums.first().toLong(),
                    nums.drop(1),
                )
            if (possibility != 0) testValue else 0L
        }
    }

    private fun parseInput(row: String): Pair<Long, List<Int>> {
        val (testValue, nums) = row.split(":")
        return Pair(testValue.toLong(), nums.parseInts())
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        return inputs.sumOf { input ->
            val (testValue, nums) = parseInput(input)
            val possibility =
                computePossibility(
                    part = 2,
                    testValueTarget = testValue,
                    currentValue = nums.first().toLong(),
                    nums.drop(1),
                )
            if (possibility != 0) testValue else 0L
        }
    }

    private fun computePossibility(
        part: Int,
        testValueTarget: Long,
        currentValue: Long,
        nums: List<Int>,
    ): Int {
        return if (nums.isEmpty()) {
            if (testValueTarget == currentValue) 1 else 0
        } else {
            val head = nums.first()
            val tail = nums.drop(1)
            computePossibility(part, testValueTarget, currentValue + head, tail) +
                computePossibility(part, testValueTarget, currentValue * head, tail) +
                if (part == 2)
                    computePossibility(
                        part,
                        testValueTarget,
                        concatNumber(currentValue, head),
                        tail,
                    )
                else 0
        }
    }

    private fun concatNumber(a: Number, b: Number): Long = (a.toString() + b.toString()).toLong()
}
