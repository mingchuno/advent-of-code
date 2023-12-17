package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.Coord
import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlin.math.abs

object Day17 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().map { str -> str.map { it.digitToInt() } }
        return Dijkstra(inputs).findShortestPath()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        TODO("Not yet implemented")
    }
}

private class Dijkstra(private val graph: List<List<Int>>) {
    private val SIZE = graph.size

    private val debug = true

    private val visited: List<MutableList<Boolean>> = List(SIZE) { MutableList(SIZE) { false } }
    private val prevNode: MutableMap<Coord, Coord> = mutableMapOf()
    private val costs: MutableMap<Coord, Int> by lazy {
        val map = mutableMapOf<Coord, Int>()
        for (x in 0 ..< SIZE) {
            for (y in 0 ..< SIZE) {
                map[x to y] = if (x == 0 && y == 0) 0 else Int.MAX_VALUE
            }
        }
        map
    }

    private fun printPath(x: Int = SIZE - 1, y: Int = SIZE - 1) {
        if (x == 0 && y == 0) {
            return
        }
        val prev = prevNode[x to y]
        if (prev != null) {
            val (px, py) = prev
            println("x=$px;y=$py;cost=${graph[py][px]}")
            printPath(px, py)
        }
    }

    fun findShortestPath(): Int {
        while (costs.isNotEmpty()) {
            val (coord, cost) = costs.minBy { (_, cost) -> cost }
            val (nX, nY) = coord
            costs.remove(coord)
            if (nX + 1 == SIZE && nY + 1 == SIZE) {
                if (debug) printPath()
                return cost
            }
            val neighbors = findNeighbor(nX, nY)
            if (debug) println("nX=$nX;nY=$nY;neighbors=$neighbors")
            neighbors.forEach { (x, y) ->
                val newCost = graph[y][x] + cost
                val oldCost = costs[x to y]!!
                if (newCost < oldCost) {
                    costs[x to y] = newCost
                    prevNode[x to y] = nX to nY
                }
            }
            visited[nY][nX] = true
        }
        throw Exception("Error!")
    }

    private fun findNeighbor(x: Int, y: Int): List<Coord> {
        val maxSameSteps = 3
        val neighbor = mutableListOf<Coord>()
        val back3Steps = backtrack(x to y, maxSameSteps)
        if (debug) println("x=$x;y=$y;back3Steps=$back3Steps")
        val (invalidXDirection, invalidYDirection) =
            if (back3Steps == null) {
                false to false
            } else {
                val (px, py) = back3Steps
                (abs(px - x) == maxSameSteps && py == y) to (abs(py - y) == maxSameSteps && px == x)
            }
        if (validCoord(x + 1, y) && !invalidXDirection) neighbor.add(x + 1 to y)
        if (validCoord(x - 1, y) && !invalidXDirection) neighbor.add(x - 1 to y)
        if (validCoord(x, y + 1) && !invalidYDirection) neighbor.add(x to y + 1)
        if (validCoord(x, y - 1) && !invalidYDirection) neighbor.add(x to y - 1)
        return neighbor
    }

    private fun backtrack(from: Coord, count: Int): Coord? {
        if (count == 0) return from
        return prevNode[from]?.let { backtrack(it, count - 1) }
    }

    private fun validCoord(x: Int, y: Int): Boolean =
        x in 0 ..< SIZE && y in 0 ..< SIZE && !visited[y][x]
}
