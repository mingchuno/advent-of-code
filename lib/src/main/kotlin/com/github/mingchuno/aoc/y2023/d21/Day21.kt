package com.github.mingchuno.aoc.y2023.d21

import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.to2DChars
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.api.linalg.solve
import org.jetbrains.kotlinx.multik.api.mk
import org.jetbrains.kotlinx.multik.api.ndarray
import org.jetbrains.kotlinx.multik.ndarray.data.D1
import org.jetbrains.kotlinx.multik.ndarray.data.D2
import org.jetbrains.kotlinx.multik.ndarray.data.NDArray
import org.jetbrains.kotlinx.multik.ndarray.operations.map

object Day21 {
    fun computePart1(inputFile: String, maxSteps: Int): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        return WalkGarden(inputs, maxSteps).walk()
    }

    fun computePart2(inputFile: String, maxSteps: Int): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        return WalkInfiniteGarden(inputs, maxSteps).walk()
    }

    private fun stepsFromN(n: Int): Int = n * 131 + 65

    fun computePart2Quadratic(inputFile: String, n: Long): Long {
        val tuples =
            (0..2).map { i ->
                i to
                    computePart2(inputFile, stepsFromN(i)).also { y ->
                        println("Finish step n=$i and y=$y")
                    }
            }
        // Solve AX = Y
        val A: NDArray<Int, D2> = mk.ndarray(tuples.map { (i, _) -> vec(i) })
        val Y: NDArray<Int, D1> = mk.ndarray(tuples.map { (_, y) -> y })
        println("A=$A")
        println("Y=$Y")
        val X: NDArray<Long, D1> = mk.linalg.solve(A, Y).map { it.toLong() }
        val N: NDArray<Long, D1> = mk.ndarray(vec(n))
        println("coefficient:$X")
        println("N=$N")
        // X.N will give you the ans
        return X.dot(N)
    }

    private fun vec(i: Int): List<Int> = mk[i * i, i, 1]

    private fun vec(i: Long): List<Long> = mk[i * i, i, 1]
}
