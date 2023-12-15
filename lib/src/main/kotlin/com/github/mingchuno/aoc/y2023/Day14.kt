package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.transpose

object Day14 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return PlatformV2(inputs).computeTiltOnceLoad()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return PlatformV2(inputs).run()
    }
}

/** Class that contain all the state and functions need for part 1 & 2 */
class PlatformV2(platform: List<String>) {
    private val charsMap = platform.map { it.toList() }

    private val SIZE = platform.size

    // x -> y
    private val cubesInX: List<List<Int>> = charsMap.transpose().cubeCoordinates()

    // y -> x
    private val cubesInY: List<List<Int>> = charsMap.cubeCoordinates()

    // x -> y
    private val initialRocks: List<List<Int>> = charsMap.transpose().rockCoordinate()

    private fun List<List<Char>>.cubeCoordinates(): List<List<Int>> = coordinateOf('#')

    private fun List<List<Char>>.rockCoordinate(): List<List<Int>> = coordinateOf('O')

    private fun List<List<Char>>.coordinateOf(char: Char): List<List<Int>> = map { xs ->
        xs.mapIndexedNotNull { x, c -> if (c == char) x else null }
    }

    /** Run enough cycles to deduce the pattern and compute ans */
    fun run(): Int {
        var res = initialRocks
        val reverseScoreMap = mutableMapOf<Int, MutableList<Int>>()
        var i = 0
        while (true) {
            i++
            res = cycle(res)
            val score = res.sumOf { it.computeScore() }
            reverseScoreMap[score]?.add(i) ?: run { reverseScoreMap[score] = mutableListOf(i) }
            reverseScoreMap.findPotentialAns()?.let {
                return it
            }
        }
    }

    /** Find potential loop and compute score (key of the map) from it */
    private fun Map<Int, List<Int>>.findPotentialAns(iterTarget: Int = 1000000000): Int? {
        // 10 give us a good confident that a pattern occur.
        val cycles = this.filterValues { it.size >= 10 }
        val possibleCycleCount =
            cycles
                .mapValues { (_, iter) -> iter.zipWithNext { a, b -> b - a }.toSet() }
                .filterValues { it.size == 1 }
        if (possibleCycleCount.isEmpty()) {
            return null
        }
        val cycle = possibleCycleCount.values.first().first()
        val startOffset = cycles.values.flatten().min()
        val answerCycle = startOffset + (iterTarget - startOffset).mod(cycle)
        println("startOffset=$startOffset;cycle=$cycle;repeatingCycle=$answerCycle")
        return this.firstNotNullOf { (score, vs) -> if (vs.contains(answerCycle)) score else null }
    }

    /** This is for part 1. I consolidate part 1 using the same approach as part 2 */
    fun computeTiltOnceLoad(): Int {
        return initialRocks.tiltNorth().mapIndexed { y, xs -> xs.sumOf { SIZE - y } }.sum()
    }

    // y-axis
    private fun List<Int>.computeScore(): Int = sumOf { idx -> SIZE - idx }

    private fun cycle(input: List<List<Int>>): List<List<Int>> {
        return input.tiltNorth().tiltWest().tiltSouth().tiltEast()
    }

    private fun tilt(
        currentIdx: Int,
        rockIdx: Int,
        rocks: List<Int>,
        cubeIdx: Int,
        cubes: List<Int>,
        results: MutableList<Int>
    ): MutableList<Int> {
        if (rockIdx == rocks.size) {
            return results
        }
        val rock = rocks[rockIdx]
        return if (cubeIdx == cubes.size || rock < cubes[cubeIdx]) {
            tilt(
                currentIdx + 1,
                rockIdx + 1,
                rocks,
                cubeIdx,
                cubes,
                results.also { it.add(currentIdx) }
            )
        } else {
            tilt(cubes[cubeIdx] + 1, rockIdx, rocks, cubeIdx + 1, cubes, results)
        }
    }

    /**
     * Every Int in the list is the index from x or y axis. This function reverse and sort it so
     * that it could be applied to the same tilt function for South and East case
     */
    private fun List<Int>.reverseP(): List<Int> = map { SIZE - it - 1 }.reversed()

    // x -> y => y -> x
    private fun List<List<Int>>.tiltNorth(): List<List<Int>> {
        val results: List<MutableList<Int>> = List(SIZE) { mutableListOf() }
        forEachIndexed { x, ys ->
            val cubes = cubesInX[x]
            val tilted = tilt(0, 0, ys, 0, cubes, mutableListOf())
            tilted.forEach { y -> results[y].add(x) }
        }
        return results
    }

    // y -> x => x -> y
    private fun List<List<Int>>.tiltWest(): List<List<Int>> {
        val results: List<MutableList<Int>> = List(SIZE) { mutableListOf() }
        forEachIndexed { y, xs ->
            val cubes = cubesInY[y]
            val tilted = tilt(0, 0, xs, 0, cubes, mutableListOf())
            tilted.forEach { x -> results[x].add(y) }
        }
        return results
    }

    // x -> y => y -> x
    private fun List<List<Int>>.tiltSouth(): List<List<Int>> {
        val results: List<MutableList<Int>> = List(SIZE) { mutableListOf() }
        forEachIndexed { x, ys ->
            val cubes = cubesInX[x]
            val tilted = tilt(0, 0, ys.reverseP(), 0, cubes.reverseP(), mutableListOf()).reverseP()
            tilted.forEach { y -> results[y].add(x) }
        }
        return results
    }

    // y -> x => x -> y
    private fun List<List<Int>>.tiltEast(): List<List<Int>> {
        val results: List<MutableList<Int>> = List(SIZE) { mutableListOf() }
        forEachIndexed { y, xs ->
            val cubes = cubesInY[y]
            val tilted = tilt(0, 0, xs.reverseP(), 0, cubes.reverseP(), mutableListOf()).reverseP()
            tilted.forEach { x -> results[x].add(y) }
        }
        return results
    }
}
