package com.github.mingchuno.aoc.interfaceing

interface Problem<T> {
    fun computePart1(inputFile: String): T

    fun computePart2(inputFile: String): T
}
