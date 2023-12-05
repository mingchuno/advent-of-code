package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class Day5Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day5.computePart1("2023/day5-example.txt").shouldBe(35) }

            it("real case") { Day5.computePart1("2023/day5-real.txt").shouldBe(3374647) }
        }

        describe("part 2") {
            it("example case") { Day5.computePart2("2023/day5-example.txt").shouldBe(46) }

            xit("real case - burte force") {
                Day5.computePart2("2023/day5-real.txt").shouldBe(6082852)
            }

            xit("real case - burte force parallel") {
                Day5.computePart2Parallel("2023/day5-real.txt").shouldBe(6082852)
            }

            it("example case - range mapping") {
                Day5.computePart2RangeMapping("2023/day5-example.txt").shouldBe(46)
            }

            it("real case - range mapping") {
                Day5.computePart2RangeMapping("2023/day5-real.txt").shouldBe(6082852)
            }
        }

        describe("expand range") {
            it("range inside mapping") {
                listOf(Mapping(100, 10, 20))
                    .expandRange(LongRange(13, 17))
                    .shouldContainExactlyInAnyOrder(LongRange(103, 107))
            }

            it("range across 2 continuous mapping") {
                listOf(Mapping(100, 10, 10), Mapping(200, 20, 10))
                    .expandRange(LongRange(15, 25))
                    .shouldContainExactlyInAnyOrder(LongRange(105, 109), LongRange(200, 205))
            }

            it("range across 2 mapping with gap") {
                listOf(Mapping(100, 10, 10), Mapping(200, 30, 10))
                    .expandRange(LongRange(15, 35))
                    .shouldContainExactlyInAnyOrder(
                        LongRange(105, 109),
                        LongRange(20, 29),
                        LongRange(200, 205)
                    )
            }

            it("range across 1 mapping with gap") {
                listOf(Mapping(100, 10, 10))
                    .expandRange(LongRange(15, 25))
                    .shouldContainExactlyInAnyOrder(
                        LongRange(105, 109),
                        LongRange(20, 25),
                    )
            }
        }
    })
