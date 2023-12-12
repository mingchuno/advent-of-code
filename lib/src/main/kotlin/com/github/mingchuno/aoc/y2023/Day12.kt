package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlinx.coroutines.runBlocking

class SpringConfig {

    private val pCache = mutableMapOf<String, Long>()
    private val sCache = mutableMapOf<String, Long>()

    fun search(springs: List<Char>, damagedCount: List<Int>): Long {
        return possibleConfig(springs, damagedCount).also { println(it) }
    }

    private fun toKey(spring: String, damagedCount: List<Int>): String =
        spring + damagedCount.joinToString(",")

    private fun toKey(springs: List<String>, damagedCount: List<Int>): String =
        springs.joinToString("-") + damagedCount.joinToString(",")

    fun part2(springs: String, damaged: String): Long {
        val scale = 5
        val d = damaged.split(",").map { it.toInt() }
        val fullDamaged = List(scale) { d }.flatten()
        return possibleConfigM(List(scale) { springs }.joinToString("?").toList(), fullDamaged)
    }

    fun search2(springs: List<String>, damagedCount: List<Int>): Long {
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
        val firstGroupMin = firstSpringGroup.count { it == '#' }
        val runningSum = damagedCount.runningReduce { acc, i -> acc + i }
        val endIdx = runningSum.indexOfFirst { firstSpringGroupSize <= it }
        val startIdx = runningSum.indexOfFirst { firstGroupMin > it }
        val trueEnd = if (endIdx < 0) damagedCount.size - 1 else endIdx
        val trueStart = if (startIdx >= 0) startIdx else 0
        var sum = 0L
        for (i in trueStart..trueEnd + 1) {
            val headDamage = damagedCount.take(i)
            val tailDamage = damagedCount.drop(i)
            sum +=
                possibleConfig(firstSpringGroup.toList(), headDamage) *
                    search2(tailSpringGroup, tailDamage)
        }
        sCache[sKey] = sum
        return sum
    }

    private fun possibleConfigM(springs: List<Char>, damagedCount: List<Int>): Long {
        val key = toKey(springs.joinToString(""), damagedCount)
        return pCache[key] ?: possibleConfig(springs, damagedCount).also { pCache[key] = it }
    }

    private fun possibleConfig(springs: List<Char>, damagedCount: List<Int>): Long {
        if (springs.isEmpty() && damagedCount.isEmpty()) {
            return 1
        }
        if (springs.isEmpty()) {
            return 0
        }

        if (damagedCount.isEmpty()) {
            return if (springs.all { it == '.' || it == '?' }) 1 else 0
        }
        val firstDamage = damagedCount.first()
        if (springs.size < firstDamage) {
            return 0
        }
        if (springs.size < damagedCount.sum()) {
            return 0
        }

        return when (springs.first()) {
            '.' -> possibleConfigM(springs.drop(1), damagedCount)
            '#' -> {
                if (springs.take(firstDamage).all { it == '#' || it == '?' }) {
                    if (springs.size == firstDamage) {
                        possibleConfigM(listOf(), damagedCount.drop(1))
                    } else {
                        when (springs[firstDamage]) {
                            '#' -> 0
                            else ->
                                possibleConfigM(springs.drop(firstDamage + 1), damagedCount.drop(1))
                        }
                    }
                } else {
                    0
                }
            }
            '?' -> {
                possibleConfigM(springs.replaceFirst('.'), damagedCount) +
                    possibleConfigM(springs.replaceFirst('#'), damagedCount)
            }
            else -> throw Exception("This should not happens")
        }
    }

    private fun <T> List<T>.replaceFirst(value: T): List<T> = toMutableList().also { it[0] = value }
}

object Day12 : Problem<Long> {
    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        return inputs
            .map { line ->
                val (springs, damaged) = line.split(" ")
                SpringConfig().search(springs.toList(), damaged.split(",").map { it.toInt() })
            }
            .sum()
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        return runBlocking {
            inputs
                .map { line ->
                    val (springs, damaged) = line.split(" ")
                    SpringConfig().part2(springs, damaged)
                }
                .sum()
        }
    }
}
