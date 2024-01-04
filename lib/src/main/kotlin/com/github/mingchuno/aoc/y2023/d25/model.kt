package com.github.mingchuno.aoc.y2023.d25

import com.github.mingchuno.aoc.utils.ThisShouldNotHappenException
import java.util.*

typealias Graph = Map<String, List<String>>

typealias MGraph = MutableMap<String, MutableList<String>>

fun Graph.print() {
    onEach { (k, v) -> println("k=$k;v=$v") }
}

fun MGraph.removeLinks(links: List<Pair<String, String>>): Graph {
    links.forEach { (k, v) ->
        this[k]!!.remove(v)
        this[v]!!.remove(k)
    }
    return this
}

class Day25Part1(inputs: List<String>) {
    private val unidirectionalLink =
        inputs
            .map { row ->
                val (key, rowValues) = row.split(":")
                val values = rowValues.split(" ").filter { it.isNotEmpty() }.map { it.trim() }
                key to values
            }
            .sortedBy { (_, v) -> v.size }
    private val allLinks = unidirectionalLink.flatMap { (k, xs) -> xs.map { k to it } }

    fun printGraphviz() {
        unidirectionalLink.forEach { (k, v) -> v.forEach { println("$k -- $it;") } }
    }

    fun analysis(): Int {
        val SIZE = allLinks.size
        val graph = buildGraph()
        println("edge=${allLinks.size}")
        var iter = 0
        for (i in allLinks.indices) {
            for (j in i + 1 ..< SIZE) {
                for (k in j + 1 ..< SIZE) {
                    iter++
                    if (iter.mod(10000) == 0) {
                        println("iter=$iter")
                    }
                    val skippedLinks = listOf(allLinks[i], allLinks[j], allLinks[k])
                    val res = WalkGraph(graph, skippedLinks).walkAndDetermineIsSplit()
                    if (res != null) {
                        // Found ans!
                        return res
                    }
                }
            }
        }
        throw ThisShouldNotHappenException()
    }

    private fun buildGraph(): Graph {
        val graph: MGraph = mutableMapOf()
        for ((k, value) in allLinks) {
            // a -> b
            graph.compute(k) { _, v -> ((v ?: listOf()) + value).toMutableList() }
            // b -> a
            graph.compute(value) { _, v -> ((v ?: listOf()) + k).toMutableList() }
        }
        return graph
    }
}

class WalkGraph(
    private val graph: Graph,
    private val skippedEdges: List<Pair<String, String>> = listOf()
) {
    private val visited = mutableSetOf<String>()
    private val q = LinkedList<String>()

    fun walkAndDetermineIsSplit(): Int? {
        q.add(graph.keys.first()) // Add a random item into Q
        bfs()
        return if (visited.size != graph.size) {
            visited.size * (graph.size - visited.size)
        } else null
    }

    private fun bfs() {
        while (q.isNotEmpty()) {
            val key = q.poll()
            val links = graph[key]!!
            visited.add(key)
            for (link in links) {
                val kl = key to link
                if (
                    !visited.contains(link) &&
                        skippedEdges.all { (a, b) -> (a to b) != kl && (b to a) != kl }
                ) {
                    q.add(link)
                }
            }
        }
    }
}
