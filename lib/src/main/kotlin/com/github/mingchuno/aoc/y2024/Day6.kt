package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.*

object Day6 : Problem<Int> {

    override fun computePart1(inputFile: String): Int {
        // Init state
        val input = inputFile.readFileFromResource().to2DChars()
        val visited = mutableSetOf<Coord>()
        val maze = input.toMaze()

        // Calculate & set initial state
        val pos = maze.findStartingPos('^', '>', '<', 'v')!!
        maze.setCurrentPos(pos)
        var direction = maze.currentCell.direction()
        var next: Maze.NextCell?

        // Start walking
        do {
            visited.add(maze.currentPos)
            next =
                when (direction) {
                    Direction.UP -> maze.walkUp(1)
                    Direction.DOWN -> maze.walkDown(1)
                    Direction.LEFT -> maze.walkLeft(1)
                    Direction.RIGHT -> maze.walkRight(1)
                }
            if (next != null) {
                if (next.cell == '#') {
                    direction = direction.turnRight()
                } else {
                    maze.setCurrentPos(next.x, next.y)
                }
            }
        } while (next != null)

        return visited.size
    }

    override fun computePart2(inputFile: String): Int {
        val input = inputFile.readFileFromResource().to2DChars()
        val maze = input.toMaze()
        val startPos = maze.findStartingPos('^', '>', '<', 'v')!!

        var result = 0
        for (y in input.indices) {
            for (x in input[0].indices) {
                if (input[y][x] != '.') {
                    // Should not proceed
                    continue
                }
                // Reset state
                maze.setCurrentPos(startPos)
                var direction = maze.currentCell.direction()
                var next: Maze.NextCell?
                val visited = mutableSetOf<Pair<Coord, Direction>>()

                loop@ do {
                    next =
                        when (direction) {
                            Direction.UP -> maze.walkUp(1)
                            Direction.DOWN -> maze.walkDown(1)
                            Direction.LEFT -> maze.walkLeft(1)
                            Direction.RIGHT -> maze.walkRight(1)
                        }
                    if (next != null) {
                        if (visited.contains(Pair(next.pos, direction))) {
                            result++
                            break@loop
                        } else {
                            visited.add(Pair(next.pos, direction))
                        }

                        if (next.cell == '#' || next.pos.equals(x, y)) {
                            direction = direction.turnRight()
                        } else {
                            maze.setCurrentPos(next.pos)
                        }
                    }
                } while (next != null)
            }
        }
        return result
    }
}
