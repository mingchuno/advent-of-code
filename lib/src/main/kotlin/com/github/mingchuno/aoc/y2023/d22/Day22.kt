package com.github.mingchuno.aoc.y2023.d22

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.parseInts
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day22 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val bricks = inputs.map { it.parseBrick() }
        return Jenga(bricks).compute()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        TODO("Not yet implemented")
    }
}

private fun String.parseBrick(): Brick {
    val (start, end) = this.split("~").map { it.parseInts() }
    val (sx, sy, sz) = start
    val (ex, ey, ez) = end
    return Brick(Position(sx, sy, sz), Position(ex, ey, ez))
}
