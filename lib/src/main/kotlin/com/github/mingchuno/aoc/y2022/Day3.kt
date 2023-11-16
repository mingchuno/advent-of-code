package com.github.mingchuno.aoc.y2022

import com.github.mingchuno.aoc.utils.readFileFromResource

object Day3 {
    fun compute(inputFile: String): Int {
        return inputFile.readFileFromResource().sumOf { lineScore(it) }
    }

    private fun lineScore(line: String): Int {
        val (head, tail) = line.chunked(line.length / 2).map { s -> s.toSet() }
        val error = head.intersect(tail)
        if (error.isEmpty()) return 0
        val char = error.first()
        return char.score()
    }

    private fun Char.score(): Int {
        return if (this.isLowerCase()) {
            this.code - 96
        } else {
            this.code - 38
        }
    }

    fun computePart2(inputFile: String): Int {
        return inputFile.readFileFromResource().chunked(3).sumOf { group ->
            val (a, b, c) = group.map { it.toSet() }
            a.intersect(b).intersect(c).first().score()
        }
    }
}
