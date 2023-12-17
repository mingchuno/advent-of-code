package com.github.mingchuno.aoc.utils

typealias Coord = Pair<Int, Int>

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
