package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.pmap
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day8 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        // setup
        val instructions = inputs.first().parseInstructionsAsSeq()
        val nodes = parseNodes(inputs.drop(2))
        // var
        var stepCount = 0
        var currentKey = "AAA"
        // run
        for (instruction in instructions) {
            val (l, r) = nodes[currentKey]!!
            currentKey = if (instruction == 'L') l else r
            stepCount++
            if (currentKey == "ZZZ") {
                break
            }
        }
        return stepCount
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        // setup
        val instructions = inputs.first().parseInstructionsAsSeq()
        instructions.take(100).toList().onEach { println(it) }
        val nodes = parseNodes(inputs.drop(2))
        val startingNode = nodes.keys.filter { it.endsWith("A") }

        // var
        var stepCount = 0
        var currentKeys = startingNode
        // run
        for (instruction in instructions) {
            currentKeys =
                currentKeys.map {
                    val (l, r) = nodes[it]!!
                    if (instruction == 'L') l else r
                }
            stepCount++
            if (stepCount.mod(100000000) == 0) {
                println(stepCount)
            }
            if (currentKeys.any { it.endsWith("Z") }) {
                println(stepCount)
                println(currentKeys)
            }
            if (currentKeys.all { it.endsWith("Z") }) {
                break
            }
        }
        return stepCount
    }

    suspend fun computePart2Method2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val instructions = inputs.first().parseInstructionsAsSeq()
        val nodes = parseNodes(inputs.drop(2))
        val startingNode = nodes.keys.filter { it.endsWith("A") }
        val stepPairs = startingNode.pmap { run(instructions, nodes, it) }
        println(stepPairs)
        return 0
    }

    private fun run(
        instructions: Sequence<Char>,
        nodes: Map<String, Pair<String, String>>,
        startingKey: String
    ): Int {
        // var
        var currentKey = startingKey
        var stepCount = 0
        // run
        for (instruction in instructions) {
            stepCount++
            val (l, r) = nodes[currentKey]!!
            currentKey = if (instruction == 'L') l else r
            if (currentKey.endsWith("Z")) {
                break
            }
        }
        println("start:$startingKey;stepCount=$stepCount")
        return stepCount
    }

    private fun parseNodes(inputs: List<String>): Map<String, Pair<String, String>> {
        return inputs.associate { it.parseNode() }
    }

    private fun String.parseInstructionsAsSeq(): Sequence<Char> {
        val steps = this.toCharArray().toList()
        val size = steps.size
        return generateSequence(0) { it + 1 }.map { steps[it.mod(size)] }
    }

    private val nodeRegex = """([0-9A-Z]{3})""".toRegex()

    private fun String.parseNode(): Pair<String, Pair<String, String>> {
        val (key, left, right) = nodeRegex.findAll(this).map { it.value }.toList()
        return key to (left to right)
    }
}
