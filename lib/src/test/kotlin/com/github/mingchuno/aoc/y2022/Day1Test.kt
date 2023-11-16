package com.github.mingchuno.aoc.y2022

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day1Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day1.compute("2022/day1-example.txt").shouldBe(24000) }

            it("real case") { Day1.compute("2022/day1-real.txt").shouldBe(66616) }
        }

        describe("part 2") {
            it("example case") { Day1.computeTop3("2022/day1-example.txt").shouldBe(45000) }

            it("real case") { Day1.computeTop3("2022/day1-real.txt").shouldBe(199172) }
        }
    })
