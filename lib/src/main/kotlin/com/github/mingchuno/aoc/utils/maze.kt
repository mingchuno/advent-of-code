package com.github.mingchuno.aoc.utils

fun List<List<Char>>.toMaze(): Maze<Char> = Maze(this)

fun List<List<Char>>.toIntMaze(): Maze<Int> = Maze(this.map { row -> row.map { it.digitToInt() } })

/** Walking in a 2D maze */
class Maze<T>(val inputs: List<List<T>>) {
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

    fun findStartingPos(vararg chars: T): Coord? {
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

    val currentCell: T
        get() = inputs[currentPosY][currentPosX]

    fun checkUp(step: Int): T? = walkUp(step)?.cell

    fun checkDown(step: Int): T? = walkDown(step)?.cell

    fun checkLeft(step: Int): T? = walkLeft(step)?.cell

    fun checkRight(step: Int): T? = walkRight(step)?.cell

    fun checkTopRight(step: Int): T? = walkTopRight(step)?.cell

    fun checkTopLeft(step: Int): T? = walkTopLeft(step)?.cell

    fun checkBottomRight(step: Int): T? = walkBottomRight(step)?.cell

    fun checkBottomLeft(step: Int): T? = walkBottomLeft(step)?.cell

    fun walkUp(step: Int): NextCell<T>? = walkGeneric(currentPosX, currentPosY - step)

    fun walkDown(step: Int): NextCell<T>? = walkGeneric(currentPosX, currentPosY + step)

    fun walkLeft(step: Int): NextCell<T>? = walkGeneric(currentPosX - step, currentPosY)

    fun walkRight(step: Int): NextCell<T>? = walkGeneric(currentPosX + step, currentPosY)

    fun walkTopRight(step: Int): NextCell<T>? = walkGeneric(currentPosX + step, currentPosY - step)

    fun walkTopLeft(step: Int): NextCell<T>? = walkGeneric(currentPosX - step, currentPosY - step)

    fun walkBottomRight(step: Int): NextCell<T>? =
        walkGeneric(currentPosX + step, currentPosY + step)

    fun walkBottomLeft(step: Int): NextCell<T>? =
        walkGeneric(currentPosX - step, currentPosY + step)

    private fun walkGeneric(newX: Int, newY: Int): NextCell<T>? =
        inputs.getOrNull(newY)?.getOrNull(newX)?.let { NextCell(newX, newY, it) }

    data class NextCell<T>(val x: Int, val y: Int, val cell: T) {
        val pos: Coord
            get() = Pair(x, y)
    }
}
