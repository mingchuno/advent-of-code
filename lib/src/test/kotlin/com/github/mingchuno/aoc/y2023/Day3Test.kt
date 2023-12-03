package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day3Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day3.computePart1("2023/day3-example.txt").shouldBe(4361) }

            it("real case") { Day3.computePart1("2023/day3-real.txt").shouldBe(539590) }
        }

        describe("part 2") {
            it("example case") { Day3.computePart2("2023/day3-example.txt").shouldBe(467835) }

            it("real case") { Day3.computePart2("2023/day3-real.txt").shouldBe(80703636) }
        }
    })
