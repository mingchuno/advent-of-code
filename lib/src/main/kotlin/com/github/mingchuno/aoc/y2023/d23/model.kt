package com.github.mingchuno.aoc.y2023.d23

import com.github.mingchuno.aoc.utils.*

data class GraphNode(val coord: Coord, val visited: Set<Coord>)

abstract class WalkForestBFSBase(private val inputs: List<List<Char>>) {
    private val X = inputs.first().size
    private val Y = inputs.size
    private val startPos: Coord = inputs.first().indexOfFirst { it == '.' } to 0
    private val endPos: Coord = inputs.last().indexOfFirst { it == '.' } to (Y - 1)

    private val costs = PriorityMap<GraphNode, Int> { a, b -> b.second.compareTo(a.second) }

    fun compute(): Int {
        // init
        costs.add(GraphNode(startPos, setOf(startPos)), 0)
        // loop
        while (costs.isNotEmpty()) {
            val (node, cost) = costs.poll()
            val (coord, visited) = node
            if (endPos == coord) {
                return cost
            }
            val neighbors = findNeighbors(node)
            neighbors.forEach { neighbor ->
                val newNode = GraphNode(neighbor, visited + neighbor)
                val oldCost = costs.get(newNode) ?: 0
                val newCost = cost + 1
                if (newCost > oldCost) {
                    costs.add(newNode, newCost)
                }
            }
        }
        throw ThisShouldNotHappenException()
    }

    protected abstract fun findNeighbors(node: GraphNode): List<Coord>

    protected fun forValidCoord(x: Int, y: Int, visited: Set<Coord>): Coord? {
        return if (x in 0 ..< X && y in 0 ..< Y && inputs[y][x] != '#' && !visited.contains(x to y))
            x to y
        else null
    }
}

class WalkForestBFSPart1(private val inputs: List<List<Char>>) : WalkForestBFSBase(inputs) {
    override fun findNeighbors(node: GraphNode): List<Coord> {
        val (coord, visited) = node
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

class WalkForestBFSPart2(inputs: List<List<Char>>) : WalkForestBFSBase(inputs) {
    override fun findNeighbors(node: GraphNode): List<Coord> {
        val (coord, visited) = node
        val (x, y) = coord
        return listOfNotNull(
            forValidCoord(x, y - 1, visited),
            forValidCoord(x, y + 1, visited),
            forValidCoord(x - 1, y, visited),
            forValidCoord(x + 1, y, visited),
        )
    }
}

abstract class WalkForestDFS(private val inputs: List<List<Char>>) {
    private val X = inputs.first().size
    private val Y = inputs.size
    private val startPos: Coord = inputs.first().indexOfFirst { it == '.' } to 0
    private val endPos: Coord = inputs.last().indexOfFirst { it == '.' } to (Y - 1)

    fun compute(): Int {
        return dfs(startPos, Direction.UP, 0)
    }

    private fun dfs(coord: Coord, fromDirection: Direction, steps: Int): Int {
        if (coord == endPos) {
            return steps
        }
        var stepsDelta = 1
        var neighbors = findNeighbors(coord, fromDirection)
        while (neighbors.size == 1) {
            val (neig, nextDir) = neighbors.first()
            neighbors = findNeighbors(neig, nextDir)
            stepsDelta++
        }
        println("coord=$coord;fromDirection=$fromDirection;steps=$steps")
        return neighbors.maxOfOrNull { (neighbor, toDirection) ->
            println("neighbor=$neighbor;toDirection=$toDirection;steps=${steps + stepsDelta}")
            dfs(neighbor, toDirection, steps + stepsDelta)
        } ?: 0
    }

    protected abstract fun findNeighbors(
        coord: Coord,
        fromDirection: Direction
    ): List<Pair<Coord, Direction>>

    protected fun forValidCoord(
        x: Int,
        y: Int,
        fromDirection: Direction,
        toDirection: Direction
    ): Pair<Coord, Direction>? {
        return if (
            x in 0 ..< X && y in 0 ..< Y && inputs[y][x] != '#' && fromDirection != toDirection
        )
            (x to y) to toDirection.opposite()
        else null
    }
}

class WalkForestDFSPart1(private val inputs: List<List<Char>>) : WalkForestDFS(inputs) {
    override fun findNeighbors(
        coord: Coord,
        fromDirection: Direction
    ): List<Pair<Coord, Direction>> {
        val (x, y) = coord
        return when (inputs[y][x]) {
            '.' -> {
                listOfNotNull(
                    forValidCoord(x, y - 1, fromDirection, Direction.UP),
                    forValidCoord(x, y + 1, fromDirection, Direction.DOWN),
                    forValidCoord(x - 1, y, fromDirection, Direction.LEFT),
                    forValidCoord(x + 1, y, fromDirection, Direction.RIGHT),
                )
            }
            '>' -> listOfNotNull(forValidCoord(x + 1, y, fromDirection, Direction.RIGHT))
            '<' -> listOfNotNull(forValidCoord(x - 1, y, fromDirection, Direction.LEFT))
            '^' -> listOfNotNull(forValidCoord(x, y - 1, fromDirection, Direction.RIGHT))
            'v' -> listOfNotNull(forValidCoord(x, y + 1, fromDirection, Direction.DOWN))
            else -> throw ThisShouldNotHappenException()
        }
    }
}

class WalkForestDFSPart2(inputs: List<List<Char>>) : WalkForestDFS(inputs) {
    override fun findNeighbors(
        coord: Coord,
        fromDirection: Direction
    ): List<Pair<Coord, Direction>> {
        val (x, y) = coord
        return listOfNotNull(
            forValidCoord(x, y - 1, fromDirection, Direction.UP),
            forValidCoord(x, y + 1, fromDirection, Direction.DOWN),
            forValidCoord(x - 1, y, fromDirection, Direction.LEFT),
            forValidCoord(x + 1, y, fromDirection, Direction.RIGHT),
        )
    }
}
