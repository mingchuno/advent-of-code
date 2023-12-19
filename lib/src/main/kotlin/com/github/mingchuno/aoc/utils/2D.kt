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
    RIGHT
}

fun Direction.opposite(): Direction {
    return when (this) {
        Direction.UP -> Direction.DOWN
        Direction.DOWN -> Direction.UP
        Direction.LEFT -> Direction.RIGHT
        Direction.RIGHT -> Direction.LEFT
    }
}
