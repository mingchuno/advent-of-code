package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.readFileFromResource

object Day1 {
    fun compute(inputFile: String): Int {
        val list = inputFile.readFileFromResource()
        return list.sumOf { it.findFirstAndLastDigitPart1().toInt() }
    }

    private fun String.findFirstAndLastDigitPart1(): String {
        val nums = filter { char -> char.isDigit() }
        return "${nums.first()}${nums.last()}"
    }

    fun computePart2(inputFile: String): Int {
        val list = inputFile.readFileFromResource()
        return list.sumOf { it.findFirstAndLastDigitPart2().toInt() }
    }

    private fun String.findFirstAndLastDigitPart2(): String {
        val digits =
            digitMap
                .flatMap { (k, v) -> listOf(Pair(indexOf(k), v), Pair(lastIndexOf(k), v)) }
                .filter { (idx, _) -> idx >= 0 }
        return digits.minBy { (idx, _) -> idx }.second + digits.maxBy { (idx, _) -> idx }.second
    }

    private val digitMap =
        mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
            "1" to "1",
            "2" to "2",
            "3" to "3",
            "4" to "4",
            "5" to "5",
            "6" to "6",
            "7" to "7",
            "8" to "8",
            "9" to "9",
        )
}
