package com.github.mingchuno.aoc.y2024

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day9Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day9.computePart1("2024/day9_example.txt").shouldBe(1928) }

            it("real case") { Day9.computePart1("2024/day9_input.txt").shouldBe(6359213660505L) }
        }

        describe("part 2") {
            it("example case") { Day9.computePart2("2024/day9_example.txt").shouldBe(2858) }

            it("real case") { Day9.computePart2("2024/day9_input.txt").shouldBe(6381624803796L) }
        }
    })
