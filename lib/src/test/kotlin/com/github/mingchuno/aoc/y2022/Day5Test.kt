package com.github.mingchuno.aoc.y2022

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day5Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day5.solvePart1Example().shouldBe("CMZ") }
            it("real case") { Day5.solvePart1Real().shouldBe("RLFNRTNFB") }
        }

        describe("part 2") {
            it("example case") { Day5.solvePart2Example().shouldBe("MCD") }
            it("real case") { Day5.solvePart2Real().shouldBe("MHQTLJRLB") }
        }
    })
