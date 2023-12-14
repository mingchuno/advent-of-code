package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.transpose
import kotlin.math.abs

object Day13 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val patterns = parsePattern(inputs)
        return patterns
            .mapNotNull { pattern ->
                pattern.findReflectionOnRow()?.let { it * 100 }
                    ?: pattern.transpose().findReflectionOnRow()
            }
            .sum()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val patterns = parsePattern(inputs)
        return patterns
            .mapNotNull { pattern ->
                pattern.findReflectionOnRowWithSmudge()?.let { it * 100 }
                    ?: pattern.transpose().findReflectionOnRowWithSmudge()
            }
            .sum()
    }

    private fun List<String>.transpose(): List<List<Char>> = map { it.toList() }.transpose()

    private fun parsePattern(inputs: List<String>): List<List<String>> {
        val results = mutableListOf<List<String>>()
        var tempList = mutableListOf<String>()
        for (input in inputs) {
            if (input.isNotEmpty()) {
                tempList.add(input)
            } else {
                results.add(tempList)
                tempList = mutableListOf()
            }
        }
        results.add(tempList)
        return results
    }

    private fun List<String>.findReflectionOnRow(): Int? {
        outer@ for (i in 1 ..< this.size) {
            val range = if (i > size / 2) i ..< size /* right half */ else 0 ..< i /* left half */
            for (j in range) {
                if (this[j] != this[2 * i - 1 - j]) {
                    continue@outer
                }
            }
            // This is the one!
            return i
        }
        return null
    }

    private fun List<String>.findReflectionOnRowWithSmudge(): Int? {
        outer@ for (i in 1 ..< this.size) {
            var fixed = false
            val range = if (i > size / 2) i ..< size /* right half */ else 0 ..< i /* left half */
            inner@ for (j in range) {
                val a = this[j]
                val b = this[2 * i - 1 - j]
                val hardSame = a == b
                if (hardSame) {
                    continue@inner
                }
                val softSame = !fixed && a.softEqual(b)
                if (softSame) {
                    fixed = true
                }
                if (!softSame) {
                    continue@outer
                }
            }
            if (!fixed) {
                continue@outer
            }
            // This is the one!
            return i
        }
        return null
    }

    private fun String.softEqual(that: String): Boolean {
        // 11 is the code point different of '.' and '#'
        return this.zip(that).sumOf { (a, b) -> abs(a - b) } == 11
    }

    @JvmName("findReflectionOnRowCharsWithSmudge")
    private fun List<List<Char>>.findReflectionOnRowWithSmudge(): Int? {
        return this.map { it.joinToString("") }.findReflectionOnRowWithSmudge()
    }

    @JvmName("findReflectionOnRowChars")
    private fun List<List<Char>>.findReflectionOnRow(): Int? {
        return this.map { it.joinToString("") }.findReflectionOnRow()
    }
}
