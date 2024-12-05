package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day5Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day5.computePart1("2024/day5_example.txt").shouldBe(143) }

            it("real case") { Day5.computePart1("2024/day5_input.txt").shouldBe(4790) }
        }

        describe("part 2") {
            it("example case") { Day5.computePart2("2024/day5_example.txt").shouldBe(123) }

            it("real case") { Day5.computePart2("2024/day5_input.txt").shouldBe(6319) }
        }
    })
