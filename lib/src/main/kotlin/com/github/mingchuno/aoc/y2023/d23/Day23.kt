package com.github.mingchuno.aoc.y2023.d23

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.to2DChars

object Day23 : Problem<Int> {
    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        return WalkForestDFSPart1(inputs).compute()
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        return WalkForestDFSPart2(inputs).compute()
    }
}
