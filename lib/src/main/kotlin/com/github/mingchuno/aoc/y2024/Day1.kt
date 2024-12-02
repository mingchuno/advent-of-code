package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.parseInts
import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlin.math.abs

object Day1 : Problem<Int> {

    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        inputs.forEach {
            val (l, r) = it.parseInts()
            left.add(l)
            right.add(r)
        }
        return left.sorted().zip(right.sorted()).sumOf { (l, r) ->
            abs(l - r)
        }
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val left = mutableListOf<Int>()
        val right = mutableMapOf<Int, Int>()
        inputs.forEach {
            val (l, r) = it.parseInts()
            left.add(l)
            right[r] = (right[r] ?: 0) + 1
        }
        return left.sumOf { right.getOrDefault(it, 0) * it }
    }

}