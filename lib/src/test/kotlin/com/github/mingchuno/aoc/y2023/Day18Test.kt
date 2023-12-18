package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day18Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day18.computePart1("2023/day18-example.txt").shouldBe(62) }

            it("real case") { Day18.computePart1("2023/day18-real.txt").shouldBe(62365) }
        }

        describe("part 2") {
            it("example case") {
                Day18.computePart2("2023/day18-example.txt").shouldBe(952408144115L)
            }

            it("real case") { Day18.computePart2("2023/day18-real.txt").shouldBe(159485361249806L) }
        }
    })
