package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.to2DChars
import com.github.mingchuno.aoc.utils.toMaze

object Day4 : Problem<Int> {

    private fun List<Char>.isXmas(): Boolean = this.joinToString("") == "XMAS"

    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        val maze = inputs.toMaze()
        var count = 0
        inputs.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == 'X') {
                    maze.setCurrentPos(x, y)
                    // right direction
                    if ((0..3).mapNotNull { maze.walkRight(it) }.isXmas()) count++
                    // left direction
                    if ((0..3).mapNotNull { maze.walkLeft(it) }.isXmas()) count++
                    // down direction
                    if ((0..3).mapNotNull { maze.walkDown(it) }.isXmas()) count++
                    // up direction
                    if ((0..3).mapNotNull { maze.walkUp(it) }.isXmas()) count++
                    // NE direction
                    if ((0..3).mapNotNull { maze.walkTopRight(it) }.isXmas()) count++
                    // SE direction
                    if ((0..3).mapNotNull { maze.walkBottomRight(it) }.isXmas()) count++
                    // SW direction
                    if ((0..3).mapNotNull { maze.walkBottomLeft(it) }.isXmas()) count++
                    // NW direction
                    if ((0..3).mapNotNull { maze.walkTopLeft(it) }.isXmas()) count++
                }
            }
        }
        return count
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        val maze = inputs.toMaze()
        var count = 0
        inputs.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == 'A') {
                    maze.setCurrentPos(x, y)
                    val pattern1 = maze.walkTopLeft(1) == 'M' && maze.walkBottomRight(1) == 'S'
                    val pattern2 = maze.walkTopLeft(1) == 'S' && maze.walkBottomRight(1) == 'M'
                    val pattern3 = maze.walkTopRight(1) == 'M' && maze.walkBottomLeft(1) == 'S'
                    val pattern4 = maze.walkTopRight(1) == 'S' && maze.walkBottomLeft(1) == 'M'
                    val isX = (pattern1 || pattern2) && (pattern3 || pattern4)
                    if (isX) {
                        count++
                    }
                }
            }
        }
        return count
    }
}
