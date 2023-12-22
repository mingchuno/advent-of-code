package com.github.mingchuno.aoc.y2023.d22

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class Day22Test :
    DescribeSpec({
        describe("atom") {
            it("brick overlap") {
                val a = Brick(Position(2, 5, 146), Position(2, 7, 146))
                val b = Brick(Position(2, 4, 146), Position(2, 6, 146))
                a.overlap(b).shouldBeTrue()
            }
        }

        describe("part 1") {
            it("example case") { Day22.computePart1("2023/day22-example.txt").shouldBe(5) }

            it("real case") { Day22.computePart1("2023/day22-real.txt").shouldBe(488) }
        }

        describe("part 1 special example") {
            withData(
                nameFn = { (file, expected) -> "$file expected $expected" },
                "2023/day22-example.txt" to 5,
                "2023/day22-special-example-1.txt" to 4,
                "2023/day22-special-example-2.txt" to 2,
                "2023/day22-special-example-3.txt" to 2,
                "2023/day22-special-example-4.txt" to 3,
                "2023/day22-special-example-5.txt" to 4,
                "2023/day22-special-example-6.txt" to 4,
                "2023/day22-special-example-7.txt" to 3,
            ) { (file, expected) ->
                Day22.computePart1(file).shouldBe(expected)
            }
        }

        xdescribe("part 2") {
            it("example case") { Day22.computePart2("2023/day22-example.txt").shouldBe(0) }

            it("real case") { Day22.computePart2("2023/day22-real.txt").shouldBe(0) }
        }
    })
