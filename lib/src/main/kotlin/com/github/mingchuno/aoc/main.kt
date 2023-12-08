package com.github.mingchuno.aoc

import com.github.mingchuno.aoc.y2023.Day8
import kotlinx.coroutines.runBlocking

fun main() {
    runBlocking {
        val ans = Day8.computePart2Method2("2023/day8-real.txt")
        println("Ans=$ans")
    }
}
// 23147 17873 20803 15529 19631 17287
