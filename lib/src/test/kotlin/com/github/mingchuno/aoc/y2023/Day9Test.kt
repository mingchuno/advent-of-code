package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day9Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day9.computePart1("2023/day9-example.txt").shouldBe(114) }

            it("real case") { Day9.computePart1("2023/day9-real.txt").shouldBe(1789635132) }
        }

        describe("part 2") {
            it("example case") { Day9.computePart2("2023/day9-example.txt").shouldBe(2) }

            it("real case") { Day9.computePart2("2023/day9-real.txt").shouldBe(913) }
        }
    })
