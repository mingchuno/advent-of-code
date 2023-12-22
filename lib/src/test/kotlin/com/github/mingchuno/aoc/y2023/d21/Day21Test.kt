package com.github.mingchuno.aoc.y2023.d21

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

class Day21Test :
    DescribeSpec({
        describe("part 1") {
            it("example case") { Day21.computePart1("2023/day21-example.txt", 6).shouldBe(16) }
            it("real case") { Day21.computePart1("2023/day21-real.txt", 64).shouldBe(3562) }
        }

        describe("part 2 example") {
            withData(
                nameFn = { (steps, expected) -> "$steps expected $expected" },
                6 to 16,
                10 to 50,
                50 to 1594,
                100 to 6536,
                //                500 to 167004,
                //                1000 to 668697,
                //                5000 to 16733044
            ) { (steps, expected) ->
                Day21.computePart2("2023/day21-example.txt", steps).shouldBe(expected)
            }
        }

        describe("part 2 real") {
            describe("part 1") {
                it("real case - 64") {
                    Day21.computePart2("2023/day21-real.txt", 64).shouldBe(3562)
                }
            }

            describe("55 * n") {
                // 26501365 = 5 * 11 * 481843
                it("real case - 55") {
                    Day21.computePart2("2023/day21-real.txt", 55).shouldBe(2588)
                }
                it("real case - 110") {
                    Day21.computePart2("2023/day21-real.txt", 110).shouldBe(10408)
                }
                it("real case - 165") {
                    Day21.computePart2("2023/day21-real.txt", 165).shouldBe(23128)
                }
                it("real case - 166") {
                    Day21.computePart2("2023/day21-real.txt", 166).shouldBe(23128)
                }
                it("real case - 220") {
                    Day21.computePart2("2023/day21-real.txt", 220).shouldBe(41347)
                }
            }

            describe("131 * n + 65") {
                // 202300 * 131 + 65 = 26501365
                // n = 0
                it("real case - 65") {
                    Day21.computePart2("2023/day21-real.txt", 65).shouldBe(3682)
                }
                // n = 1
                it("real case - 196") {
                    Day21.computePart2("2023/day21-real.txt", 196).shouldBe(32768)
                }
                // n = 2
                it("real case - 327") {
                    Day21.computePart2("2023/day21-real.txt", 327).shouldBe(90820)
                }
                // n = 3
                it("real case - 458") {
                    Day21.computePart2("2023/day21-real.txt", 458).shouldBe(177838)
                }
            }

            //            it("real case - 26501365") {
            //                Day21.computePart2("2023/day21-real.txt", 26501365).shouldBe(0)
            //            }
        }
        // 602750650598082 too high
        // 602747671120000 too high
        // 81850175401
        // 592723929260582!!!!

    })
