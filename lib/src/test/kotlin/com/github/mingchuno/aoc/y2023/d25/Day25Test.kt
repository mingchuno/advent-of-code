package com.github.mingchuno.aoc.y2023.d25

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day25Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day25.computePart1("2023/day25-example.txt").shouldBe(0) }

            it("real case") { Day25.computePart1("2023/day25-real.txt").shouldBe(0) }
        }

        describe("part 2") {
            it("example case") { Day25.computePart2("2023/day25-example.txt").shouldBe(0) }

            it("real case") { Day25.computePart2("2023/day25-real.txt").shouldBe(0) }
        }
    })
