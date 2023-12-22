package com.github.mingchuno.aoc.y2023.d22

import com.github.mingchuno.aoc.utils.ThisShouldNotHappenException
import java.util.*

data class Position(val x: Int, val y: Int, val z: Int) {
    override fun toString(): String {
        return "$x,$y,$z"
    }
}

data class Brick(val start: Position, val end: Position) {

    fun overlap(that: Brick): Boolean {
        val thisX = start.x..end.x
        val thisY = start.y..end.y
        val thatX = that.start.x..that.end.x
        val thatY = that.start.y..that.end.y
        return thisX.intersect(thatX).isNotEmpty() && thisY.intersect(thatY).isNotEmpty()
    }

    fun lowerToLevel(z: Int): Brick = Brick(start.copy(z = z), end.copy(z = end.z - start.z + z))

    override fun toString(): String {
        val horizontal = start.z == end.z
        val char = if (horizontal) "-" else "|"
        return "$start$char$end"
    }
}

class BrickResult {
    val q = ArrayDeque<Brick>()
    val mapStartKey = TreeMap<Int, MutableList<Brick>>()
    val mapEndKey = TreeMap<Int, MutableList<Brick>>()

    fun add(brick: Brick) {
        q.addFirst(brick)
        if (mapStartKey.contains(brick.start.z)) {
            mapStartKey[brick.start.z]!!.add(brick)
        } else {
            mapStartKey[brick.start.z] = mutableListOf(brick)
        }
        if (mapEndKey.contains(brick.end.z)) {
            mapEndKey[brick.end.z]!!.add(brick)
        } else {
            mapEndKey[brick.end.z] = mutableListOf(brick)
        }
    }
}

class Jenga(private val bricks: List<Brick>) {
    private val q = PriorityQueue<Brick> { o1, o2 -> o1.start.z - o2.start.z }
    private val brickResult = BrickResult()
    private var moved = 0

    init {
        bricks.forEach { q.add(it) }
    }

    fun compute(): Int {
        run()
        // number of bricks that CAN be disintegrated
        return bricks.size - findSupporting().size
    }

    fun computePart2(): Int {
        run()
        val supporting = findSupporting()
        return supporting.sumOf { brickToRemove ->
            Jenga(brickResult.q.filter { it != brickToRemove }).computeMoved()
        }
    }

    private fun run() {
        while (q.isNotEmpty()) {
            val brick = q.remove()
            val endBrick = findBrickEndPos(brick)
            if (brick != endBrick) moved++
            brickResult.add(endBrick)
        }
    }

    fun computeMoved(): Int {
        run()
        return moved
    }

    @Suppress("unused")
    private fun checkValid() {
        val temp = mutableSetOf<Position>()
        brickResult.q.forEach { brick ->
            for (x in brick.start.x..brick.end.x) {
                for (y in brick.start.y..brick.end.y) {
                    for (z in brick.start.z..brick.end.z) {
                        val pos = Position(x, y, z)
                        if (temp.contains(pos)) {
                            throw ThisShouldNotHappenException(pos.toString())
                        } else {
                            temp.add(pos)
                        }
                    }
                }
            }
        }
    }

    private fun findBrickEndPos(brick: Brick): Brick {
        for ((floor, bricks) in brickResult.mapEndKey.toList().reversed()) {
            val isOverlap = bricks.any { res -> res.overlap(brick.lowerToLevel(res.end.z)) }
            if (isOverlap) {
                return brick.lowerToLevel(floor + 1)
            }
        }
        return brick.lowerToLevel(1)
    }

    private fun findSupporting(): Set<Brick> {
        val bmap = brickResult.mapStartKey
        val amap = brickResult.mapEndKey
        val cannotBricks =
            bmap
                .flatMap { (bKey, bBricks) ->
                    val aBricks = amap[bKey - 1] ?: listOf()
                    bBricks
                        .map { bb -> aBricks.filter { ab -> ab.overlap(bb) } }
                        .filter { it.size == 1 }
                        .flatten()
                }
                .toSet()
        return cannotBricks
    }
}
