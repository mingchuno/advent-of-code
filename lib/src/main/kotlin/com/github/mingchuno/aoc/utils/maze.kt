package com.github.mingchuno.aoc.utils

fun List<List<Char>>.toMaze(): Maze = Maze(this)

/** Walking in a 2D maze */
class Maze(val inputs: List<List<Char>>) {
    private var currentPosX = 0
    private var currentPosY = 0

    fun setCurrentPos(x: Int, y: Int) {
        currentPosX = x
        currentPosY = y
    }

    fun setCurrentPos(coord: Coord) {
        val (x, y) = coord
        setCurrentPos(x, y)
    }

    val currentPos: Coord
        get() = Pair(currentPosX, currentPosY)

    fun findStartingPos(vararg chars: Char): Coord? {
        val yRange = inputs.indices
        val xRange = inputs[0].indices
        val charSet = chars.toSet()
        for (y in yRange) {
            for (x in xRange) {
                if (charSet.contains(inputs[y][x])) {
                    return Pair(x, y)
                }
            }
        }
        return null // Not found
    }

    val currentCell: Char
        get() = inputs[currentPosY][currentPosX]

    fun checkUp(step: Int): Char? = walkUp(step)?.cell

    fun checkDown(step: Int): Char? = walkDown(step)?.cell

    fun checkLeft(step: Int): Char? = walkLeft(step)?.cell

    fun checkRight(step: Int): Char? = walkRight(step)?.cell

    fun checkTopRight(step: Int): Char? = walkTopRight(step)?.cell

    fun checkTopLeft(step: Int): Char? = walkTopLeft(step)?.cell

    fun checkBottomRight(step: Int): Char? = walkBottomRight(step)?.cell

    fun checkBottomLeft(step: Int): Char? = walkBottomLeft(step)?.cell

    fun walkUp(step: Int): NextCell? = walkGeneric(currentPosX, currentPosY - step)

    fun walkDown(step: Int): NextCell? = walkGeneric(currentPosX, currentPosY + step)

    fun walkLeft(step: Int): NextCell? = walkGeneric(currentPosX - step, currentPosY)

    fun walkRight(step: Int): NextCell? = walkGeneric(currentPosX + step, currentPosY)

    fun walkTopRight(step: Int): NextCell? = walkGeneric(currentPosX + step, currentPosY - step)

    fun walkTopLeft(step: Int): NextCell? = walkGeneric(currentPosX - step, currentPosY - step)

    fun walkBottomRight(step: Int): NextCell? = walkGeneric(currentPosX + step, currentPosY + step)

    fun walkBottomLeft(step: Int): NextCell? = walkGeneric(currentPosX - step, currentPosY + step)

    private fun walkGeneric(newX: Int, newY: Int): NextCell? =
        inputs.getOrNull(newY)?.getOrNull(newX)?.let { NextCell(newX, newY, it) }

    data class NextCell(val x: Int, val y: Int, val cell: Char) {
        val pos: Coord
            get() = Pair(x, y)
    }
}
