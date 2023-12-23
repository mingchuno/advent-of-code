package com.github.mingchuno.aoc.y2023.d23

import com.github.mingchuno.aoc.utils.Coord
import com.github.mingchuno.aoc.utils.PriorityMap
import com.github.mingchuno.aoc.utils.ThisShouldNotHappenException

class WalkForest(private val inputs: List<List<Char>>) {
    private val X = inputs.first().size
    private val Y = inputs.size
    private val startPos: Coord = inputs.first().indexOfFirst { it == '.' } to 0
    private val endPos: Coord = inputs.last().indexOfFirst { it == '.' } to (Y - 1)

    private val costs = PriorityMap<Coord, Int> { a, b -> b.second.compareTo(a.second) }
    private val visited: MutableSet<Coord> = mutableSetOf()

    fun longestPath(): Int {
        // init
        costs.add(startPos, 0)
        visited.add(startPos)
        // loop
        while (costs.isNotEmpty()) {
            val (coord, cost) = costs.poll()
            if (endPos == coord) {
                return cost
            }
            val neighbors = findNeighbors(coord)
            neighbors.forEach { neighbor ->
                val oldCost = costs.get(neighbor) ?: 0
                val newCost = cost + 1
                if (newCost > oldCost) {
                    costs.add(neighbor, newCost)
                }
            }
            visited.add(coord)
        }
        throw ThisShouldNotHappenException()
    }

    private fun findNeighbors(coord: Coord): List<Coord> {
        val (x, y) = coord
        return when (inputs[y][x]) {
            '.' -> {
                listOfNotNull(
                    forValidCoord(x, y - 1),
                    forValidCoord(x, y + 1),
                    forValidCoord(x - 1, y),
                    forValidCoord(x + 1, y),
                )
            }
            '>' -> listOfNotNull(forValidCoord(x + 1, y))
            '<' -> listOfNotNull(forValidCoord(x - 1, y))
            '^' -> listOfNotNull(forValidCoord(x, y - 1))
            'v' -> listOfNotNull(forValidCoord(x, y + 1))
            else -> throw ThisShouldNotHappenException()
        }
    }

    private fun forValidCoord(x: Int, y: Int): Coord? {
        return if (x in 0 ..< X && y in 0 ..< Y && inputs[y][x] != '#' && !visited.contains(x to y))
            x to y
        else null
    }
}
