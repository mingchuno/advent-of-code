package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day6Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day6.computePart1("2024/day6_example.txt").shouldBe(41) }

            it("real case") { Day6.computePart1("2024/day6_input.txt").shouldBe(4374) }
        }

        describe("part 2") {
            it("example case") { Day6.computePart2("2024/day6_example.txt").shouldBe(6) }

            it("real case") { Day6.computePart2("2024/day6_input.txt").shouldBe(1705) }
        }
    })
