package com.github.mingchuno.aoc.y2023.d20

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day20Test :
    DescribeSpec({
        describe("part 1") {
            it("example case 1") {
                Day20.computePart1("2023/day20-example1.txt").shouldBe(32000000)
            }
            it("example case 2") {
                Day20.computePart1("2023/day20-example2.txt").shouldBe(11687500)
            }

            it("real case") { Day20.computePart1("2023/day20-real.txt").shouldBe(0) }
        }

        describe("part 2") {
            it("example case") { Day20.computePart2("2023/day20-example1.txt").shouldBe(0) }

            it("real case") { Day20.computePart2("2023/day20-real.txt").shouldBe(0) }
        }
    })
