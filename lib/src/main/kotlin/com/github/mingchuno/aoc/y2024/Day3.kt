package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day3 : Problem<Int> {

    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return inputs.sumOf { scanAndComputeProgram(it).sum() }
    }

    private fun scanAndComputeProgram(program: String): List<Int> {
        val pattern = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val matches = pattern.findAll(program)
        return matches
            .map { match ->
                val a = match.groups[1]?.value?.toInt()
                val b = match.groups[2]?.value?.toInt()
                if (a != null && b != null) {
                    return@map a * b
                } else {
                    throw RuntimeException("This should not happen")
                }
            }
            .toList()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return scanAndComputeProgramPart2(inputs.joinToString(""))
    }

    private fun scanAndComputeProgramPart2(program: String): Int {
        val pattern = Regex("do\\(\\)|don't\\(\\)|mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val matches = pattern.findAll(program)
        var sum = 0
        var enable = true
        matches.forEach { match ->
            val t = if (enable) "Y" else "N"
            println("${t}/${match.value}")
            if (match.value == "do()") {
                enable = true
            } else if (match.value == "don't()") {
                enable = false
            } else {
                if (enable) {
                    val a = match.groups[1]?.value?.toInt()
                    val b = match.groups[2]?.value?.toInt()
                    if (a != null && b != null) {
                        sum += a * b
                    } else {
                        throw RuntimeException("This should not happen")
                    }
                }
            }
        }
        return sum
    }
}
