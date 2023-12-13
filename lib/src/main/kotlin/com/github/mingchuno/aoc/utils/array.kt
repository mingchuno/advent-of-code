package com.github.mingchuno.aoc.utils

fun <T> List<List<T>>.transpose(): List<List<T>> {
    val y = this.size
    val x = this.first().size
    val result = MutableList(x) { MutableList(y) { this.first().first() } }
    for (i in 0 ..< y) {
        for (j in 0 ..< x) {
            result[j][i] = this[i][j]
        }
    }
    return result
}
