package com.github.mingchuno.aoc.utils

typealias Coord = Pair<Int, Int>

typealias LongCoord = Pair<Long, Long>

fun Coord.atOrigin(): Boolean {
    val (x, y) = this
    return x == 0 && y == 0
}

fun Coord.toString(): String = "x=${first},y=${second}"

fun Coord.equals(x: Int, y: Int): Boolean = first == x && second == y

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
}

fun Char.direction(): Direction =
    when (this) {
        '^' -> Direction.UP
        '>' -> Direction.RIGHT
        '<' -> Direction.LEFT
        'v' -> Direction.DOWN
        else -> throw ThisShouldNotHappenException()
    }

fun Direction.turnRight(): Direction {
    return when (this) {
        Direction.UP -> Direction.RIGHT
        Direction.DOWN -> Direction.LEFT
        Direction.LEFT -> Direction.UP
        Direction.RIGHT -> Direction.DOWN
    }
}

fun Direction.turnLeft(): Direction {
    return when (this) {
        Direction.UP -> Direction.LEFT
        Direction.DOWN -> Direction.RIGHT
        Direction.LEFT -> Direction.DOWN
        Direction.RIGHT -> Direction.UP
    }
}

fun Direction.opposite(): Direction {
    return when (this) {
        Direction.UP -> Direction.DOWN
        Direction.DOWN -> Direction.UP
        Direction.LEFT -> Direction.RIGHT
        Direction.RIGHT -> Direction.LEFT
    }
}
