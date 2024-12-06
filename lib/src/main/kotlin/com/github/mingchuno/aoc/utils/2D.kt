package com.github.mingchuno.aoc.utils

typealias Coord = Pair<Int, Int>

typealias LongCoord = Pair<Long, Long>

fun Coord.atOrigin(): Boolean {
    val (x, y) = this
    return x == 0 && y == 0
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}

fun Direction.opposite(): Direction {
    return when (this) {
        Direction.UP -> Direction.DOWN
        Direction.DOWN -> Direction.UP
        Direction.LEFT -> Direction.RIGHT
        Direction.RIGHT -> Direction.LEFT
    }
}

fun List<List<Char>>.toMaze(): Maze = Maze(this)

/** Walking in a 2D maze */
class Maze(val inputs: List<List<Char>>) {
    private var currentPosX = 0
    private var currentPosY = 0

    fun setCurrentPos(x: Int, y: Int) {
        currentPosX = x
        currentPosY = y
    }

    fun walkUp(step: Int): Char? {
        return inputs.getOrNull(currentPosY - step)?.getOrNull(currentPosX)
    }

    fun walkDown(step: Int): Char? {
        return inputs.getOrNull(currentPosY + step)?.getOrNull(currentPosX)
    }

    fun walkLeft(step: Int): Char? {
        return inputs.getOrNull(currentPosY)?.getOrNull(currentPosX - step)
    }

    fun walkRight(step: Int): Char? {
        return inputs.getOrNull(currentPosY)?.getOrNull(currentPosX + step)
    }

    fun walkTopRight(step: Int): Char? {
        return inputs.getOrNull(currentPosY - step)?.getOrNull(currentPosX + step)
    }

    fun walkTopLeft(step: Int): Char? {
        return inputs.getOrNull(currentPosY - step)?.getOrNull(currentPosX - step)
    }

    fun walkBottomRight(step: Int): Char? {
        return inputs.getOrNull(currentPosY + step)?.getOrNull(currentPosX + step)
    }

    fun walkBottomLeft(step: Int): Char? {
        return inputs.getOrNull(currentPosY + step)?.getOrNull(currentPosX - step)
    }
}
