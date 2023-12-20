package com.github.mingchuno.aoc.utils

fun Iterable<Int>.product(): Int = reduce { acc, i -> acc * i }

fun Iterable<Long>.product(): Long = reduce { acc, i -> acc * i }
