package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day4Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day4.computePart1("2024/day4_example.txt").shouldBe(18) }

            it("real case") { Day4.computePart1("2024/day4_input.txt").shouldBe(2517) }
        }

        describe("part 2") {
            it("example case") { Day4.computePart2("2024/day4_example.txt").shouldBe(9) }

            it("real case") { Day4.computePart2("2024/day4_input.txt").shouldBe(1960) }
        }
    })
