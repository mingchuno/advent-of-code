package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day1Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day1.computePart1("2024/day1_example.txt").shouldBe(11) }

            it("real case") { Day1.computePart1("2024/day1_input.txt").shouldBe(1603498) }
        }

        describe("part 2") {
            it("example case") { Day1.computePart2("2024/day1_example.txt").shouldBe(31) }

            it("real case") { Day1.computePart2("2024/day1_input.txt").shouldBe(25574739) }
        }
    })
