// Generated using IDE template `aoct`
package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day7Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day7.computePart1("2023/day7-example.txt").shouldBe(6440) }

            it("real case") { Day7.computePart1("2023/day7-real.txt").shouldBe(251927063) }
        }

        describe("part 2") {
            it("example case") { Day7.computePart2("2023/day7-example.txt").shouldBe(5905) }

            it("real case") { Day7.computePart2("2023/day7-real.txt").shouldBe(255632664) }
        }
    })
