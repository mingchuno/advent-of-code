package com.github.mingchuno.aoc.y2023.d25

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day25 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val graph = buildGraph(inputs)
        return WalkGraph(graph, listOf("kdk" to "nct", "fsv" to "spx", "tvj" to "cvx"))
            .walkAndDetermineIsSplit() ?: 0
        //        val g = Day25Part1(inputs)
        //        g.printGraphviz()
        //        return g.analysis()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        TODO("Not yet implemented")
    }
}

fun buildGraph(inputs: List<String>): MGraph {
    val graph: MGraph = mutableMapOf()
    for (row in inputs) {
        val (key, rowValues) = row.split(":")
        val values = rowValues.split(" ").filter { it.isNotEmpty() }.map { it.trim() }
        // a -> b
        graph.compute(key) { _, v -> ((v ?: listOf()) + values).toMutableList() }
        // b -> a
        values.forEach { vKey ->
            graph.compute(vKey) { _, v -> ((v ?: listOf()) + key).toMutableList() }
        }
    }
    return graph
}
