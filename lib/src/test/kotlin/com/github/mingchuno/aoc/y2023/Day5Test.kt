package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day5Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day5.computePart1("2023/day5-example.txt").shouldBe(35) }

            it("real case") { Day5.computePart1("2023/day5-real.txt").shouldBe(3374647) }
        }

        describe("part 2") {
            it("example case") { Day5.computePart2("2023/day5-example.txt").shouldBe(46) }

            it("real case") { Day5.computePart2("2023/day5-real.txt").shouldBe(6082852) }
        }
    })
