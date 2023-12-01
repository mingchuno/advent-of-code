package com.github.mingchuno.aoc.y2022

object Day5 : Solution() {
    override val day: Int = 5

    override fun solvePart1(input: List<String>): String {
        val breakIdx = findBreakIndex(input)
        val stacks = parseStack(input, breakIdx)
        val steps = parseSteps(input, breakIdx)
        // Run the steps
        steps.forEach { step ->
            for (i in 1..step.count) {
                val popedElm = stacks[step.from - 1].removeFirst()
                stacks[step.to - 1].addFirst(popedElm)
            }
        }
        return stacks.map { it.first() }.joinToString("")
    }

    private fun parseSteps(input: List<String>, breakIdx: Int): List<Step> {
        val regexp = "move (\\d+) from (\\d+) to (\\d+)".toRegex()
        val steps = mutableListOf<Step>()
        for (i in (breakIdx + 1) ..< input.size) {
            val (count, from, to) = regexp.find(input[i])!!.destructured
            steps.add(Step(from = from.toInt(), to = to.toInt(), count = count.toInt()))
        }
        return steps
    }

    private fun parseStack(input: List<String>, breakIdx: Int): List<ArrayDeque<Char>> {
        val stacks = mutableListOf<ArrayDeque<Char>>()
        val numberRow = input[breakIdx - 1]
        numberRow.forEachIndexed { index, char ->
            if (char.isDigit()) {
                val stack = ArrayDeque<Char>()
                loop@ for (i in (breakIdx - 2) downTo 0) {
                    // scan and build stack
                    val cargo = input[i].getOrNull(index)
                    if (cargo != null && cargo.isLetter()) {
                        stack.addFirst(cargo) // push
                    } else {
                        break@loop
                    }
                }
                stacks.add(stack)
            }
        }
        return stacks
    }

    private fun findBreakIndex(input: List<String>): Int = input.indexOfFirst { it.isEmpty() }

    override fun solvePart2(input: List<String>): String {
        val breakIdx = findBreakIndex(input)
        val stacks = parseStack(input, breakIdx)
        val steps = parseSteps(input, breakIdx)
        // Run the steps
        steps.forEach { step ->
            val tempStack = ArrayDeque<Char>()
            for (i in 1..step.count) {
                val popedElm = stacks[step.from - 1].removeFirst()
                tempStack.addFirst(popedElm)
            }
            for (i in 1..step.count) {
                val popedElm = tempStack.removeFirst()
                stacks[step.to - 1].addFirst(popedElm)
            }
        }
        return stacks.map { it.first() }.joinToString("")
    }
}

data class Step(val from: Int, val to: Int, val count: Int)
