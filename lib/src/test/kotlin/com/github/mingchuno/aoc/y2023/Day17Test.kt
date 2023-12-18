package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day17Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day17.computePart1("2023/day17-example.txt").shouldBe(102) }
            it("example case 2") { Day17.computePart1("2023/day17-example2.txt").shouldBe(14) }
            it("example case 3") { Day17.computePart1("2023/day17-example3.txt").shouldBe(13) }
            it("example case 4") { Day17.computePart1("2023/day17-example4.txt").shouldBe(13) }
            it("example case 5") { Day17.computePart1("2023/day17-example5.txt").shouldBe(24) }

            it("real case") { Day17.computePart1("2023/day17-real.txt").shouldBe(0) }
            // 1189 high
        }

        describe("part 2") {
            it("example case") { Day17.computePart2("2023/day17-example.txt").shouldBe(0) }

            it("real case") { Day17.computePart2("2023/day17-real.txt").shouldBe(0) }
        }
    })
