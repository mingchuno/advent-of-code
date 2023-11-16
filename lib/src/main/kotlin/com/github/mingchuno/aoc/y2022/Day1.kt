package com.github.mingchuno.aoc.y2022

import com.github.mingchuno.aoc.utils.readFileFromResource

object Day1 {
    fun compute(inputFile: String): Int {
        val list = inputFile.readFileFromResource()
        var runningCalories = 0
        var maxCalories = 0
        for (item in list) {
            if (item.isEmpty()) {
                if (runningCalories > maxCalories) {
                    maxCalories = runningCalories
                }
                runningCalories = 0
            } else {
                runningCalories += item.toInt()
            }
        }
        return maxCalories
    }

    fun computeTop3(inputFile: String): Int {
        val list = inputFile.readFileFromResource()
        val caloriesForEachElves = mutableListOf<Int>()
        var runningCalories = 0
        for (item in list) {
            if (item.isEmpty()) {
                caloriesForEachElves.add(runningCalories)
                runningCalories = 0
            } else {
                runningCalories += item.toInt()
            }
        }
        caloriesForEachElves.add(runningCalories)
        return caloriesForEachElves.sortedDescending().take(3).sum()
    }
}
