package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day2Test :
    DescribeSpec({
        describe("part 1") {
            val target = RGB(red = 12, green = 13, blue = 14)
            it("example case") { Day2.computePart1("2023/day2-example.txt", target).shouldBe(8) }

            it("real case") { Day2.computePart1("2023/day2-real.txt", target).shouldBe(2416) }
        }

        describe("part 2") {
            it("example case") { Day2.computePart2("2023/day2-example.txt").shouldBe(2286) }

            it("real case") { Day2.computePart2("2023/day2-real.txt").shouldBe(63307) }
        }
    })
