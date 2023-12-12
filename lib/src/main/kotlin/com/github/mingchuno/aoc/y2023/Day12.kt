package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.pmap
import com.github.mingchuno.aoc.utils.readFileFromResource
import kotlinx.coroutines.runBlocking

class SpringConfig {
    fun search(springs: List<Char>, damagedCount: List<Int>): Int {
        return possibleConfig(springs, damagedCount)
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
                    val fullSprings = List(5) { springs }.joinToString("?")
                    val fullDamaged = List(5) { damaged.split(",").map { it.toInt() } }.flatten()
                    SpringConfig().search(fullSprings.toList(), fullDamaged)
                }
                .sum()
        }
    }
}
