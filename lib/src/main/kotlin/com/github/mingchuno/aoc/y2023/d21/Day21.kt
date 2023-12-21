package com.github.mingchuno.aoc.y2023.d21

import com.github.mingchuno.aoc.utils.readFileFromResource

object Day21 {
    fun computePart1(inputFile: String, maxSteps: Int): Int {
        val inputs = inputFile.readFileFromResource().toInputs()
        return BFS(inputs, maxSteps).computePart1()
    }

    fun computePart2(inputFile: String, maxSteps: Int): Int {
        val inputs = inputFile.readFileFromResource().toInputs()
        TODO("Not yet implemented")
    }
}

private fun List<String>.toInputs(): List<List<Char>> = map { it.toList() }
