package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlin.math.abs

object Day11 {
    fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().map { it.toList() }
        val universe = inputs.expand().transpose().expand().transpose()
        return universe.findGalaxies().findShortestDistance().sum()
    }

    private fun <T> List<List<T>>.transpose(): List<List<T>> {
        val y = this.size
        val x = this.first().size
        val result = MutableList(x) { MutableList(y) { this.first().first() } }
        for (i in 0 ..< y) {
            for (j in 0 ..< x) {
                result[j][i] = this[i][j]
            }
        }
        return result
    }

    private fun List<List<Char>>.expand(): List<List<Char>> = flatMap { row ->
        if (row.all { it == EMPTY }) listOf(row, row) else listOf(row)
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

    private fun List<Pair<Int, Int>>.findShortestDistance(): List<Int> {
        val distance = mutableListOf<Int>()
        for (i in indices) {
            for (j in 0 ..< i) {
                val (ix, iy) = this[i]
                val (jx, jy) = this[j]
                distance.add(abs(iy - jy) + abs(ix - jx))
            }
        }
        return distance
    }

    fun computePart2(inputFile: String, expansionFactor: Int = 2 /* part 1 = 2*/): Long {
        val inputs = inputFile.readFileFromResource().map { it.toList() }
        val yIndexes = inputs.findExpandIndex()
        val xIndexes = inputs.transpose().findExpandIndex()
        return inputs
            .findGalaxies()
            .findShortestDistance2(xIndexes, yIndexes, expansionFactor)
            .sum()
    }

    private fun List<Pair<Int, Int>>.findShortestDistance2(
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
                distance.add(
                    abs(iy - jy) +
                        abs(ix - jx) +
                        (xExpansionCount + yExpansionCount) * (expansionFactor.toLong() - 1)
                )
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
