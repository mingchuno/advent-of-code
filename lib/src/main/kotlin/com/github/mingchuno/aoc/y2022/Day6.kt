package com.github.mingchuno.aoc.y2022

object Day6 {

    private val day = 6

    fun solvePart1Example(): Int = solvePart1(InputReader.readExample(day).first().toList())

    fun solvePart1Real(): Int = solvePart1(InputReader.readReal(day).first().toList())

    fun solvePart2Example(): Int = solvePart2(InputReader.readExample(day).first().toList())

    fun solvePart2Real(): Int = solvePart2(InputReader.readReal(day).first().toList())

    fun solvePart1(chars: List<Char>): Int {
        for (i in 3 ..< chars.size) {
            if (setOf(chars[i], chars[i - 1], chars[i - 2], chars[i - 3]).size == 4) {
                return i + 1
            }
        }
        return 0
    }

    fun solvePart2(chars: List<Char>): Int {
        val runningBuffer = ArrayDeque<Char>()
        chars.forEachIndexed { index, c ->
            if (index <= 13) {
                runningBuffer.addFirst(c)
            } else {
                if (runningBuffer.size != 14) {
                    throw RuntimeException("Bug!")
                }
                if (runningBuffer.toSet().size == 14) {
                    return@solvePart2 index
                }
                runningBuffer.addFirst(c)
                runningBuffer.removeLast()
            }
        }
        return 0
    }
}
