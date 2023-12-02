package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.readFileFromResource

object Day2 {
    private val gameRegexp = """Game (\d+)""".toRegex()
    private val blueRegexp = """.* (\d+) blue.*""".toRegex()
    private val redRegexp = """.* (\d+) red.*""".toRegex()
    private val greenRegexp = """.* (\d+) green.*""".toRegex()

    fun computePart1(inputFile: String, rgbTarget: RGB): Int {
        val inputs = inputFile.readFileFromResource()
        val games = parseGame(inputs)
        return games.filter { it.isPossible(rgbTarget) }.sumOf { it.id }
    }

    private fun parseGame(inputs: List<String>): List<Game> =
        inputs.map {
            val (head, tail) = it.split(":")
            val (id) = gameRegexp.matchEntire(head)!!.destructured
            val rgbs =
                tail.split(";").map { trial ->
                    RGB(
                        red = trial.matchRegexp(redRegexp),
                        green = trial.matchRegexp(greenRegexp),
                        blue = trial.matchRegexp(blueRegexp)
                    )
                }
            Game(id = id.toInt(), rgbs = rgbs)
        }

    private fun String.matchRegexp(regexp: Regex): Int {
        return regexp.matchEntire(this)?.let {
            val (count) = it.destructured
            count.toInt()
        } ?: Int.MIN_VALUE
    }

    fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val games = parseGame(inputs)
        return games.sumOf { it.foundPower() }
    }
}

private data class Game(val id: Int, val rgbs: List<RGB>) {
    private val lowerBound: RGB by lazy { rgbs.fold(RGB.LowerBound) { acc, rgb -> acc.merge(rgb) } }

    // For part 1
    fun isPossible(input: RGB): Boolean {
        return lowerBound.red <= input.red &&
            lowerBound.green <= input.green &&
            lowerBound.blue <= input.blue
    }

    // For part 2
    fun foundPower(): Int {
        return lowerBound.power()
    }
}

data class RGB(val red: Int, val green: Int, val blue: Int) {
    companion object {
        val LowerBound = RGB(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
    }

    fun power(): Int = red * green * blue

    fun merge(rgb: RGB): RGB {
        return RGB(
            red = maxOf(red, rgb.red),
            green = maxOf(green, rgb.green),
            blue = maxOf(blue, rgb.blue)
        )
    }
}
