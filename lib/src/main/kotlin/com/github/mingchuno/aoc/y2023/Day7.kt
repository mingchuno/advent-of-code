// Generated using IDE template `aoc`
package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.ThisShouldNotHappenException
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
        return hands.sortedWith { a, b -> a.comparePart2(b) }.winnings()
    }
}

private fun List<Hand>.winnings(): Int = mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()

private const val FIVE_OF_A_KIND = 6
private const val FOURTH_OF_A_KIND = 5
private const val FULL_HOUSE = 4
private const val THREE_OF_A_KIND = 3
private const val TWO_PAIRS = 2
private const val ONE_PAIR = 1
private const val HIGH_CARD = 0

data class Hand(val hand: String, val bid: Int) {
    private val _map: Map<Char, Int> = hand.groupBy { it }.mapValues { (_, v) -> v.size }
    private val _mapValues = _map.values
    private val _mapWithoutJ = _map.filterNot { (k, _) -> k == 'J' }
    private val _mapWithoutJValues = _mapWithoutJ.values

    val rankNumber: Int =
        if (_mapValues.contains(5)) FIVE_OF_A_KIND
        else if (_mapValues.contains(4)) FOURTH_OF_A_KIND
        else if (_mapValues.contains(2) && _mapValues.contains(3)) FULL_HOUSE
        else if (_mapValues.filter { it == 3 }.size == 1) THREE_OF_A_KIND
        else if (_mapValues.filter { it == 2 }.size == 2) TWO_PAIRS
        else if (_mapValues.filter { it == 2 }.size == 1) ONE_PAIR
        else if (_mapValues.all { it == 1 }) HIGH_CARD
        else throw ThisShouldNotHappenException("unknown hand:$hand")

    val part2RankNumber: Int by lazy {
        val jokerCount = hand.count { it == 'J' }
        when (jokerCount) {
            0 -> rankNumber
            5 -> FIVE_OF_A_KIND
            4 -> FIVE_OF_A_KIND
            3 ->
                if (_mapWithoutJValues.any { it == 2 }) FIVE_OF_A_KIND /*3,2*/
                else FOURTH_OF_A_KIND /*3,1,1*/
            2 ->
                if (_mapWithoutJValues.any { it == 3 }) FIVE_OF_A_KIND /*2,3*/
                else if (_mapWithoutJValues.any { it == 2 }) FOURTH_OF_A_KIND /*2,2,1*/
                else if (_mapWithoutJValues.all { it == 1 }) THREE_OF_A_KIND /*2,1,1,1*/
                else throw ThisShouldNotHappenException("unknown hand:$hand")
            1 ->
                if (_mapWithoutJValues.any { it == 4 }) FIVE_OF_A_KIND /*1,4*/
                else if (_mapWithoutJValues.any { it == 3 }) FOURTH_OF_A_KIND /*1,3,1*/
                else if (_mapWithoutJValues.all { it == 2 }) FULL_HOUSE /*1,2,2*/
                else if (_mapWithoutJValues.any { it == 2 }) THREE_OF_A_KIND /*1,2,1,1*/
                else if (_mapWithoutJValues.all { it == 1 }) ONE_PAIR /*1,1,1,1,1*/
                else throw ThisShouldNotHappenException("unknown hand:$hand")
            else -> throw ThisShouldNotHappenException("unknown hand:$hand")
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
        else -> throw ThisShouldNotHappenException("unknown card:$this")
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
        else -> throw ThisShouldNotHappenException("unknown card:$this")
    }
}

private fun String.parseHands(): Hand {
    val (hand, bid) = this.split(" ")
    return Hand(hand = hand, bid = bid.toInt())
}
