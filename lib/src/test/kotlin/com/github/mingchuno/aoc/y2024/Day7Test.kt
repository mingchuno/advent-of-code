package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day7Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day7.computePart1("2024/day7_example.txt").shouldBe(3749) }

            it("real case") { Day7.computePart1("2024/day7_input.txt").shouldBe(2437272016585L) }
        }

        describe("part 2") {
            it("example case") { Day7.computePart2("2024/day7_example.txt").shouldBe(11387) }

            it("real case") { Day7.computePart2("2024/day7_input.txt").shouldBe(162987117690649L) }
        }
    })
