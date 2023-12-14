package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.transpose

object Day14 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return inputs
            .map { it.toList() }
            .transpose()
            .sumOf { column -> PlatformColumn(column).computeLoad() }
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        TODO("Not yet implemented")
    }
}

class PlatformColumn(private val column: List<Char>) {
    private val size = column.size

    fun computeLoad(): Int {
        return computeLoad(0, -1, 0)
    }

    private fun computeLoad(currentIdx: Int, rockIdx: Int, currentRockCount: Int): Int {
        if (currentIdx == size) {
            return 0
        }
        return when (column[currentIdx]) {
            '.' -> computeLoad(currentIdx + 1, rockIdx, currentRockCount)
            'O' ->
                (size - rockIdx - currentRockCount - 1) +
                    computeLoad(currentIdx + 1, rockIdx, currentRockCount + 1)
            '#' -> computeLoad(currentIdx + 1, currentIdx, currentRockCount = 0)
            else -> throw Exception("This should not happen")
        }
    }
}
