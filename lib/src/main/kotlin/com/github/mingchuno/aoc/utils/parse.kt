package com.github.mingchuno.aoc.utils

val numberRegexp = """(-?\d+)""".toRegex()

fun String.parseInts(): List<Int> = numberRegexp.findAll(this).map { it.value.toInt() }.toList()

fun String.parseLongs(): List<Long> = numberRegexp.findAll(this).map { it.value.toLong() }.toList()
