package com.github.mingchuno.aoc.y2023.d23

import com.github.mingchuno.aoc.utils.Coord
import com.github.mingchuno.aoc.utils.ThisShouldNotHappenException

abstract class WalkForestDFS(private val inputs: List<List<Char>>) {
    private val X = inputs.first().size
    private val Y = inputs.size
    private val startPos: Coord = inputs.first().indexOfFirst { it == '.' } to 0
    private val endPos: Coord = inputs.last().indexOfFirst { it == '.' } to (Y - 1)

    fun compute(): Int {
        return dfs(startPos, 0, setOf())
    }

    private fun dfs(coord: Coord, steps: Int, visited: Set<Coord>): Int {
        if (coord == endPos) {
            return steps
        }
        val newVisited = visited + coord
        val neighbors = findNeighbors(coord, newVisited)
        return neighbors.maxOfOrNull { neighbor -> dfs(neighbor, steps + 1, newVisited) } ?: 0
    }

    protected abstract fun findNeighbors(coord: Coord, visited: Set<Coord>): List<Coord>

    protected fun forValidCoord(x: Int, y: Int, visited: Set<Coord>): Coord? {
        return if (x in 0 ..< X && y in 0 ..< Y && inputs[y][x] != '#' && !visited.contains(x to y))
            x to y
        else null
    }
}

class WalkForestDFSPart1(private val inputs: List<List<Char>>) : WalkForestDFS(inputs) {
    override fun findNeighbors(coord: Coord, visited: Set<Coord>): List<Coord> {
        val (x, y) = coord
        return when (inputs[y][x]) {
            '.' -> {
                listOfNotNull(
                    forValidCoord(x, y - 1, visited),
                    forValidCoord(x, y + 1, visited),
                    forValidCoord(x - 1, y, visited),
                    forValidCoord(x + 1, y, visited),
                )
            }
            '>' -> listOfNotNull(forValidCoord(x + 1, y, visited))
            '<' -> listOfNotNull(forValidCoord(x - 1, y, visited))
            '^' -> listOfNotNull(forValidCoord(x, y - 1, visited))
            'v' -> listOfNotNull(forValidCoord(x, y + 1, visited))
            else -> throw ThisShouldNotHappenException()
        }
    }
}

class WalkForestDFSPart2(inputs: List<List<Char>>) : WalkForestDFS(inputs) {
    override fun findNeighbors(coord: Coord, visited: Set<Coord>): List<Coord> {
        val (x, y) = coord
        return listOfNotNull(
            forValidCoord(x, y - 1, visited),
            forValidCoord(x, y + 1, visited),
            forValidCoord(x - 1, y, visited),
            forValidCoord(x + 1, y, visited),
        )
    }
}
