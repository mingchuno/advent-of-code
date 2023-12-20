package com.github.mingchuno.aoc.y2023.d21

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day21Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day21.computePart1("2023/day21-example.txt").shouldBe(0) }

            it("real case") { Day21.computePart1("2023/day21-real.txt").shouldBe(0) }
        }

        describe("part 2") {
            it("example case") { Day21.computePart2("2023/day21-example.txt").shouldBe(0) }

            it("real case") { Day21.computePart2("2023/day21-real.txt").shouldBe(0) }
        }
    })
