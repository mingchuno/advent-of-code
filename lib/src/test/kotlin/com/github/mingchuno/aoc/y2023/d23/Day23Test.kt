package com.github.mingchuno.aoc.y2023.d23

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day23Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day23.computePart1("2023/day23-example.txt").shouldBe(94) }

            it("real case") { Day23.computePart1("2023/day23-real.txt").shouldBe(0) }
        }

        describe("part 2") {
            it("example case") { Day23.computePart2("2023/day23-example.txt").shouldBe(0) }

            it("real case") { Day23.computePart2("2023/day23-real.txt").shouldBe(0) }
        }
    })
