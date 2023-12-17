package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.Direction
import com.github.mingchuno.aoc.utils.opposite
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day10 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return DFS(inputs).run()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return DFS(inputs).runPart2()
    }

    private fun Char.`in`(vararg chars: Char): Boolean = chars.contains(this)

    private fun Char.inTop(): Boolean = `in`(VERTICAL, F, `7`)

    private fun Char.inBottom(): Boolean = `in`(VERTICAL, J, L)

    private fun Char.inLeft(): Boolean = `in`(HORIZONTAL, L, F)

    private fun Char.inRight(): Boolean = `in`(HORIZONTAL, J, `7`)

    private const val START = 'S'
    private const val VERTICAL = '|'
    private const val HORIZONTAL = '-'
    private const val L = 'L'
    private const val NE = L
    private const val J = 'J'
    private const val NW = J
    private const val `7` = '7'
    private const val SW = `7`
    private const val F = 'F'
    private const val SE = F
    private const val GROUND = '.'

    private class DFS(inputs: List<String>) {
        private val X = inputs.first().length
        private val Y = inputs.size
        private val startingPos = findStartingPos(inputs)
        private val startingChar = decideStartingChar(inputs, startingPos)
        private val realInput = inputs.assumePipe(startingPos, startingChar)
        // state
        private val visited = initVisited(inputs)

        private fun initVisited(inputs: List<String>): MutableList<MutableList<Boolean>> =
            inputs.map { input -> input.map { false }.toMutableList() }.toMutableList()

        fun run(): Int {
            val nextDirection = startingChar.nextDirection().first()
            return dfs(nextDirection.toPos(startingPos), steps = 1, nextDirection) / 2
        }

        fun runPart2(): Int {
            run()
            // state
            val areaMap = cleanInputs(realInput)
            for (x in 0 ..< X) {
                for (y in 0 ..< Y) {
                    if (areaMap[y][x] == GROUND) {
                        // decide if it is `I` or `O` by odd/even
                        val isPosInside = isInside(x, y, areaMap)
                        // Then, use DFS again to fill the partial area map
                        fillAreaWith(x, y, if (isPosInside) 'I' else 'O', areaMap)
                    }
                }
            }
            return areaMap.sumOf { it.count { char -> char == 'I' } }
        }

        private fun isInside(x: Int, y: Int, areaMap: List<List<Char>>): Boolean {
            var count = 0
            for (i in 0 ..< x) {
                if (areaMap[y][i].inTop()) {
                    count++
                }
            }
            return count.mod(2) == 1
        }

        private fun fillAreaWith(
            x: Int,
            y: Int,
            fill: Char,
            areaMap: MutableList<MutableList<Char>>
        ) {
            if (areaMap[y][x] == GROUND) {
                areaMap[y][x] = fill
                listOf(x to y - 1, x + 1 to y, x - 1 to y, x to y + 1) // Scan T,B,L,R
                    .filter { validXY(it) } // Boundary check
                    .forEach { fillAreaWith(it.first, it.second, fill, areaMap) }
            }
        }

        /** map all non-path tile into '.' */
        private fun cleanInputs(inputs: List<String>): MutableList<MutableList<Char>> {
            return inputs
                .mapIndexed { y, input ->
                    input.mapIndexed { x, c -> if (visited[y][x]) c else GROUND }.toMutableList()
                }
                .toMutableList()
        }

        private fun validXY(it: Pair<Int, Int>): Boolean {
            val (x, y) = it
            return (x in 0 ..< X) && (y in 0 ..< Y)
        }

        private fun findStartingPos(inputs: List<String>): Pair<Int, Int> {
            inputs.forEachIndexed { y, input ->
                input.forEachIndexed { x, c ->
                    if (c == START) {
                        return@findStartingPos x to y
                    }
                }
            }
            throw Exception("Starting pos not found!")
        }

        private fun decideStartingChar(inputs: List<String>, startingPos: Pair<Int, Int>): Char {
            val (x, y) = startingPos
            return decideStartingChar(
                top = if (validXY(x to y - 1)) inputs[y - 1][x] else null,
                right = if (validXY(x + 1 to y)) inputs[y][x + 1] else null,
                bottom = if (validXY(x to y + 1)) inputs[y + 1][x] else null,
                left = if (validXY(x - 1 to y)) inputs[y][x - 1] else null
            )
        }

        private fun decideStartingChar(top: Char?, right: Char?, bottom: Char?, left: Char?): Char {
            return if (top != null && bottom != null && top.inTop() && bottom.inBottom()) {
                VERTICAL
            } else if (left != null && right != null && left.inLeft() && right.inRight()) {
                HORIZONTAL
            } else if (left != null && top != null && left.inLeft() && top.inTop()) {
                J
            } else if (left != null && bottom != null && left.inLeft() && bottom.inBottom()) {
                `7`
            } else if (right != null && top != null && right.inRight() && top.inTop()) {
                L
            } else if (right != null && bottom != null && right.inRight() && bottom.inBottom()) {
                F
            } else {
                throw Exception("Cannot decide starting pipe")
            }
        }

        private tailrec fun dfs(
            pos: Pair<Int, Int>,
            steps: Int,
            previousDirection: Direction
        ): Int {
            val (x, y) = pos
            visited[y][x] = true
            if (pos == startingPos) {
                println("arrive starting pos again:$pos with steps=$steps")
                return steps // back to starting pos again
            }
            val currentNode = realInput[y][x]
            val possibleDirections = currentNode.nextDirection()
            val nextDirection = possibleDirections.first { it != previousDirection.opposite() }
            val nextPos = nextDirection.toPos(pos)
            return dfs(nextPos, steps + 1, nextDirection)
        }
    }

    private fun Char.nextDirection(): List<Direction> {
        return when (this) {
            VERTICAL -> listOf(Direction.UP, Direction.DOWN)
            HORIZONTAL -> listOf(Direction.LEFT, Direction.RIGHT)
            NE -> listOf(Direction.UP, Direction.RIGHT)
            NW -> listOf(Direction.UP, Direction.LEFT)
            SW -> listOf(Direction.DOWN, Direction.LEFT)
            SE -> listOf(Direction.DOWN, Direction.RIGHT)
            GROUND -> throw Exception("Should not arrive here!")
            else -> throw Exception("current node is unknown:${this}")
        }
    }

    private fun Direction.toPos(start: Pair<Int, Int>): Pair<Int, Int> {
        val (x, y) = start
        return when (this) {
            Direction.UP -> x to y - 1
            Direction.RIGHT -> x + 1 to y
            Direction.LEFT -> x - 1 to y
            Direction.DOWN -> x to y + 1
        }
    }

    private fun List<String>.assumePipe(pos: Pair<Int, Int>, pipe: Char): List<String> {
        val (x, y) = pos
        return mapIndexed { yIdx, s ->
            if (yIdx != y) s else StringBuilder(s).apply { setCharAt(x, pipe) }.toString()
        }
    }
}
