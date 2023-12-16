package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day16 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().map { it.toList() }
        return Contraption(inputs).computeEnergized()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().map { it.toList() }
        return Contraption(inputs).findMaxEnergyConfigScore()
    }
}

enum class BeamDirection {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

private data class Beam(val x: Int, val y: Int, val direction: BeamDirection)

/** Not thread safe! */
class Contraption(private val input: List<List<Char>>) {
    private val X = input.first().size
    private val Y = input.size

    private val visited: MutableSet<Beam> = mutableSetOf()

    private fun reset() {
        visited.clear()
    }

    /** Part 1 */
    fun computeEnergized(
        x: Int = 0,
        y: Int = 0,
        direction: BeamDirection = BeamDirection.RIGHT
    ): Int {
        move(x, y, direction)
        return uniqueCoord().also { reset() }
    }

    /** Part 2 */
    fun findMaxEnergyConfigScore(): Int {
        val score = mutableListOf<Int>()
        for (x in 0 ..< X) {
            score.add(computeEnergized(x, 0, BeamDirection.DOWN)) // Top edge
            score.add(computeEnergized(x, Y - 1, BeamDirection.UP)) // Bottom edge
        }
        for (y in 0 ..< Y) {
            score.add(computeEnergized(0, y, BeamDirection.RIGHT)) // Left edge
            score.add(computeEnergized(X - 1, y, BeamDirection.LEFT)) // Right edge
        }
        return score.max()
    }

    private fun uniqueCoord(): Int {
        return visited.map { (x, y, _) -> x to y }.distinct().count()
    }

    private fun moveRight(x: Int, y: Int) {
        if (x + 1 < X) move(x + 1, y, BeamDirection.RIGHT)
    }

    private fun moveLeft(x: Int, y: Int) {
        if (x - 1 >= 0) move(x - 1, y, BeamDirection.LEFT)
    }

    private fun moveUp(x: Int, y: Int) {
        if (y - 1 >= 0) move(x, y - 1, BeamDirection.UP)
    }

    private fun moveDown(x: Int, y: Int) {
        if (y + 1 < Y) move(x, y + 1, BeamDirection.DOWN)
    }

    private fun move(x: Int, y: Int, direction: BeamDirection) {
        val tile = input[y][x]
        val beam = Beam(x, y, direction)
        if (visited.contains(beam)) {
            return // No need to repeat since it will loop
        }
        visited.add(beam)
        when (direction) {
            BeamDirection.UP -> {
                when (tile) {
                    '/' -> moveRight(x, y)
                    '\\' -> moveLeft(x, y)
                    '-' -> {
                        moveLeft(x, y)
                        moveRight(x, y)
                    }
                    else -> moveUp(x, y)
                }
            }
            BeamDirection.DOWN -> {
                when (tile) {
                    '/' -> moveLeft(x, y)
                    '\\' -> moveRight(x, y)
                    '-' -> {
                        moveLeft(x, y)
                        moveRight(x, y)
                    }
                    else -> moveDown(x, y)
                }
            }
            BeamDirection.LEFT -> {
                when (tile) {
                    '/' -> moveDown(x, y)
                    '\\' -> moveUp(x, y)
                    '|' -> {
                        moveUp(x, y)
                        moveDown(x, y)
                    }
                    else -> moveLeft(x, y)
                }
            }
            BeamDirection.RIGHT -> {
                when (tile) {
                    '/' -> moveUp(x, y)
                    '\\' -> moveDown(x, y)
                    '|' -> {
                        moveUp(x, y)
                        moveDown(x, y)
                    }
                    else -> moveRight(x, y)
                }
            }
        }
    }
}
