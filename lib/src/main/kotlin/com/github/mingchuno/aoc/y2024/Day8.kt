package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.*

object Day8 : Problem<Int> {

    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        val grid = parseGrid(inputs)
        val c = Compute(grid, inputs[0].size, inputs.size)
        return c.computePart1()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        val grid = parseGrid(inputs)
        val c = Compute(grid, inputs[0].size, inputs.size)
        return c.computePart2()
    }

    private fun parseGrid(inputs: List<List<Char>>): Map<Char, List<Coord>> {
        val map = mutableMapOf<Char, MutableList<Coord>>()
        inputs.forEachIndexed { y, rows ->
            rows.forEachIndexed { x, c ->
                if (c != '.') {
                    map.putIfAbsent(c, mutableListOf())
                    map[c]?.add(Coord(x, y))
                }
            }
        }
        return map
    }

    class Compute(
        private val grid: Map<Char, List<Coord>>,
        private val xSize: Int,
        private val ySize: Int,
    ) {
        fun computePart1(): Int {
            return computeGeneric(computeAntinodePart1)
        }

        fun computePart2(): Int {
            return computeGeneric(computeAntinodePart2)
        }

        private fun computeGeneric(fn: (Coord, Coord) -> List<Coord>): Int {
            val antinodes = mutableSetOf<Coord>()
            grid.forEach { (_, coords) ->
                for (i in coords.indices) {
                    for (j in i + 1 until coords.size) {
                        antinodes.addAll(fn(coords[i], coords[j]))
                    }
                }
            }
            return antinodes.size
        }

        private val computeAntinodePart1 = { a: Coord, b: Coord ->
            val (ax, ay) = a
            val (bx, by) = b
            val deltaX = ax - bx
            val deltaY = ay - by
            val antinode1 = Pair(bx - deltaX, by - deltaY)
            val antinode2 = Pair(ax + deltaX, ay + deltaY)
            listOf(antinode1, antinode2).filter { it.withInBoundary() }
        }

        private val computeAntinodePart2 = { a: Coord, b: Coord ->
            val (ax, ay) = a
            val (bx, by) = b
            val deltaX = ax - bx
            val deltaY = ay - by
            val antinodes = mutableListOf<Coord>()
            // +ve direction
            var i = 0
            while (true) {
                val nextNode = Pair(ax + i * deltaX, ay + i * deltaY)
                if (nextNode.withInBoundary()) {
                    antinodes.add(nextNode)
                    i++
                } else {
                    break
                }
            }

            // -ve direction
            var j = 0
            while (true) {
                val nextNode = Pair(bx - j * deltaX, by - j * deltaY)
                if (nextNode.withInBoundary()) {
                    antinodes.add(nextNode)
                    j++
                } else {
                    break
                }
            }
            antinodes
        }

        private fun Coord.withInBoundary(): Boolean {
            val (x, y) = this
            return x in 0 until xSize && y in 0 until ySize
        }
    }
}
