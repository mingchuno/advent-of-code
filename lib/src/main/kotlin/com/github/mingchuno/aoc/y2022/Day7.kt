package com.github.mingchuno.aoc.y2022

object Day7 {

    //    private fun parseInput(input: String)
    private fun buildDirTree(input: List<String>): Node {
        input.forEach { line ->
            val isCommand = line.startsWith("$")

        }
        TODO()
    }
}

interface Command

data class CD(val dir: String) : Command

data object CD_UP : Command

data object CD_ROOT : Command

data object LS : Command

interface Node

data class File(val filename: String, val size: Int) : Node

data class Dir(val dirname: String, val children: List<Node>) : Node

val ROOT = Dir("/", listOf())
