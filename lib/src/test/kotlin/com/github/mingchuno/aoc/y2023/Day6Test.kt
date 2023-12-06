package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day6Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day6.computePart1("2023/day6-example.txt").shouldBe(288) }

            it("real case") { Day6.computePart1("2023/day6-real.txt").shouldBe(114400) }
        }

        describe("part 2") {
            it("example case") { Day6.computePart2("2023/day6-example.txt").shouldBe(71503) }

            it("real case") { Day6.computePart2("2023/day6-real.txt").shouldBe(21039729) }
        }
    })
