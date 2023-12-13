package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.transpose
import kotlin.math.abs

object Day11 {
    /** Part 1 is just special case of Part 2 */
    fun computePart1(inputFile: String): Long {
        return computePart2(inputFile)
    }

    private fun List<List<Char>>.findGalaxies(): List<Pair<Int, Int>> {
        val galaxies = mutableListOf<Pair<Int, Int>>()
        val y = this.size
        val x = this.first().size
        for (i in 0 ..< y) {
            for (j in 0 ..< x) {
                if (this[i][j] == GALAXY) {
                    galaxies.add(j to i)
                }
            }
        }
        return galaxies
    }

    fun computePart2(inputFile: String, expansionFactor: Int = 2 /* part 1 = 2*/): Long {
        val inputs = inputFile.readFileFromResource().map { it.toList() }
        val yIndexes = inputs.findExpandIndex()
        val xIndexes = inputs.transpose().findExpandIndex()
        return inputs.findGalaxies().findShortestDistance(xIndexes, yIndexes, expansionFactor).sum()
    }

    private fun List<Pair<Int, Int>>.findShortestDistance(
        xIdx: List<Int>,
        yIdx: List<Int>,
        expansionFactor: Int
    ): List<Long> {
        val distance = mutableListOf<Long>()
        for (i in indices) {
            for (j in 0 ..< i) {
                val (ix, iy) = this[i]
                val (jx, jy) = this[j]
                val xExpansionCount =
                    xIdx.count { if (ix > jx) it in (jx + 1) ..< ix else it in (ix + 1) ..< jx }
                val yExpansionCount =
                    yIdx.count { if (iy > jy) it in (jy + 1) ..< iy else it in (iy + 1) ..< jy }
                val d =
                    abs(iy - jy) +
                        abs(ix - jx) +
                        (xExpansionCount + yExpansionCount) * (expansionFactor.toLong() - 1)
                distance.add(d)
            }
        }
        return distance
    }

    private fun List<List<Char>>.findExpandIndex(): List<Int> = mapIndexedNotNull { index, chars ->
        if (chars.all { it == EMPTY }) index else null
    }

    private const val EMPTY = '.'
    private const val GALAXY = '#'
}
