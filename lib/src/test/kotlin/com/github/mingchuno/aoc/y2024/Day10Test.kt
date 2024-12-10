package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day10Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day10.computePart1("2024/day10_example.txt").shouldBe(36) }

            it("real case") { Day10.computePart1("2024/day10_input.txt").shouldBe(552) }
        }

        describe("part 2") {
            it("example case") { Day10.computePart2("2024/day10_example.txt").shouldBe(2858) }

            it("real case") { Day10.computePart2("2024/day10_input.txt").shouldBe(6381624803796L) }
        }
    })
