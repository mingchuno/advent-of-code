package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day14Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day14.computePart1("2023/day14-example.txt").shouldBe(0) }

            it("real case") { Day14.computePart1("2023/day14-real.txt").shouldBe(0) }
        }

        describe("part 2") {
            it("example case") { Day14.computePart2("2023/day14-example.txt").shouldBe(0) }

            it("real case") { Day14.computePart2("2023/day14-real.txt").shouldBe(0) }
        }
    })
