package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.Coord
import com.github.mingchuno.aoc.utils.Direction
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day18 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val instructions = inputs.map { parseLine(it) }
        return DigPlan(instructions).part1()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        TODO("Not yet implemented")
    }
}

private data class Plan(val coord: Coord, val wall: Char)

private class DigPlan(private val instructions: List<Instruction>) {
    private val SIZE = instructions.size

    fun buildPlans(): List<Plan> {
        return instructions.foldIndexed(listOf()) { idx, plans, instruction ->
            val last = plans.lastOrNull()
            val (startX, startY) = last?.coord ?: (0 to 0)
            val nextInstruction = if (idx + 1 != SIZE) instructions[idx + 1] else instructions[0]
            val turning = turningDirection(instruction.direction, nextInstruction.direction)
            val newPlans =
                when (instruction.direction) {
                    Direction.RIGHT -> {
                        (startX + 1 ..< startX + instruction.steps).map { x ->
                            Plan(x to startY, wall = '-')
                        } + Plan(startX + instruction.steps to startY, wall = turning)
                    }
                    Direction.LEFT -> {
                        ((startX - instruction.steps + 1) ..< startX).map { x ->
                            Plan(x to startY, wall = '-')
                        } + Plan(startX - instruction.steps to startY, wall = turning)
                    }
                    Direction.UP -> {
                        ((startY - instruction.steps + 1) ..< startY).map { y ->
                            Plan(startX to y, wall = '|')
                        } + Plan(startX to startY - instruction.steps, wall = turning)
                    }
                    Direction.DOWN -> {
                        (startY + 1 ..< (startY + instruction.steps)).map { y ->
                            Plan(startX to y, wall = '|')
                        } + Plan(startX to startY + instruction.steps, wall = turning)
                    }
                }
            plans + newPlans
        }
    }

    private fun List<Plan>.normalizePlans(): List<Plan> {
        val minX = this.minBy { it.coord.first }.coord.first
        val minY = this.minBy { it.coord.second }.coord.second
        return this.map {
            val (x, y) = it.coord
            it.copy(coord = (x - minX) to (y - minY))
        }
    }

    fun drawMapFromPlans(plans: List<Plan>): List<String> {
        val maxX = plans.maxBy { it.coord.first }.coord.first
        val maxY = plans.maxBy { it.coord.second }.coord.second
        val map = List(maxY + 1) { MutableList(maxX + 1) { '.' } }
        plans.forEachIndexed { idx, plan ->
            val (x, y) = plan.coord
            map[y][x] = if (idx == 0) 'S' else plan.wall
        }
        return map.map { it.joinToString("") }
    }

    fun part0(): List<String> {
        val plans = buildPlans().normalizePlans()
        return drawMapFromPlans(plans)
    }

    fun part1(): Int {
        val inputs = part0()
        return Day10.DFS(inputs).totalArea()
    }

    private fun turningDirection(from: Direction, to: Direction): Char {
        return when (from to to) {
            (Direction.RIGHT to Direction.DOWN) -> '7'
            (Direction.RIGHT to Direction.UP) -> 'J'
            (Direction.LEFT to Direction.DOWN) -> 'F'
            (Direction.LEFT to Direction.UP) -> 'L'
            (Direction.UP to Direction.RIGHT) -> 'F'
            (Direction.UP to Direction.LEFT) -> '7'
            (Direction.DOWN to Direction.RIGHT) -> 'L'
            (Direction.DOWN to Direction.LEFT) -> 'J'
            else -> throw Exception("This should not happens")
        }
    }
}

private data class Instruction(val direction: Direction, val steps: Int, val color: String)

private val colorStrRegexp = """\(#.{6}\)""".toRegex()

private fun parseLine(instruction: String): Instruction {
    val (dirStr, stepStr, colorStr) = instruction.split(" ")
    val direction =
        when (dirStr) {
            "R" -> Direction.RIGHT
            "L" -> Direction.LEFT
            "U" -> Direction.UP
            "D" -> Direction.DOWN
            else -> throw Exception("Should not happens")
        }
    val steps = stepStr.toInt()
    val color = colorStrRegexp.findAll(colorStr).map { it.value }.first()
    return Instruction(direction, steps, color)
}
