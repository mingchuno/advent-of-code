package com.github.mingchuno.aoc.y2023

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day15 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().first().split(",")
        return inputs.sumOf { it.hash() }
    }

    override fun computePart2(inputFile: String): Int {
        val instructions = inputFile.readFileFromResource().first().split(",")
        return Facility().passInstructionsAndComputePower(instructions)
    }
}

private fun String.hash(): Int {
    return fold(0) { acc, c -> ((acc + c.code) * 17).mod(256) }
}

private class Facility {
    private val facility = List(256) { Box(it) }

    fun passInstructionsAndComputePower(instructions: List<String>): Int {
        for (instruction in instructions) {
            if (instruction.contains("-")) {
                val (label) = instruction.split("-")
                facility[label.hash()].removeLens(label)
            } else {
                val (label, focalS) = instruction.split("=")
                val focal = focalS.toInt()
                facility[label.hash()].addOrReplaceLens(label, focal)
            }
        }
        return facility.sumOf { it.fPower() }
    }
}

private data class Slot(val label: String, val focalLength: Int)

private class Box(private val boxIdx: Int) {
    private val slots = mutableListOf<Slot>()

    fun removeLens(label: String) {
        slots.removeIf { slot -> slot.label == label }
    }

    fun addOrReplaceLens(label: String, focal: Int) {
        val idx = slots.indexOfFirst { slot -> slot.label == label }
        if (idx >= 0) {
            val oldSlot = slots[idx]
            slots[idx] = oldSlot.copy(focalLength = focal)
        } else {
            slots.add(Slot(label, focal))
        }
    }

    fun fPower(): Int {
        return slots
            .mapIndexed { index, slot -> (1 + boxIdx) * (1 + index) * slot.focalLength }
            .sum()
    }
}
