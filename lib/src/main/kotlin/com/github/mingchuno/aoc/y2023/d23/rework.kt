package com.github.mingchuno.aoc.y2023.d23

import com.github.mingchuno.aoc.utils.Coord

class SimplifyBFS(private val inputs: List<List<Char>>) {
    private val X = inputs.first().size
    private val Y = inputs.size
    private val startPos: Coord = inputs.first().indexOfFirst { it == '.' } to 0
    private val endPos: Coord = inputs.last().indexOfFirst { it == '.' } to (Y - 1)
    private val neighborsMap = mutableMapOf<Coord, List<Coord>>()
    private val intersections = mutableListOf(startPos, endPos)
    private val graph = mutableMapOf<Coord, List<Pair<Coord, Int>>>()

    init {
        // Build neighbors and intersections
        outer@ for (x in 0 ..< X) {
            inner@ for (y in 0 ..< Y) {
                if (inputs[y][x] == '#') {
                    continue@inner
                }
                val neighbors =
                    listOfNotNull(
                        formValidCoord(x, y - 1),
                        formValidCoord(x, y + 1),
                        formValidCoord(x - 1, y),
                        formValidCoord(x + 1, y),
                    )
                neighborsMap[x to y] = neighbors
                if (neighbors.size >= 3) {
                    intersections.add(x to y)
                }
            }
        }
        // Build graph
        for (i in intersections) {
            for (n in neighborsMap[i]!!) {
                val (nextIntersection, distance) = intersectionDist(n, 1, setOf(i))
                graph[i] = (graph[i] ?: listOf()) + (nextIntersection to distance)
            }
        }
    }

    fun compute(): Int {
        return dfs(startPos, 0, setOf(startPos))
    }

    private fun dfs(current: Coord, steps: Int, visited: Set<Coord>): Int {
        if (current == endPos) {
            return steps
        }
        return graph[current]!!
            .filter { (to, _) -> !visited.contains(to) }
            .maxOfOrNull { (to, distance) -> dfs(to, steps + distance, visited + current) } ?: 0
    }

    private fun intersectionDist(
        current: Coord,
        distance: Int,
        visited: Set<Coord>
    ): Pair<Coord, Int> {
        if (intersections.contains(current)) {
            return current to distance
        }
        val next = (neighborsMap[current]!!).first { p -> !visited.contains(p) }
        return intersectionDist(next, distance + 1, visited + current)
    }

    private fun formValidCoord(x: Int, y: Int): Coord? {
        return if (x in 0 ..< X && y in 0 ..< Y && inputs[y][x] != '#') x to y else null
    }
}
