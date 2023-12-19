package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day17Test :
    DescribeSpec({
        describe("part 1 example") {
            withData(
                nameFn = { (file, expected) -> "$file shortest path is $expected" },
                "2023/day17-example.txt" to 102,
                "2023/day17-example2.txt" to 14,
                "2023/day17-example3.txt" to 13,
                "2023/day17-example4.txt" to 13,
                "2023/day17-example5.txt" to 23,
                "2023/day17-example6.txt" to 7,
            ) { (file, expected) ->
                Day17.computePart1(file).shouldBe(expected)
            }
        }

        // ~300ms runtime
        describe("part 1 real") {
            it("real case") { Day17.computePart1("2023/day17-real.txt").shouldBe(1138) }
        }

        describe("part 2 example") {
            it("example case") { Day17.computePart2("2023/day17-example.txt").shouldBe(94) }
            it("example case part 2") { Day17.computePart2("2023/day17-example7.txt").shouldBe(71) }
        }

        // ~1.5s runtime
        describe("part 2 real") {
            it("real case") { Day17.computePart2("2023/day17-real.txt").shouldBe(1312) }
        }
    })
