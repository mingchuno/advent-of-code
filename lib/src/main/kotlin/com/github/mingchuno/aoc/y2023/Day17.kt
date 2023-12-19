package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.*
import java.util.*

object Day17 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().parseInput()
        return Dijkstra(inputs).shortestPath()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().parseInput()
        return Dijkstra(inputs, minSteps = 4, maxSteps = 10).shortestPath()
    }
}

private data class GraphNode(
    val coord: Coord,
    val travelDir: Direction,
    val stepsBeforeLastTurn: Int
)

private val ALL_DIRECTION = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)

private class PriorityMap<K, V>(comparator: Comparator<Pair<K, V>>) {
    private val map: MutableMap<K, V> = mutableMapOf()
    private val q: PriorityQueue<Pair<K, V>> = PriorityQueue(comparator)

    fun add(k: K, v: V): V? {
        q.add(k to v)
        return map.put(k, v)
    }

    fun isEmpty(): Boolean = map.isEmpty()

    fun isNotEmpty(): Boolean = map.isNotEmpty()

    fun poll(): Pair<K, V> {
        val pair = q.poll()
        val (k, _) = pair
        map.remove(k)
        return pair
    }

    fun replace(k: K, v: V) {
        val oldV = map[k]
        q.remove(k to oldV)
        q.add(k to v)
        map[k] = v
    }

    fun get(k: K): V? = map[k]
}

class Dijkstra(
    private val graph: List<List<Int>>,
    private val minSteps: Int = 0,
    private val maxSteps: Int = 3
) {
    private val X = graph.first().size
    private val Y = graph.size
    private val costs: PriorityMap<GraphNode, Int> = PriorityMap { a, b ->
        a.second.compareTo(b.second)
    }
    private val prevNode: MutableMap<GraphNode, GraphNode> = mutableMapOf()
    private val visited: MutableMap<GraphNode, Boolean> = mutableMapOf()

    init {
        assert(minSteps < maxSteps)
        // Init graph state for Dijkstra
        for (x in 0 ..< X) {
            for (y in 0 ..< Y) {
                val nodes =
                    if (x == 0 && y == 0) { // top left
                        listOf(Direction.RIGHT, Direction.DOWN /* doesn't matter */).map { d ->
                            GraphNode(0 to 0, d, 0)
                        }
                    } else if (x + 1 == X && y + 1 == Y) { // bottom right
                        listOf(Direction.RIGHT, Direction.DOWN /* matter */).flatMap { d ->
                            (minSteps..maxSteps).map { s -> GraphNode(x to y, d, s) }
                        }
                    } else { // others
                        ALL_DIRECTION.flatMap { d ->
                            (1..maxSteps).map { s -> GraphNode(x to y, d, s) }
                        }
                    }
                nodes.forEach { visited[it] = false }
            }
        }
        costs.add(GraphNode(0 to 0, Direction.RIGHT, 0), 0)
    }

    private fun printPath(start: GraphNode) {
        println(start)
        if (start.coord.atOrigin()) {
            return
        }
        val prev = prevNode[start]
        if (prev != null) {
            printPath(prev)
        }
    }

    fun shortestPath(): Int {
        while (costs.isNotEmpty()) {
            val (node, cost) = costs.poll()
            val (nX, nY) = node.coord
            if (nX + 1 == X && nY + 1 == Y) {
                return cost
            }
            val neighbors = findNeighbors(node)
            neighbors.forEach { neighbor ->
                val (x, y) = neighbor.coord
                val oldCost = costs.get(neighbor) ?: Int.MAX_VALUE
                val newCost = graph[y][x] + cost
                if (newCost < oldCost) {
                    costs.add(neighbor, newCost)
                    prevNode[neighbor] = node
                }
            }
            visited[node] = true
        }
        throw Exception("This should not happens")
    }

    private fun findNeighbors(node: GraphNode): List<GraphNode> {
        val (coord, travelDir, stepsBeforeLastTurn) = node
        val (x, y) = coord
        return listOfNotNull(
            formValidNode(x, y - 1, Direction.UP, travelDir, stepsBeforeLastTurn),
            formValidNode(x, y + 1, Direction.DOWN, travelDir, stepsBeforeLastTurn),
            formValidNode(x - 1, y, Direction.LEFT, travelDir, stepsBeforeLastTurn),
            formValidNode(x + 1, y, Direction.RIGHT, travelDir, stepsBeforeLastTurn)
        )
    }

    private fun formValidNode(
        x: Int,
        y: Int,
        travelDir: Direction,
        fromDir: Direction,
        stepsBeforeLastTurn: Int
    ): GraphNode? {
        if (stepsBeforeLastTurn < minSteps && travelDir != fromDir) {
            return null // You shall not turn!
        }
        val newSteps = if (travelDir == fromDir) stepsBeforeLastTurn + 1 else 1
        val node = GraphNode(x to y, travelDir, newSteps)
        return if (
            x in 0 ..< X &&
                y in 0 ..< Y &&
                newSteps <= maxSteps &&
                fromDir.opposite() != travelDir &&
                (visited[node] == false)
        ) {
            node
        } else null
    }
}

private fun List<String>.parseInput(): List<List<Int>> = map { str -> str.map { it.digitToInt() } }
