package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe

private data class PlatformColumnSpec(val column: String, val expected: Int)

private data class PlatformColumnSpec2(val column: String, val expected: List<Int>)

class Day14Test :
    DescribeSpec({
        describe("atom case part 1") {
            withData(
                nameFn = { "${it.column} should have load ${it.expected}" },
                PlatformColumnSpec("OO.O.O..##", 34),
                PlatformColumnSpec("...OO....O", 27),
                PlatformColumnSpec(".O...#O..O", 17),
            ) { (column, expected) ->
                PlatformColumn(column.toList()).computeLoad().shouldBe(expected)
            }
        }

        describe("atom case part 2") {
            withData(
                nameFn = { "${it.column} should have load ${it.expected}" },
                PlatformColumnSpec2("OO.O.O..##", listOf(0, 1, 2, 3)),
                PlatformColumnSpec2("...OO....O", listOf(0, 1, 2)),
                PlatformColumnSpec2(".O...#O..O", listOf(0, 6, 7)),
            ) { (column, expected) ->
                PlatformColumn(column.toList()).tiltedRock().shouldContainExactly(expected)
            }
        }

        describe("part 1") {
            it("example case") { Day14.computePart1("2023/day14-example.txt").shouldBe(136) }

            it("real case") { Day14.computePart1("2023/day14-real.txt").shouldBe(107142) }
        }

        describe("part 2") {
            it("example case") { Day14.computePart2("2023/day14-example.txt").shouldBe(64) }

            it("real case") { Day14.computePart2("2023/day14-real.txt").shouldBe(0) }
        }
    })
