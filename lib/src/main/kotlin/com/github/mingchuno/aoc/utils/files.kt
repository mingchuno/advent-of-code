package com.github.mingchuno.aoc.utils

import com.google.common.base.Charsets.UTF_8
import com.google.common.io.Resources

fun String.readFileFromResource(): List<String> {
    return Resources.readLines(Resources.getResource(this), UTF_8).toList()
}
