package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day19Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day19.computePart1("2023/day19-example.txt").shouldBe(19114) }

            it("real case") { Day19.computePart1("2023/day19-real.txt").shouldBe(353046) }
        }

        describe("part 2") {
            it("example case") {
                Day19.computePart2("2023/day19-example.txt").shouldBe(167409079868000)
            }

            it("real case") { Day19.computePart2("2023/day19-real.txt").shouldBe(125355665599537) }
        }
    })
