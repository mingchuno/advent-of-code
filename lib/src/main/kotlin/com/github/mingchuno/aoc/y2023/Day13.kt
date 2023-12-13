package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.transpose

object Day13 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val patterns = parsePattern(inputs)
        return patterns
            .mapNotNull { pattern ->
                pattern.findReflectionOnRow()?.let { it * 100 }
                    ?: pattern.map { it.toList() }.transpose().findReflectionOnRow()
            }
            .sum()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        TODO("Not yet implemented")
    }

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

    @JvmName("findReflectionOnRowString")
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

    @JvmName("findReflectionOnRowChars")
    private fun List<List<Char>>.findReflectionOnRow(): Int? {
        return this.map { it.joinToString("") }.findReflectionOnRow()
    }
}
