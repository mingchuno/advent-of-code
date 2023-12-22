package com.github.mingchuno.aoc.y2023.d21

import com.github.mingchuno.aoc.utils.Coord

data class Node(val coord: Coord, val steps: Int)

class BFS(private val inputs: List<List<Char>>, private val maxStep: Int) {
    private val X = inputs.first().size
    private val Y = inputs.size
    private val memo = mutableMapOf<Node, Set<Coord>>()

    fun computePart1(): Int {
        val coord = inputs.findStartingPos()
        return dfsMemo(Node(coord, 0)).size
    }

    private fun dfsMemo(node: Node): Set<Coord> {
        return memo[node] ?: dfs(node).also { memo[node] = it }
    }

    private fun dfs(node: Node): Set<Coord> {
        val (coord, steps) = node
        if (steps == maxStep) {
            return setOf(coord)
        }
        return node.findNeighbors().flatMap { dfsMemo(it) }.toSet()
    }

    private fun Node.findNeighbors(): List<Node> {
        val (x, y) = coord
        return listOfNotNull(
                isValid(x, y - 1),
                isValid(x, y + 1),
                isValid(x + 1, y),
                isValid(x - 1, y),
            )
            .map { Node(it, steps + 1) }
    }

    private fun isValid(x: Int, y: Int): Coord? {
        return if (x in 0 ..< X && y in 0 ..< Y && inputs[y][x] != '#') x to y else null
    }
}

class BFSPart2(private val inputs: List<List<Char>>, private val maxStep: Int) {
    private val X = inputs.first().size
    private val Y = inputs.size
    private val memo = mutableSetOf<Node>()
    private val results = mutableSetOf<Coord>()
    private val startingPos = inputs.findStartingPos()

    fun computePart2(): Int {
        dfsMemo(Node(startingPos, 0))
        // inside center square!
        val firstSq = results.count { (x, y) -> x in 0 ..< X && y in 0 ..< Y }
        val outside = results.size - firstSq
        println("firstSq=$firstSq;outside=$outside")
        return results.size
    }

    private fun dfsMemo(node: Node) {
        if (!memo.contains(node)) {
            memo.add(node)
            dfs(node)
        }
    }

    private fun isStartingPos(x: Int, y: Int): Boolean = inputs[y.mod(Y)][x.mod(X)] == 'S'

    private fun isNotWallPos(x: Int, y: Int): Boolean = inputs[y.mod(Y)][x.mod(X)] != '#'

    private fun dfs(node: Node) {
        val (coord, steps) = node
        if (steps == maxStep) {
            results.add(coord)
        } else {
            node.findNeighbors().forEach { dfsMemo(it) }
        }
    }

    private fun Node.findNeighbors(): Set<Node> {
        val (x, y) = coord
        val newStep = steps + 1
        return listOfNotNull(
                isValid(x, y - 1),
                isValid(x, y + 1),
                isValid(x + 1, y),
                isValid(x - 1, y),
            )
            .map { n ->
                val (nx, ny) = n
                if (!isStartingPos(nx, ny)) {
                    Node(n, newStep)
                } else {
                    val q = maxStep / newStep
                    val (sX, sY) = startingPos
                    val xx = sX + (nx - sX) * q
                    val yy = sY + (ny - sY) * q
                    Node(xx to yy, q * newStep)
                }
            }
            .toSet()
    }

    private fun isValid(x: Int, y: Int): Coord? {
        return if (isNotWallPos(x, y)) x to y else null
    }
}

private fun List<List<Char>>.findStartingPos(): Coord {
    for (x in this.first().indices) {
        for (y in this.first().indices) {
            if (this[y][x] == 'S') {
                return x to y
            }
        }
    }
    throw Exception("This should not happen!")
}
