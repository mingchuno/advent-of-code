package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.*

object Day11 : Problem<Long> {

    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource().first().parseLongs()
        val b = Blink(25)
        return inputs.sumOf { stone -> b.blinkWithCache(0, stone) }
    }

    private class Blink(private val maxLayer: Int = 75) {
        private val cache = mutableMapOf<Pair<Int, Long>, Long>()

        private fun recursiveBlink(layer: Int, stone: Long): Long {
            return if (layer == maxLayer) {
                1L
            } else {
                val stoneStr = stone.toString()
                val stoneLength = stoneStr.length
                if (stone == 0L) {
                    blinkWithCache(layer + 1, 1L)
                } else if (stoneLength % 2 == 0) {
                    val m = stoneLength / 2
                    val l = stoneStr.subSequence(0, m).toString().toLong()
                    val r = stoneStr.subSequence(m, stoneLength).toString().toLong()
                    blinkWithCache(layer + 1, l) + blinkWithCache(layer + 1, r)
                } else {
                    blinkWithCache(layer + 1, stone * 2024)
                }
            }
        }

        fun blinkWithCache(layer: Int, stone: Long): Long {
            val key = Pair(layer, stone)
            return cache[key] ?: recursiveBlink(layer, stone).also { cache[key] = it }
        }
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource().first().parseLongs()
        val b = Blink(75)
        return inputs.sumOf { stone -> b.blinkWithCache(0, stone) }
    }
}
