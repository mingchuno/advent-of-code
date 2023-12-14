package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

private data class PlatformColumnSpec(val column: String, val expected: Int)

class Day14Test :
    DescribeSpec({
        describe("atom case") {
            withData(
                nameFn = { "${it.column} should have load ${it.expected}" },
                PlatformColumnSpec("OO.O.O..##", 34),
                PlatformColumnSpec("...OO....O", 27),
                PlatformColumnSpec(".O...#O..O", 17),
            ) { (column, expected) ->
                PlatformColumn(column.toList()).computeLoad().shouldBe(expected)
            }
        }

        describe("part 1") {
            it("example case") { Day14.computePart1("2023/day14-example.txt").shouldBe(136) }

            it("real case") { Day14.computePart1("2023/day14-real.txt").shouldBe(107142) }
        }

        describe("part 2") {
            it("example case") { Day14.computePart2("2023/day14-example.txt").shouldBe(0) }

            it("real case") { Day14.computePart2("2023/day14-real.txt").shouldBe(0) }
        }
    })
