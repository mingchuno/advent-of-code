package com.github.mingchuno.aoc.y2022

object Day4 : Solution() {

    private fun computeFullyContain(s: String): Boolean {
        val (xStart, xEnd, yStart, yEnd) = splitNumbers(s)
        return (xStart <= yStart && xEnd >= yEnd) || (yStart <= xStart && yEnd >= xEnd)
    }

    private fun computeOverlap(s: String): Boolean {
        val (xStart, xEnd, yStart, yEnd) = splitNumbers(s)
        return xEnd >= yStart && xStart <= yEnd
    }

    private fun splitNumbers(s: String): List<Int> {
        val (x, y) = s.split(",").take(2).map { it.split("-").take(2).map { i -> i.toInt() } }
        val (xStart, xEnd) = x
        val (yStart, yEnd) = y
        return listOf(xStart, xEnd, yStart, yEnd)
    }

    override val day: Int = 4

    override fun solvePart1(input: List<String>): String {
        return input.map { computeFullyContain(it) }.count { it }.toString()
    }

    override fun solvePart2(input: List<String>): String {
        return input.map { computeOverlap(it) }.count { it }.toString()
    }
}
