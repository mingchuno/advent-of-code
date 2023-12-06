// Generated using IDE template `aoct`
package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day7Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day7.computePart1("/day7-example.txt").shouldBe(0) }

            it("real case") { Day7.computePart1("/day7-real.txt").shouldBe(0) }
        }

        describe("part 2") {
            it("example case") { Day7.computePart2("/day7-example.txt").shouldBe(0) }

            it("real case") { Day7.computePart2("/day7-real.txt").shouldBe(0) }
        }
    })
