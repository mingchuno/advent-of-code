// Generated using IDE template `aoc`
package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day7 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val hands = parseInputs(inputs)
        return hands.sortedWith { a, b -> a.comparePart1(b) }.winnings()
    }

    private fun parseInputs(input: List<String>): List<Hand> = input.map { it.parseHands() }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource()
        val hands = parseInputs(inputs)
        return hands.sortedWith { a, b -> a.comparePart2(b) }.onEach { println(it) }.winnings()
    }
}

private fun List<Hand>.winnings(): Int = mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()

data class Hand(val hand: String, val bid: Int) {
    private val _map: Map<Char, Int> = hand.groupBy { it }.mapValues { (_, v) -> v.size }
    private val _mapWithoutJ = _map.filterNot { (k, _) -> k == 'J' }
    private val isFiveOfAKind = _map.values.contains(5)
    private val isFourthOfAKind = _map.values.contains(4)
    private val isFullHouse = _map.size == 2 && _map.values.contains(2) && _map.values.contains(3)
    private val isThreeOfAKind =
        _map.values.filter { it == 3 }.size == 1 && !_map.values.contains(2)
    private val isTwoPairs = _map.values.filter { it == 2 }.size == 2
    private val isOnePair =
        _map.values.filter { it == 2 }.size == 1 /* have a pair */ &&
            !_map.values.any { it >= 3 } /* and don't have full house or above */
    private val highCard = _map.values.all { it == 1 }

    val rankNumber =
        if (isFiveOfAKind) {
            6
        } else if (isFourthOfAKind) {
            5
        } else if (isFullHouse) {
            4
        } else if (isThreeOfAKind) {
            3
        } else if (isTwoPairs) {
            2
        } else if (isOnePair) {
            1
        } else if (highCard) {
            0
        } else {
            throw Exception("unknown hand:$hand")
        }

    val part2RankNumber: Int by lazy {
        val jokerCount = hand.count { it == 'J' }
        when (jokerCount) {
            0 -> rankNumber
            5 -> 6
            4 -> 6
            3 -> if (_mapWithoutJ.values.any { it == 2 }) 6 else 5
            2 ->
                if (_mapWithoutJ.values.any { it == 3 }) {
                    6
                } else if (_mapWithoutJ.values.any { it == 2 }) {
                    5
                } else if (_mapWithoutJ.values.all { it == 1 }) 3 else 4
            1 ->
                if (_mapWithoutJ.values.any { it == 4 }) {
                    6
                } else if (_mapWithoutJ.values.any { it == 3 }) {
                    5
                } else if (_mapWithoutJ.values.all { it == 2 }) {
                    4
                } else if (_mapWithoutJ.values.any { it == 2 }) {
                    3
                } else if (_mapWithoutJ.values.all { it == 1 }) {
                    1
                } else {
                    throw Exception("unknown hand:$hand")
                }
            else -> throw Exception("unknown hand:$hand")
        }
    }

    fun comparePart1(other: Hand): Int {
        val rankDiff = this.rankNumber - other.rankNumber
        return if (rankDiff != 0) rankDiff
        else {
            val (l, r) = this.hand.zip(other.hand).first { (a, b) -> a != b }
            l.score() - r.score()
        }
    }

    fun comparePart2(other: Hand): Int {
        val rankDiff = this.part2RankNumber - other.part2RankNumber
        return if (rankDiff != 0) rankDiff
        else {
            val (l, r) = this.hand.zip(other.hand).first { (a, b) -> a != b }
            l.jokerScore() - r.jokerScore()
        }
    }
}

private fun Char.score(): Int {
    return when (this) {
        'A' -> 13
        'K' -> 12
        'Q' -> 11
        'J' -> 10
        'T' -> 9
        '9' -> 8
        '8' -> 7
        '7' -> 6
        '6' -> 5
        '5' -> 4
        '4' -> 3
        '3' -> 2
        '2' -> 1
        else -> throw Exception("unknown card:$this")
    }
}

private fun Char.jokerScore(): Int {
    return when (this) {
        'A' -> 13
        'K' -> 12
        'Q' -> 11
        'T' -> 10
        '9' -> 9
        '8' -> 8
        '7' -> 7
        '6' -> 6
        '5' -> 5
        '4' -> 4
        '3' -> 3
        '2' -> 2
        'J' -> 1
        else -> throw Exception("unknown card:$this")
    }
}

private fun String.parseHands(): Hand {
    val (hand, bid) = this.split(" ")
    return Hand(hand = hand, bid = bid.toInt())
}
