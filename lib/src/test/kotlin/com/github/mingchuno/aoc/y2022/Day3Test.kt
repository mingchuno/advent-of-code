package com.github.mingchuno.aoc.y2022

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day3Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day3.compute("2022/day3-example.txt").shouldBe(157) }
            it("real case") { Day3.compute("2022/day3-real.txt").shouldBe(8349) }
        }

        describe("part 2") {
            it("example case") { Day3.computePart2("2022/day3-example.txt").shouldBe(70) }
            it("real case") { Day3.computePart2("2022/day3-real.txt").shouldBe(2681) }
        }
    })
