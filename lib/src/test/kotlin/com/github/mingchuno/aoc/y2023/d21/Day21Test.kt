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

        describe("part 2 experiment") {
            describe("131 * n + 65") {
                // observe: 202300 * 131 + 65 = 26501365
                withData(
                    nameFn = { (n, expected) ->
                        "131 * $n + 65 = ${131 * n + 65} steps => expected $expected"
                    },
                    0 to 3682,
                    1 to 32768,
                    2 to 90820,
                    3 to 177838,
                ) { (n, expected) ->
                    val steps = 131 * n + 65
                    Day21.computePart2("2023/day21-real.txt", steps).shouldBe(expected)
                }
            }
        }

        describe("Part 2 actual") {
            // observe: 202300 * 131 + 65 = 26501365
            it("real case - 26501365") {
                Day21.computePart2Quadratic("2023/day21-real.txt", 202300)
                    .shouldBe(592723929260582L)
            }
        }
    })
