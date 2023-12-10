package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day10Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day10.computePart1("2023/day10-example.txt").shouldBe(8) }

            it("real case") { Day10.computePart1("2023/day10-real.txt").shouldBe(6714) }
        }

        describe("part 2") {
            it("example case") { Day10.computePart2("2023/day10-example.txt").shouldBe(0) }

            it("real case") { Day10.computePart2("2023/day10-real.txt").shouldBe(0) }
        }
    })
