package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.Direction
import com.github.mingchuno.aoc.utils.LongCoord
import com.github.mingchuno.aoc.utils.ThisShouldNotHappenException
import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlin.math.abs

object Day18 : Problem<Long> {
    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val instructions = inputs.map { parseLine(it) }
        return findArea(instructions)
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val instructions = inputs.map { parseLine(it).parsePart2() }
        return findArea(instructions)
    }

    private fun findArea(instructions: List<Instruction>): Long {
        val vertices: List<LongCoord> =
            instructions.scan(0L to 0L) { acc, instruction ->
                val (x, y) = acc
                when (instruction.direction) {
                    Direction.RIGHT -> x + instruction.steps to y
                    Direction.LEFT -> x - instruction.steps to y
                    Direction.UP -> x to y - instruction.steps
                    Direction.DOWN -> x to y + instruction.steps
                }
            }
        // https://brilliant.org/wiki/area-of-a-polygon/
        val area: Long =
            abs(
                (vertices + vertices[0])
                    .zipWithNext { prev, next ->
                        val (x1, y1) = prev
                        val (x2, y2) = next
                        x1 * y2 - y1 * x2
                    }
                    .sum() / 2
            )
        val boundary: Long = instructions.sumOf { it.steps.toLong() }
        // https://en.wikipedia.org/wiki/Pick%27s_theorem
        val interior: Long = area + 1 - boundary / 2
        println("interior=$interior;boundary=$boundary;area=$area")
        return interior + boundary
    }
}

private data class Instruction(val direction: Direction, val steps: Int, val color: String) {
    fun parsePart2(): Instruction {
        val newSteps = color.take(5).toInt(16)
        val newDirection =
            when (color.takeLast(1)) {
                "0" -> Direction.RIGHT
                "1" -> Direction.DOWN
                "2" -> Direction.LEFT
                "3" -> Direction.UP
                else -> throw ThisShouldNotHappenException()
            }
        return Instruction(newDirection, newSteps, color)
    }
}

private val colorStrRegexp = """\(#(.{6})\)""".toRegex()

private fun parseLine(instruction: String): Instruction {
    val (dirStr, stepStr, colorStr) = instruction.split(" ")
    val direction =
        when (dirStr) {
            "R" -> Direction.RIGHT
            "L" -> Direction.LEFT
            "U" -> Direction.UP
            "D" -> Direction.DOWN
            else -> throw ThisShouldNotHappenException()
        }
    val steps = stepStr.toInt()
    val color = colorStrRegexp.find(colorStr)?.groupValues?.last()!!
    return Instruction(direction, steps, color)
}
