package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.*

object Day10 : Problem<Int> {

    override fun computePart1(inputFile: String): Int {
        val input = inputFile.readFileFromResource().to2DChars()
        val maze = input.toIntMaze()
        val c = ComputePart1(maze)
        return c.computeScore()
    }

    private class ComputePart1(val maze: Maze<Int>) {
        fun dfs(pos: Coord, height: Int): Set<Coord> {
            return if (height == 9) {
                setOf(pos)
            } else {
                val nextHeight = height + 1
                maze.setCurrentPos(pos)
                val up = maze.walkUp(1)
                val down = maze.walkDown(1)
                val left = maze.walkLeft(1)
                val right = maze.walkRight(1)
                up.walk(nextHeight) +
                    down.walk(nextHeight) +
                    left.walk(nextHeight) +
                    right.walk(nextHeight)
            }
        }

        private fun Maze.NextCell<Int>?.walk(nextHeight: Int): Set<Coord> =
            this?.takeIf { it.cell == nextHeight }?.let { dfs(it.pos, nextHeight) } ?: setOf()

        fun computeScore(): Int {
            return maze.inputs
                .mapIndexed { y, rows ->
                    rows
                        .mapIndexed { x, height ->
                            if (height == 0) dfs(Coord(x, y), height).size else 0
                        }
                        .sum()
                }
                .sum()
        }
    }

    override fun computePart2(inputFile: String): Int {
        val maze = inputFile.readFileFromResource().to2DChars().toIntMaze()
        return 0
    }
}
