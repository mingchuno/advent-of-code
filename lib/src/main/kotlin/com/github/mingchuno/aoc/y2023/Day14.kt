package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.transpose

object Day14 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return inputs
            .map { it.toList() }
            .transpose()
            .sumOf { column -> PlatformColumn(column).computeLoad() }
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return PlatformV2(inputs).run()
    }
}

class PlatformV2(platform: List<String>) {
    private val charsMap = platform.map { it.toList() }

    private val SIZE = platform.size

    private val ROCK_COUNT: Int = platform.sumOf { it.count { c -> c == 'O' } }

    // x -> y
    private val cubesInX: List<List<Int>> by lazy { charsMap.transpose().cubeCoordinates() }

    // y -> x
    private val cubesInY: List<List<Int>> by lazy { charsMap.cubeCoordinates() }

    // x -> y
    private val initialRocks: List<List<Int>> = charsMap.transpose().rockCoordinate()

    private fun List<List<Char>>.cubeCoordinates(): List<List<Int>> = coordinateOf('#')

    private fun List<List<Char>>.rockCoordinate(): List<List<Int>> = coordinateOf('O')

    private fun List<List<Char>>.coordinateOf(char: Char): List<List<Int>> = map { xs ->
        xs.mapIndexedNotNull { x, c -> if (c == char) x else null }
    }

    fun run(): Int {
        var res = initialRocks
        val cache = mutableMapOf<String, List<List<Int>>>()
        for (i in 1..100) {
            val startKey = res.toKey()
            if (cache.containsKey(startKey)) {
                res = cache[startKey]!!
            } else {
                res = cycle(res)
                cache[startKey] = res
            }
            // val score = res.sumOf { it.computeScore() }
            // println("i=$i;score=$score")
        }
        return res.sumOf { it.computeScore() }
    }

    private fun List<List<Int>>.toKey(): String =
        mapIndexed { idx, it -> "$idx#${it.joinToString("-")}" }.joinToString(";")

    // y-axis
    private fun List<Int>.computeScore(): Int = sumOf { idx -> SIZE - idx }

    private fun cycle(input: List<List<Int>>): List<List<Int>> {
        return input.tiltNorth().tiltWest().tiltSouth().tiltEast()
    }

    private fun List<List<Int>>.assertCount() {
        assert(sumOf { it.size } == ROCK_COUNT)
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

    private fun List<Int>.reverseP(): List<Int> = map { SIZE - it - 1 }.reversed()

    // x -> y => y -> x
    fun List<List<Int>>.tiltNorth(): List<List<Int>> {
        val results: List<MutableList<Int>> = List(SIZE) { mutableListOf() }
        forEachIndexed { x, ys ->
            val cubes = cubesInX[x]
            val tilted = tilt(0, 0, ys, 0, cubes, mutableListOf())
            //            println("N:x=$x;tilted=$tilted")
            tilted.forEach { y -> results[y].add(x) }
        }
        return results
    }

    // y -> x => x -> y
    fun List<List<Int>>.tiltWest(): List<List<Int>> {
        val results: List<MutableList<Int>> = List(SIZE) { mutableListOf() }
        forEachIndexed { y, xs ->
            val cubes = cubesInY[y]
            val tilted = tilt(0, 0, xs, 0, cubes, mutableListOf())
            //            println("W:y=$y;tilted=$tilted")
            tilted.forEach { x -> results[x].add(y) }
        }
        return results
    }

    // x -> y => y -> x
    fun List<List<Int>>.tiltSouth(): List<List<Int>> {
        val results: List<MutableList<Int>> = List(SIZE) { mutableListOf() }
        forEachIndexed { x, ys ->
            val cubes = cubesInX[x]
            val tilted = tilt(0, 0, ys.reverseP(), 0, cubes.reverseP(), mutableListOf()).reverseP()
            //            println("S:x=$x;tilted=$tilted")
            tilted.forEach { y -> results[y].add(x) }
        }
        return results
    }

    // y -> x => x -> y
    fun List<List<Int>>.tiltEast(): List<List<Int>> {
        val results: List<MutableList<Int>> = List(SIZE) { mutableListOf() }
        forEachIndexed { y, xs ->
            val cubes = cubesInY[y]
            val tilted = tilt(0, 0, xs.reverseP(), 0, cubes.reverseP(), mutableListOf()).reverseP()
            //            println("E:y=$y;tilted=$tilted")
            tilted.forEach { x -> results[x].add(y) }
        }
        return results
    }
}

class PlatformColumn(private val column: List<Char>) {
    private val size = column.size

    fun computeLoad(): Int {
        return computeLoad(0, -1, 0)
    }

    private fun computeLoad(rockIdx: Int, cubeIdx: Int, rockCount: Int): Int {
        if (rockIdx == size) {
            return 0
        }
        return when (column[rockIdx]) {
            '.' -> computeLoad(rockIdx + 1, cubeIdx, rockCount)
            'O' ->
                (size - cubeIdx - rockCount - 1) + computeLoad(rockIdx + 1, cubeIdx, rockCount + 1)
            '#' -> computeLoad(rockIdx + 1, rockIdx, rockCount = 0)
            else -> throw Exception("This should not happen")
        }
    }

    fun tiltedRock(): List<Int> {
        return tiltedRockInternal(0, -1, 0, listOf())
    }

    private fun tiltedRockInternal(
        currentIdx: Int,
        prevCubeIdx: Int,
        rockCount: Int,
        result: List<Int>
    ): List<Int> {
        if (currentIdx == size) {
            return result
        }
        return when (column[currentIdx]) {
            '.' -> tiltedRockInternal(currentIdx + 1, prevCubeIdx, rockCount, result)
            'O' -> {
                val newIdx = prevCubeIdx + rockCount + 1
                tiltedRockInternal(currentIdx + 1, prevCubeIdx, rockCount + 1, result + newIdx)
            }
            '#' -> tiltedRockInternal(currentIdx + 1, currentIdx, rockCount = 0, result)
            else -> throw Exception("This should not happen")
        }
    }
}

// class TiltOnce(
//    private val rockSet: Set<Int>,
//    private val cubeSet: Set<Int>,
//    private val size: Int
// ) {
//    fun tiltedRock(): Set<Int> = tiltedRockInternal(0, -1, 0, setOf())
//
//    private tailrec fun tiltedRockInternal(
//        currentIdx: Int,
//        prevCubeIdx: Int,
//        rockCount: Int,
//        result: Set<Int>
//    ): Set<Int> {
//        if (currentIdx == size) {
//            return result
//        }
//        return if (rockSet.contains(currentIdx)) {
//            val newIdx = prevCubeIdx + rockCount + 1
//            tiltedRockInternal(currentIdx + 1, prevCubeIdx, rockCount + 1, result + newIdx)
//        } else if (cubeSet.contains(currentIdx)) {
//            tiltedRockInternal(currentIdx + 1, currentIdx, rockCount = 0, result)
//        } else {
//            tiltedRockInternal(currentIdx + 1, prevCubeIdx, rockCount, result)
//        }
//    }
// }
//
// data class Coord(val x: Int, val y: Int)
//
// class Platform(private val platform: List<String>) {
//    private val X = platform.first().length
//    private val Y = platform.size
//    private val cubesCoord: List<Coord> by lazy {
//        platform.flatMapIndexed { y, s ->
//            s.toList().mapIndexedNotNull { x, c -> if (c == '#') Coord(x, y) else null }
//        }
//    }
//
//    private val rocksCoord: List<Coord> by lazy {
//        platform.flatMapIndexed { y, s ->
//            s.toList().mapIndexedNotNull { x, c -> if (c == 'O') Coord(x, y) else null }
//        }
//    }
//
//    private val cubeByX =
//        cubesCoord.groupBy { (x, y) -> x }.mapValues { (k, v) -> v.map { it.y }.toSet() }
//    private val cubeByY =
//        cubesCoord.groupBy { (x, y) -> y }.mapValues { (k, v) -> v.map { it.x }.toSet() }
//
//    fun run(): Int {
//        var res = rocksCoord
//        for (i in 1..1000000000) {
//            res = loop(res)
//        }
//        return res.groupBy { (x, y) -> x }
//            .asSequence()
//            .flatMap { (x, rocks) ->
//                val cubes = cubeByX[x] ?: listOf()
//                TiltOnce(rocks.map { it.y }.toSet(), cubes.toSet(), Y).tiltedRock().map {
//                    Coord(x, it)
//                }
//            }
//            .map { (x, y) -> Y - y }
//            .sum()
//    }
//
//    private fun loop(input: List<Coord>): List<Coord> {
//        return input
//            // North
//            .groupBy { (x, y) -> x }
//            .asSequence()
//            .flatMap { (x, rocks) ->
//                val cubes = cubeByX[x] ?: listOf()
//                TiltOnce(rocks.map { it.y }.toSet(), cubes.toSet(), Y).tiltedRock().map {
//                    Coord(x, it)
//                }
//            }
//            // West
//            .groupBy { (x, y) -> y }
//            .asSequence()
//            .flatMap { (y, rocks) ->
//                val cubes = cubeByY[y] ?: listOf()
//                TiltOnce(rocks.map { it.x }.toSet(), cubes.toSet(), X).tiltedRock().map {
//                    Coord(it, y)
//                }
//            }
//            // South
//            .groupBy { (x, y) -> x }
//            .asSequence()
//            .flatMap { (x, rocks) ->
//                val cubes = cubeByX[x] ?: listOf()
//                TiltOnce(rocks.map { it.y }.toSet().reverse(Y), cubes.toSet().reverse(Y), Y)
//                    .tiltedRock()
//                    .map { Coord(x, Y - it) }
//            }
//            // East
//            .groupBy { (x, y) -> y }
//            .asSequence()
//            .flatMap { (y, rocks) ->
//                val cubes = cubeByY[y] ?: listOf()
//                TiltOnce(rocks.map { it.x }.toSet().reverse(X), cubes.toSet().reverse(X), X)
//                    .tiltedRock()
//                    .map { Coord(X - it, y) }
//            }
//            .toList()
//    }
//
//    private fun Set<Int>.reverse(size: Int): Set<Int> = map { size - it }.toSet()
// }
