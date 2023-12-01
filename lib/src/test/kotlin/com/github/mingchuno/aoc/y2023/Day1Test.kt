package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day1Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day1.compute("2023/day1-example.txt").shouldBe(142) }

            it("real case") { Day1.compute("2023/day1-real.txt").shouldBe(54597) }
        }

        describe("part 2") {
            it("example case") { Day1.computePart2("2023/day1-example-2.txt").shouldBe(281) }

            it("real case") { Day1.computePart2("2023/day1-real.txt").shouldBe(54504) }
        }
    })
