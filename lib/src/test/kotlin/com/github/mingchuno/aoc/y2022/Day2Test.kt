package com.github.mingchuno.aoc.y2022

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day2Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day2.compute("2022/day2-example.txt").shouldBe(15) }
            it("real case") { Day2.compute("2022/day2-real.txt").shouldBe(12458) }
        }

        describe("part 2") {
            it("example case") { Day2.computePart2("2022/day2-example.txt").shouldBe(12) }
            it("real case") { Day2.computePart2("2022/day2-real.txt").shouldBe(12683) }
        }
    })
