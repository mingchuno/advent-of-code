package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day3Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day3.computePart1("2024/day3_example.txt").shouldBe(161) }

            it("real case") { Day3.computePart1("2024/day3_input.txt").shouldBe(178538786) }
        }

        describe("part 2") {
            it("example case") { Day3.computePart2("2024/day3_example_2.txt").shouldBe(48) }

            it("real case") { Day3.computePart2("2024/day3_input.txt").shouldBe(102467299) }
        }
    })
