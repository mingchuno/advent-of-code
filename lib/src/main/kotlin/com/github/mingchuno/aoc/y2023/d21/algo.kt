package com.github.mingchuno.aoc.y2023.d21

import com.github.mingchuno.aoc.utils.Coord
import com.github.mingchuno.aoc.utils.ThisShouldNotHappenException

data class Node(val coord: Coord, val steps: Int)

abstract class BFSBase(inputs: List<List<Char>>, private val maxStep: Int) {
    protected val X = inputs.first().size
    protected val Y = inputs.size
    private val memo = mutableSetOf<Node>()
    private val results = mutableSetOf<Coord>()
    private val startingPos = inputs.findStartingPos()

    fun walk(): Int {
        dfsMemo(Node(startingPos, 0))
        return results.size
    }

    private fun dfsMemo(node: Node) {
        if (!memo.contains(node)) {
            memo.add(node)
            dfs(node)
        }
    }

    protected abstract fun isValid(x: Int, y: Int): Coord?

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
            .map { n -> Node(n, newStep) }
            .toSet()
    }
}

class WalkGarden(private val inputs: List<List<Char>>, maxStep: Int) : BFSBase(inputs, maxStep) {
    override fun isValid(x: Int, y: Int): Coord? {
        return if (x in 0 ..< X && y in 0 ..< Y && inputs[y][x] != '#') x to y else null
    }
}

class WalkInfiniteGarden(private val inputs: List<List<Char>>, maxStep: Int) :
    BFSBase(inputs, maxStep) {
    override fun isValid(x: Int, y: Int): Coord? {
        return if (inputs[y.mod(Y)][x.mod(X)] != '#') x to y else null
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
    throw ThisShouldNotHappenException()
}
