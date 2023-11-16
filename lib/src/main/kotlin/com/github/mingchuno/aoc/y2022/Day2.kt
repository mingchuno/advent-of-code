package com.github.mingchuno.aoc.y2022

import com.github.mingchuno.aoc.utils.readFileFromResource

object Day2 {

    fun compute(inputFile: String): Int {
        return inputFile.readFileFromResource().sumOf {
            val (left, right) = it.split(" ").take(2)
            RPS.from(left, right).totalScore
        }
    }

    fun computePart2(inputFile: String): Int {
        return inputFile.readFileFromResource().sumOf {
            val (left, right) = it.split(" ").take(2)
            RPS.fromPart2(left, right).totalScore
        }
    }
}

data class RPS private constructor(val left: String, val right: String) {
    companion object {
        fun from(left: String, right: String): RPS {
            val r =
                when (right) {
                    "X" -> "R"
                    "Y" -> "P"
                    "Z" -> "S"
                    else -> throw RuntimeException("Error")
                }
            val l =
                when (left) {
                    "A" -> "R"
                    "B" -> "P"
                    "C" -> "S"
                    else -> throw RuntimeException("Error")
                }
            return RPS(l, r)
        }

        fun fromPart2(left: String, right: String): RPS {
            val l =
                when (left) {
                    "A" -> "R"
                    "B" -> "P"
                    "C" -> "S"
                    else -> throw RuntimeException("Error")
                }
            val r =
                when (right) {
                    "X" ->
                        when (l) {
                            "R" -> "S"
                            "P" -> "R"
                            "S" -> "P"
                            else -> throw RuntimeException("Error")
                        }
                    "Y" -> l
                    "Z" ->
                        when (l) {
                            "R" -> "P"
                            "P" -> "S"
                            "S" -> "R"
                            else -> throw RuntimeException("Error")
                        }
                    else -> throw RuntimeException("Error")
                }
            return RPS(l, r)
        }
    }

    fun baseScore(): Int {
        return when (right) {
            "R" -> 1
            "P" -> 2
            "S" -> 3
            else -> throw RuntimeException("Error")
        }
    }

    fun gameScore(): Int {
        if (left == right) {
            return 3
        }
        val lost =
            (left == "R" && right == "S") ||
                (left == "P" && right == "R") ||
                (left == "S" && right == "P")
        return if (lost) 0 else 6
    }

    val totalScore: Int by lazy { baseScore() + gameScore() }
}
