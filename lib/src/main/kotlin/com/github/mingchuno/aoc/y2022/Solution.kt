package com.github.mingchuno.aoc.y2022

import com.github.mingchuno.aoc.utils.readFileFromResource

abstract class Solution {
    private val year: Int = 2022
    abstract val day: Int

    private val exampleInputPath: String by lazy { "$year/day$day-example.txt" }
    private val realInputPath: String by lazy { "$year/day$day-real.txt" }

    abstract fun solvePart1(input: List<String>): String

    abstract fun solvePart2(input: List<String>): String

    fun solvePart1Example(): String = solvePart1(exampleInputPath.readFileFromResource())

    fun solvePart2Example(): String = solvePart2(exampleInputPath.readFileFromResource())

    fun solvePart1Real(): String = solvePart1(realInputPath.readFileFromResource())

    fun solvePart2Real(): String = solvePart2(realInputPath.readFileFromResource())
}
