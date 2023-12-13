package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day13Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day13.computePart1("2023/day13-example.txt").shouldBe(405) }

            it("real case") { Day13.computePart1("2023/day13-real.txt").shouldBe(37718) }
        }

        describe("part 2") {
            it("example case") { Day13.computePart2("2023/day13-example.txt").shouldBe(400) }

            it("real case") { Day13.computePart2("2023/day13-real.txt").shouldBe(40995) }
        }
    })
