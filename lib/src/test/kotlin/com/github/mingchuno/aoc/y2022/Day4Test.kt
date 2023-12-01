package com.github.mingchuno.aoc.y2022

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day4Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day4.solvePart1Example().shouldBe("2") }
            it("real case") { Day4.solvePart1Real().shouldBe("507") }
        }

        describe("part 2") {
            it("example case") { Day4.solvePart2Example().shouldBe("4") }
            it("real case") { Day4.solvePart2Real().shouldBe("897") }
        }
    })
