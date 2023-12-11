package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day11Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day11.computePart1("2023/day11-example.txt").shouldBe(374) }

            it("real case") { Day11.computePart1("2023/day11-real.txt").shouldBe(9522407) }
        }

        describe("part 2") {
            it("example case - 10x") {
                Day11.computePart2("2023/day11-example.txt", 10).shouldBe(1030)
            }
            it("example case - 100x") {
                Day11.computePart2("2023/day11-example.txt", 100).shouldBe(8410)
            }

            it("real case") {
                Day11.computePart2("2023/day11-real.txt", 1000000).shouldBe(544723432977)
            }
        }
    })
