package com.github.mingchuno.aoc.y2022

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class Day6Test :
    DescribeSpec({
        describe("part 1") {
            it("example case 1") {
                Day6.solvePart1("bvwbjplbgvbhsrlpgdmjqwftvncz".toList()).shouldBe(5)
                Day6.solvePart1("nppdvjthqldpwncqszvftbrmjlhg".toList()).shouldBe(6)
                Day6.solvePart1("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".toList()).shouldBe(10)
                Day6.solvePart1("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".toList()).shouldBe(11)
            }
            it("real case") { Day6.solvePart1Real().shouldBe(1034) }
        }

        describe("part 2") {
            it("example case") {
                Day6.solvePart2("mjqjpqmgbljsphdztnvjfqwrcgsmlb".toList()).shouldBe(19)
                Day6.solvePart2("bvwbjplbgvbhsrlpgdmjqwftvncz".toList()).shouldBe(23)
                Day6.solvePart2("nppdvjthqldpwncqszvftbrmjlhg".toList()).shouldBe(23)
                Day6.solvePart2("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".toList()).shouldBe(29)
                Day6.solvePart2("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".toList()).shouldBe(26)
            }
            it("real case") { Day6.solvePart2Real().shouldBe(2472) }
        }
    })
