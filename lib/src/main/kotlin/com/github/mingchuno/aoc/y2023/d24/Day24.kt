package com.github.mingchuno.aoc.y2023.d24

import com.github.mingchuno.aoc.utils.parseInts
import com.github.mingchuno.aoc.utils.parseLongs
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day24 {
    fun computePart1(inputFile: String, start: Long, end: Long): Int {
        val inputs = inputFile.readFileFromResource()
        val hailstones = inputs.map { it.parseHailstone() }
        return part1(hailstones, start, end)
    }

    fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val hailstones = inputs.map { it.parseHailstone() }
        TODO("Not yet implemented")
    }
}

private fun String.parseHailstone(): Hailstones {
    val (p0, v) = this.split("@")
    val (x, y, z) = p0.parseLongs()
    val (vx, vy, vz) = v.parseInts()
    return Hailstones(x, y, z, vx, vy, vz)
}

private fun part1(hailstones: List<Hailstones>, start: Long, end: Long): Int {
    var count = 0
    for (i in hailstones.indices) {
        for (j in 0..i) {
            if (willIntersect(hailstones[i], hailstones[j], start, end)) {
                count++
            }
        }
    }
    return count
}
