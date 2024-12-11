package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day11Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day11.computePart1("2024/day11_example.txt").shouldBe(55312) }

            it("real case") { Day11.computePart1("2024/day11_input.txt").shouldBe(190865) }
        }

        describe("part 2") {
            it("example case") {
                Day11.computePart2("2024/day11_example.txt").shouldBe(65601038650482L)
            }

            it("real case") {
                Day11.computePart2("2024/day11_input.txt").shouldBe(225404711855335L)
            }
        }
    })
