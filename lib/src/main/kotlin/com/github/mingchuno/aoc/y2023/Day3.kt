package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.readFileFromResource

object Day3 {
    private val partsNumberRegex = """(\d+)""".toRegex()

    fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val parts = parseParts(inputs)
        val symbols = parseSymbol(inputs)
        return symbols
            .flatMap { symbol ->
                parts.filter { part -> validPart(part, symbol) }.map { it.partNumber }
            }
            .sum()
    }

    fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val parts = parseParts(inputs)
        val gears = parseGear(inputs)
        return gears.sumOf { gear ->
            val validParts = parts.filter { part -> validPart(part, gear) }
            val ratio =
                if (validParts.size == 2) {
                    val (first, second) = validParts
                    first.partNumber * second.partNumber
                } else 0
            ratio
        }
    }

    private fun validPart(part: Part, gear: Gear): Boolean {
        val rowRange = IntRange(gear.row - 1, gear.row + 1)
        val colRange = IntRange(gear.col - 1, gear.col + 1)
        return rowRange.contains(part.row) && part.range.intersect(colRange).isNotEmpty()
    }

    private fun parseParts(inputs: List<String>): List<Part> {
        return inputs.flatMapIndexed { idx, it ->
            partsNumberRegex
                .findAll(it)
                .map { res -> Part(partNumber = res.value.toInt(), row = idx, range = res.range) }
                .toList()
        }
    }

    private fun parse(inputs: List<String>, checkFn: Char.() -> Boolean): List<Gear> {
        return inputs.flatMapIndexed { row, it ->
            it.mapIndexed { col, char -> if (checkFn(char)) Gear(row = row, col = col) else null }
                .filterNotNull()
        }
    }

    private fun parseGear(inputs: List<String>): List<Gear> = parse(inputs, Char::isGear)

    private fun parseSymbol(inputs: List<String>): List<Gear> = parse(inputs, Char::isSymbol)
}

data class Gear(val row: Int, val col: Int)

data class Part(val partNumber: Int, val row: Int, val range: IntRange)

private fun Char.isSymbol(): Boolean = !this.isDigit() && this != '.'

private fun Char.isGear(): Boolean = this == '*'
