package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.parseLongs
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day9 : Problem<Long> {
    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        return inputs.map { it.parseLongs() }.sumOf { predictNext(it) }
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        return inputs.map { it.parseLongs() }.sumOf { predictNext(it.reversed()) }
    }

    private fun predictNext(ints: List<Long>): Long {
        if (ints.all { it == 0L }) {
            return 0
        } else {
            val nextSeq = ints.zipWithNext().map { (a, b) -> b - a }
            return ints.last() + predictNext(nextSeq)
        }
    }
}
