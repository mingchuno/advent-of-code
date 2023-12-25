package com.github.mingchuno.aoc.y2023.d24

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.doubles.plusOrMinus
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe

private data class TestData(val a: Hailstones, val b: Hailstones, val expected: Boolean)

class Day24Test :
    DescribeSpec({
        describe("intersection point") {
            it("test case 1 inside") {
                val a = Hailstones(19, 13, 30, -2, 1, -2)
                val b = Hailstones(18, 19, 22, -1, -1, -2)
                val point = a.intersectionPoint(b)
                point.shouldNotBeNull()
                point.x.shouldBe(14.333.plusOrMinus(0.001))
                point.y.shouldBe(15.333.plusOrMinus(0.001))
            }

            it("test case 2 inside") {
                val a = Hailstones(19, 13, 30, -2, 1, -2)
                val b = Hailstones(20, 25, 34, -2, -2, -4)
                val point = a.intersectionPoint(b)
                point.shouldNotBeNull()
                point.x.shouldBe(11.667.plusOrMinus(0.001))
                point.y.shouldBe(16.667.plusOrMinus(0.001))
            }

            it("test case 3 outside") {
                val a = Hailstones(19, 13, 30, -2, 1, -2)
                val b = Hailstones(12, 31, 28, -1, -2, -1)
                val point = a.intersectionPoint(b)
                point.shouldNotBeNull()
                point.x.shouldBe(6.2.plusOrMinus(0.001))
                point.y.shouldBe(19.4.plusOrMinus(0.001))
            }

            it("same slope") {
                val a = Hailstones(18, 19, 22, -1, -1, -2)
                val b = Hailstones(20, 25, 35, -2, -2, -4)
                val point = a.intersectionPoint(b)
                point.shouldBeNull()
            }
        }

        describe("willIntersect") {
            withData(
                TestData(
                    Hailstones(19, 13, 30, -2, 1, -2),
                    Hailstones(18, 19, 22, -1, -1, -2),
                    true
                ),
                TestData(
                    Hailstones(19, 13, 30, -2, 1, -2),
                    Hailstones(20, 25, 34, -2, -2, -4),
                    true
                ),
                TestData(
                    Hailstones(19, 13, 30, -2, 1, -2),
                    Hailstones(20, 19, 15, 1, -5, -3),
                    false
                ),
                TestData(
                    Hailstones(18, 19, 22, -1, -1, -2),
                    Hailstones(20, 25, 34, -2, -2, -4),
                    false
                ),
                TestData(
                    Hailstones(18, 19, 22, -1, -1, -2),
                    Hailstones(12, 31, 28, -1, -2, -1),
                    false
                ),
                TestData(
                    Hailstones(18, 19, 22, -1, -1, -2),
                    Hailstones(20, 19, 15, 1, -5, -3),
                    false
                ),
                TestData(
                    Hailstones(20, 25, 34, -2, -2, -4),
                    Hailstones(12, 31, 28, -1, -2, -1),
                    false
                ),
                TestData(
                    Hailstones(20, 25, 34, -2, -2, -4),
                    Hailstones(20, 19, 15, 1, -5, -3),
                    false
                ),
                TestData(
                    Hailstones(12, 31, 28, -1, -2, -1),
                    Hailstones(20, 19, 15, 1, -5, -3),
                    false
                ),
            ) { (a, b, expected) ->
                willIntersect(a, b, 7, 27).shouldBe(expected)
            }
        }

        describe("part 1") {
            it("example case") { Day24.computePart1("2023/day24-example.txt", 7, 27).shouldBe(2) }

            it("real case") {
                Day24.computePart1("2023/day24-real.txt", 200000000000000L, 400000000000000L)
                    .shouldBe(15889)
            }
        }

        xdescribe("part 2") {
            it("example case") { Day24.computePart2("2023/day24-example.txt").shouldBe(0) }

            it("real case") { Day24.computePart2("2023/day24-real.txt").shouldBe(0) }
        }
    })
