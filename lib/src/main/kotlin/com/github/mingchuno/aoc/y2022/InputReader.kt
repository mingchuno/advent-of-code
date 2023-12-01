package com.github.mingchuno.aoc.y2022

import com.github.mingchuno.aoc.utils.readFileFromResource

object InputReader {
    private const val year = 2022

    private fun examplePath(day: Int) = "$year/day$day-example.txt"

    private fun realPath(day: Int) = "$year/day$day-real.txt"

    fun readExample(day: Int): List<String> = examplePath(day).readFileFromResource()

    fun readReal(day: Int): List<String> = realPath(day).readFileFromResource()
}
