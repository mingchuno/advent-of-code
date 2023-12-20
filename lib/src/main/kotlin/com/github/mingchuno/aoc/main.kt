package com.github.mingchuno.aoc

import com.github.mingchuno.aoc.y2023.d20.Day20
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val ans = Day20.computePart1("2023/day20-example1.txt")
        println("Ans=$ans")
    }
}
