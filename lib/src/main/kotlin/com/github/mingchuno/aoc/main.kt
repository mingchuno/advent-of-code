package com.github.mingchuno.aoc

import com.github.mingchuno.aoc.y2023.d25.Day25
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val ans = Day25.computePart1("2023/day25-real.txt")
        println("Ans=$ans")
    }
}
