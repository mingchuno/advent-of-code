package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day8Test :
    DescribeSpec({
        describe("part 1") {
            it("example case 1") { Day8.computePart1("2023/day8-example.txt").shouldBe(2) }
            it("example case 2") { Day8.computePart1("2023/day8-example-2.txt").shouldBe(6) }
            it("real case") { Day8.computePart1("2023/day8-real.txt").shouldBe(19631) }
        }

        describe("part 2") {
            it("example case") { Day8.computePart2("2023/day8-example-part2.txt").shouldBe(6) }

            //            it("real case") { Day8.computePart2("2023/day8-real.txt").shouldBe(0) }
        }
    })
