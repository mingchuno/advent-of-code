package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day2Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day2.computePart1("2024/day2_example.txt").shouldBe(2) }

            it("real case") { Day2.computePart1("2024/day2_input.txt").shouldBe(224) }
        }

        describe("part 2") {
            it("example case") { Day2.computePart2("2024/day2_example.txt").shouldBe(4) }

            it("real case") { Day2.computePart2("2024/day2_input.txt").shouldBe(293) }
        }
    })
