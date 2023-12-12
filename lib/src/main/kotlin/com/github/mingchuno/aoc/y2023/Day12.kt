package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

class SpringConfig(private val scale: Int) {

    private val pCache = mutableMapOf<String, Long>()

    private fun toKey(spring: String, damagedCount: List<Int>): String =
        spring + damagedCount.joinToString(",")

    fun search(springs: String, damaged: String): Long {
        val d = damaged.split(",").map { it.toInt() }
        val fullDamaged = List(scale) { d }.flatten()
        return possibleConfigMemo(List(scale) { springs }.joinToString("?").toList(), fullDamaged)
    }

    private fun possibleConfigMemo(springs: List<Char>, damagedCount: List<Int>): Long {
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
            '.' -> possibleConfigMemo(springs.drop(1), damagedCount)
            '#' -> {
                if (springs.take(firstDamage).all { it == '#' || it == '?' }) {
                    if (springs.size == firstDamage) {
                        possibleConfigMemo(listOf(), damagedCount.drop(1))
                    } else {
                        when (springs[firstDamage]) {
                            '#' -> 0
                            else ->
                                possibleConfigMemo(
                                    springs.drop(firstDamage + 1),
                                    damagedCount.drop(1)
                                )
                        }
                    }
                } else {
                    0
                }
            }
            '?' -> {
                possibleConfigMemo(springs.replaceFirst('.'), damagedCount) +
                    possibleConfigMemo(springs.replaceFirst('#'), damagedCount)
            }
            else -> throw Exception("This should not happens")
        }
    }

    private fun <T> List<T>.replaceFirst(value: T): List<T> = toMutableList().also { it[0] = value }
}

object Day12 : Problem<Long> {
    override fun computePart1(inputFile: String): Long {
        return compute(inputFile, 1)
    }

    override fun computePart2(inputFile: String): Long {
        return compute(inputFile, 5)
    }

    private fun compute(inputFile: String, scale: Int): Long {
        val inputs = inputFile.readFileFromResource()
        return inputs.sumOf { line ->
            val (springs, damaged) = line.split(" ")
            SpringConfig(scale).search(springs, damaged)
        }
    }
}
