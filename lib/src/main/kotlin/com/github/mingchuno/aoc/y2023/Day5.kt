package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.utils.parseLongs
import com.github.mingchuno.aoc.utils.pmap
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

    /** Part 2 brute force approach. Took 18 minutes on Apple M1 MAX using single thread */
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

    /**
     * Part 2 brute force with parallelism using different thread to speed up. Took 4 minutes on
     * Apple M1 MAX
     */
    suspend fun computePart2Parallel(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val seedSeq = inputs.first().parseSeedForPart2()
        val mappings = parseMappings(inputs)
        return seedSeq
            .pmap { seeds ->
                var min = Long.MAX_VALUE
                seeds.forEach { seed -> min = minOf(mappings.findLocation(seed), min) }
                min
            }
            .min()
    }

    /** Compute the Location Ranges instead and the find the min from it is the ans */
    fun computePart2RangeMapping(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        val seeds = inputs.first().parsePart2InRange()
        val mappingss = parseMappings(inputs) // Layers of Mappings
        return seeds
            .flatMap { seedRange ->
                mappingss.fold(listOf(seedRange)) { acc, nextPhase ->
                    acc.flatMap { nextPhase.expandRange(it) }
                }
            }
            .map { it.first } // Take the start value is the min of the range
            .min() // Min of all Location Ranges
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
        return this.parsePart2InRange().map { it.asSequence() }
    }

    private fun String.parsePart2InRange(): List<LongRange> {
        return this.parseSeed().chunked(2).map { xs ->
            val (start, length) = xs
            LongRange(start, start + length - 1)
        }
    }
}

data class Mapping(val destinationStart: Long, val sourceStart: Long, val rangeLength: Long) {
    val sourceEnd: Long = sourceStart + rangeLength - 1

    val destinationEnd: Long = destinationStart + rangeLength - 1

    val sourceRange = LongRange(sourceStart, sourceEnd)

    fun destinationOf(source: Long): Long = destinationStart + source - sourceStart

    fun srcToDest(source: Long): Long? {
        return if (source >= sourceStart && source < sourceStart + rangeLength) {
            destinationStart + (source - sourceStart)
        } else null
    }
}

/** Sort the input Mapping and fill the gap with 1:1 Mapping. The result Mappings will be sorted */
fun List<Mapping>.fillTheGap(): List<Mapping> {
    val mappings = mutableListOf<Mapping>()
    val sortedMappings = this.sortedBy { it.sourceStart }
    // leftmost
    val leftMost = sortedMappings[0]
    mappings.add(
        Mapping(
            destinationStart = Long.MIN_VALUE,
            sourceStart = Long.MIN_VALUE,
            rangeLength = leftMost.sourceStart - Long.MIN_VALUE
        )
    )
    for (i in 0..(sortedMappings.size - 2)) {
        val mapping = sortedMappings[i]
        val nextMapping = sortedMappings[i + 1]
        mappings.add(mapping)
        mappings.add(
            Mapping(
                destinationStart = mapping.sourceEnd + 1,
                sourceStart = mapping.sourceEnd + 1,
                rangeLength = nextMapping.sourceStart - mapping.sourceEnd - 1
            )
        )
    }
    // rightmost
    val rightMost = sortedMappings[sortedMappings.size - 1]
    mappings.add(rightMost)
    mappings.add(
        Mapping(
            destinationStart = rightMost.sourceEnd + 1,
            sourceStart = rightMost.sourceEnd + 1,
            rangeLength = Long.MAX_VALUE - rightMost.sourceEnd
        )
    )
    // Need to perform filter because 2 range may be continuous and no gap in between
    return mappings.filter { it.rangeLength != 0L }.sortedBy { it.sourceStart }
}

fun List<Mapping>.expandRange(range: LongRange): List<LongRange> {
    val sortedMappings = fillTheGap()
    val startIdx = sortedMappings.indexOfFirst { it.sourceRange.contains(range.first) }
    val endIdx = sortedMappings.indexOfFirst { it.sourceRange.contains(range.last) }
    val result = mutableListOf<LongRange>()
    if (startIdx == endIdx) {
        val mapping = sortedMappings[startIdx]
        result.add(LongRange(mapping.destinationOf(range.first), mapping.destinationOf(range.last)))
    } else {
        for (i in startIdx..endIdx) {
            val mapping = sortedMappings[i]
            when (i) {
                endIdx -> {
                    result.add(
                        LongRange(mapping.destinationStart, mapping.destinationOf(range.last))
                    )
                }
                startIdx -> {
                    result.add(
                        LongRange(mapping.destinationOf(range.first), mapping.destinationEnd)
                    )
                }
                else -> {
                    result.add(LongRange(mapping.destinationStart, mapping.destinationEnd))
                }
            }
        }
    }
    return result
}

fun List<Mapping>.srcToDest(source: Long): Long =
    firstNotNullOfOrNull { it.srcToDest(source) } ?: source
