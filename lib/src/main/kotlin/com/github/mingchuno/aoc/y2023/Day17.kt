package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.Coord
import com.github.mingchuno.aoc.utils.Direction
import com.github.mingchuno.aoc.utils.opposite
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day17 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().parseInput()
        return DijkstraV2(inputs).shortestPath()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().parseInput()
        TODO("Not yet implemented")
    }
}

private data class GraphNode(
    val coord: Coord,
    val travelDir: Direction,
    val stepsBeforeLastTurn: Int
)

private val ALL_DIRECTION = listOf(Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT)

private class DijkstraV2(private val graph: List<List<Int>>) {
    private val X = graph.first().size
    private val Y = graph.size
    private val costs: MutableMap<GraphNode, Int> = mutableMapOf()
    private val prevNode: MutableMap<GraphNode, GraphNode> = mutableMapOf()
    private val visited: MutableMap<GraphNode, Boolean> = mutableMapOf()

    init {
        for (x in 0 ..< X) {
            for (y in 0 ..< Y) {
                val cost = if (x == 0 && y == 0) 0 else Int.MAX_VALUE
                val nodes =
                    if (x == 0 && y == 0) {
                        listOf(Direction.RIGHT, Direction.DOWN /* doesn't matter */).map { d ->
                            GraphNode(0 to 0, d, 0)
                        }
                    } else {
                        ALL_DIRECTION.flatMap { d -> (1..3).map { s -> GraphNode(x to y, d, s) } }
                    }
                nodes.forEach {
                    costs[it] = cost
                    visited[it] = false
                }
            }
        }
    }

    private fun printPath(start: GraphNode) {
        val (x, y) = start.coord
        println(start)
        if (x == 0 && y == 0) {
            return
        }
        val prev = prevNode[start]
        if (prev != null) {
            printPath(prev)
        }
    }

    fun shortestPath(): Int {
        while (costs.isNotEmpty()) {
            val (node, cost) = costs.minBy { (_, cost) -> cost }
            val (nX, nY) = node.coord
            costs.remove(node)
            if (nX + 1 == X && nY + 1 == Y) { // FIXME: condition is correct?
                return cost
            }
            val neighbors = findNeighbors(node)
            neighbors.forEach { neighbor ->
                val (x, y) = neighbor.coord
                val newCost = graph[y][x] + cost
                val oldCost = costs[neighbor]!!
                if (newCost < oldCost) {
                    costs[neighbor] = newCost
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
        val newSteps = if (travelDir == fromDir) stepsBeforeLastTurn + 1 else 1
        val node = GraphNode(x to y, travelDir, newSteps)
        return if (
            x in 0 ..< X &&
                y in 0 ..< Y &&
                newSteps <= 3 &&
                fromDir.opposite() != travelDir &&
                (visited[node] == false)
        ) {
            node
        } else null
    }
}

private fun List<String>.parseInput(): List<List<Int>> = map { str -> str.map { it.digitToInt() } }
