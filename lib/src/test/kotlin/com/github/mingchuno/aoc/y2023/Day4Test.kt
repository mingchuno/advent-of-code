package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day4Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day4.computePart1("2023/day4-example.txt").shouldBe(13) }

            it("real case") { Day4.computePart1("2023/day4-real.txt").shouldBe(25174) }
        }

        describe("part 2") {
            it("example case") { Day4.computePart2("2023/day4-example.txt").shouldBe(30) }

            it("real case") { Day4.computePart2("2023/day4-real.txt").shouldBe(6420979) }
        }
    })
