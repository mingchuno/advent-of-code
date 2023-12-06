package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.numberRegexp
import com.github.mingchuno.aoc.utils.parseLongs
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day6 {
    fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val races = parseInputsPart1(inputs)
        val ways = races.map { race -> race.findWinningCount() }
        return ways.reduce { a, b -> a * b }
    }

    fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val race = parseInputsPart2(inputs)
        return race.findWinningCount()
    }

    private fun parseInputsPart2(inputs: List<String>): Race {
        val time = inputs[0].parseSingleLong()
        val distance = inputs[1].parseSingleLong()
        return Race(time = time, distance = distance)
    }

    private fun String.parseSingleLong(): Long {
        return numberRegexp.findAll(this).map { it.value }.joinToString("").toLong()
    }

    private fun parseInputsPart1(inputs: List<String>): List<Race> {
        val times = inputs[0].parseLongs()
        val distances = inputs[1].parseLongs()
        return times.zip(distances).map { (time, distance) ->
            Race(time = time, distance = distance)
        }
    }
}

data class Race(val time: Long, val distance: Long) {
    fun findWinningCount(): Int = LongRange(0, time).count { th -> (time - th) * th > distance }
}
