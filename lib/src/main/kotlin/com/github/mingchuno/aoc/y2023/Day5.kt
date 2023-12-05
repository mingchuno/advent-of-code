package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.parseLongs
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day5 {
    fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val seeds = inputs.first().parseSeed()
        val mappings = parseMappings(inputs)
        return seeds
            .map { seed ->
                mappings.fold(seed) { prev: Long, nextMap: List<Mapping> ->
                    nextMap.srcToDest(prev)
                }
            }
            .min()
    }

    /** Part 2 brute force approach. Took 18 minutes on M1 PRO MAX using single thread */
    fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val seedSeq = inputs.first().parseSeedForPart2()
        val mappings = parseMappings(inputs)
        return seedSeq
            .map { seeds ->
                var min = Long.MAX_VALUE
                seeds.forEach { seed -> min = minOf(mappings.findLocation(seed), min) }
                min
            }
            .min()
    }

    private fun List<List<Mapping>>.findLocation(seed: Long): Long {
        var prev = seed
        for (mappings in this) {
            prev = mappings.srcToDest(prev)
        }
        return prev
    }

    private fun parseMappings(inputs: List<String>): List<List<Mapping>> {
        val mappings = mutableListOf<List<Mapping>>()
        var currentMappings = mutableListOf<Mapping>()
        for (line in inputs.drop(2)) { // first 2 lines can skip
            if (line.contains("map:")) { // start
                currentMappings = mutableListOf() // reset
            } else if (line.isEmpty()) { // end
                mappings.add(currentMappings) // append to master list
            } else { // parse
                // This should be the line that need to parse
                val (destinationStart, sourceStart, rangeLength) =
                    line.split(" ").map { it.toLong() }
                currentMappings.add(
                    Mapping(
                        destinationStart = destinationStart,
                        sourceStart = sourceStart,
                        rangeLength = rangeLength
                    )
                )
            }
        }
        mappings.add(currentMappings) // add the last one
        return mappings
    }

    private fun String.parseSeed(): List<Long> = parseLongs()

    private fun String.parseSeedForPart2(): List<Sequence<Long>> {
        return this.parseSeed().chunked(2).map { xs ->
            val (start, length) = xs
            LongRange(start, start + length - 1).asSequence()
        }
    }
}

data class Mapping(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long) {
    fun srcToDest(source: Long): Long? {
        return if (source >= sourceStart && source < sourceStart + rangeLength) {
            destinationStart + (source - sourceStart)
        } else null
    }
}

fun List<Mapping>.srcToDest(source: Long): Long =
    firstNotNullOfOrNull { it.srcToDest(source) } ?: source
