package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.pmap
import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlinx.coroutines.runBlocking

class SpringConfig {

    private val pCache = mutableMapOf<String, Int>()
    private val sCache = mutableMapOf<String, Int>()

    fun search(springs: List<Char>, damagedCount: List<Int>): Int {
        return possibleConfig(springs, damagedCount)
    }

    private fun toKey(spring: String, damagedCount: List<Int>): String =
        spring + damagedCount.joinToString(",")

    private fun toKey(springs: List<String>, damagedCount: List<Int>): String =
        springs.joinToString("") + damagedCount.joinToString(",")

    fun part2(springs: String, damaged: String): Int {
        val fullSpringList =
            List(5) { springs }.joinToString("?").split(".").filter { it.isNotEmpty() }
        val fullDamaged = List(5) { damaged.split(",").map { it.toInt() } }.flatten()
        return search2(fullSpringList, fullDamaged)
    }

    fun search2(springs: List<String>, damagedCount: List<Int>): Int {
        if (springs.isEmpty() && damagedCount.isEmpty()) {
            return 1
        }
        if (springs.isEmpty() || damagedCount.isEmpty()) {
            return 0
        }
        val sKey = toKey(springs, damagedCount)
        if (sCache.containsKey(sKey)) {
            return sCache[sKey]!!
        }
        val firstSpringGroup = springs.first()
        val tailSpringGroup = springs.drop(1)
        val firstSpringGroupSize = firstSpringGroup.length
        val idx = damagedCount.recursiveSum().indexOfFirst { firstSpringGroupSize <= it }
        val realIdx = if (idx < 0) damagedCount.size - 1 else idx
        var sum = 0
        for (i in 0..realIdx + 1) {
            val headDamage = damagedCount.take(i)
            val tailDamage = damagedCount.drop(i)
            val key = toKey(firstSpringGroup, headDamage)
            val p =
                if (pCache.containsKey(key)) {
                    pCache[key]!!
                } else {
                    possibleConfig(firstSpringGroup.toList(), headDamage).also { pCache[key] = it }
                }
            sum += p * search2(tailSpringGroup, tailDamage)
        }
        sCache[sKey] = sum
        return sum
    }

    private fun List<Int>.recursiveSum(): List<Int> {
        val res = mutableListOf(this.first())
        for (i in 1 ..< this.size) {
            res.add(this[i] + res[i - 1])
        }
        return res
    }

    private fun possibleConfig(springs: List<Char>, damagedCount: List<Int>): Int {
        if (springs.isEmpty() && damagedCount.isEmpty()) {
            return 1
        }
        if (springs.isEmpty()) {
            return 0
        }
        if (damagedCount.isEmpty()) {
            return if (springs.all { it == '.' || it == '?' }) 1 else 0
        }

        return when (springs.first()) {
            '.' -> possibleConfig(springs.drop(1), damagedCount)
            '#' -> {
                val firstDamage = damagedCount.first()
                if (springs.size < firstDamage) {
                    0
                } else {
                    if (springs.take(firstDamage).all { it == '#' || it == '?' }) {
                        if (springs.size == firstDamage) {
                            possibleConfig(listOf(), damagedCount.drop(1))
                        } else {
                            when (springs[firstDamage]) {
                                '#' -> 0
                                else ->
                                    possibleConfig(
                                        springs.drop(firstDamage + 1),
                                        damagedCount.drop(1)
                                    )
                            }
                        }
                    } else {
                        0
                    }
                }
            }
            '?' -> {
                possibleConfig(springs.replaceFirst('.'), damagedCount) +
                    possibleConfig(springs.replaceFirst('#'), damagedCount)
            }
            else -> throw Exception("This should not happens")
        }
    }

    private fun <T> List<T>.replaceFirst(value: T): List<T> = toMutableList().also { it[0] = value }
}

object Day12 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return inputs
            .map { line ->
                val (springs, damaged) = line.split(" ")
                SpringConfig().search(springs.toList(), damaged.split(",").map { it.toInt() })
            }
            .sum()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        return runBlocking {
            inputs
                .pmap { line ->
                    val (springs, damaged) = line.split(" ")
                    SpringConfig().part2(springs, damaged)
                }
                .sum()
        }
    }
}
