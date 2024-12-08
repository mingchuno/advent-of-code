package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day8Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day8.computePart1("2024/day8_example.txt").shouldBe(14) }

            it("real case") { Day8.computePart1("2024/day8_input.txt").shouldBe(396) }
        }

        describe("part 2") {
            it("example case") { Day8.computePart2("2024/day8_example.txt").shouldBe(34) }

            it("real case") { Day8.computePart2("2024/day8_input.txt").shouldBe(1200) }
        }
    })
