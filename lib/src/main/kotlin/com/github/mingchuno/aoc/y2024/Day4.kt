package com.github.mingchuno.aoc.y2024

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource
import com.github.mingchuno.aoc.utils.to2DChars
import com.github.mingchuno.aoc.utils.transpose

object Day4 : Problem<Int> {

    private fun List<Char>.isXmas(): Boolean = this.joinToString("") == "XMAS"

    override fun computePart1(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        val inputsT = inputs.transpose()
        val Y = inputs.size
        val X = inputs.first().size
        var count = 0
        inputs.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == 'X') {
                    // right direction
                    if (row.subList(x, (x + 4).coerceAtMost(X)).isXmas()) {
                        count++
                    }
                    // left direction
                    if (row.slice((x - 3).coerceAtLeast(0)..x).reversed().isXmas()) {
                        count++
                    }
                    // down direction
                    if (inputsT[x].subList(y, (y + 4).coerceAtMost(Y)).isXmas()) {
                        count++
                    }
                    // up direction
                    if (inputsT[x].slice((y - 3).coerceAtLeast(0)..y).reversed().isXmas()) {
                        count++
                    }
                    // NE direction
                    if (
                        listOfNotNull(
                                'X',
                                inputs.getOrNull(y - 1)?.getOrNull(x + 1),
                                inputs.getOrNull(y - 2)?.getOrNull(x + 2),
                                inputs.getOrNull(y - 3)?.getOrNull(x + 3),
                            )
                            .isXmas()
                    ) {
                        count++
                    }
                    // SE direction
                    if (
                        listOfNotNull(
                                'X',
                                inputs.getOrNull(y + 1)?.getOrNull(x + 1),
                                inputs.getOrNull(y + 2)?.getOrNull(x + 2),
                                inputs.getOrNull(y + 3)?.getOrNull(x + 3),
                            )
                            .isXmas()
                    ) {
                        count++
                    }
                    // SW direction
                    if (
                        listOfNotNull(
                                'X',
                                inputs.getOrNull(y + 1)?.getOrNull(x - 1),
                                inputs.getOrNull(y + 2)?.getOrNull(x - 2),
                                inputs.getOrNull(y + 3)?.getOrNull(x - 3),
                            )
                            .isXmas()
                    ) {
                        count++
                    }
                    // NW direction
                    if (
                        listOfNotNull(
                                'X',
                                inputs.getOrNull(y - 1)?.getOrNull(x - 1),
                                inputs.getOrNull(y - 2)?.getOrNull(x - 2),
                                inputs.getOrNull(y - 3)?.getOrNull(x - 3),
                            )
                            .isXmas()
                    ) {
                        count++
                    }
                }
            }
        }
        return count
    }

    override fun computePart2(inputFile: String): Int {
        val inputs = inputFile.readFileFromResource().to2DChars()
        var count = 0
        inputs.forEachIndexed { y, row ->
            row.forEachIndexed { x, c ->
                if (c == 'A') {
                    //
                    val pattern1 =
                        inputs.getOrNull(y - 1)?.getOrNull(x - 1) == 'M' &&
                            inputs.getOrNull(y + 1)?.getOrNull(x + 1) == 'S'
                    val pattern2 =
                        inputs.getOrNull(y - 1)?.getOrNull(x - 1) == 'S' &&
                            inputs.getOrNull(y + 1)?.getOrNull(x + 1) == 'M'

                    val pattern3 =
                        inputs.getOrNull(y - 1)?.getOrNull(x + 1) == 'M' &&
                            inputs.getOrNull(y + 1)?.getOrNull(x - 1) == 'S'

                    val pattern4 =
                        inputs.getOrNull(y - 1)?.getOrNull(x + 1) == 'S' &&
                            inputs.getOrNull(y + 1)?.getOrNull(x - 1) == 'M'

                    val isX = (pattern1 || pattern2) && (pattern3 || pattern4)
                    if (isX) {
                        count++
                    }
                }
            }
        }
        return count
    }
}
