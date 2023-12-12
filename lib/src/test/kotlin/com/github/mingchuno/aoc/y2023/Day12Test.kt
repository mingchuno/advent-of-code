package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

private data class SpringSpec(val springs: String, val damagedCount: List<Int>, val expected: Int)

class Day12Test :
    DescribeSpec({
        describe("possible config") {
            context("possibleConfig") {
                withData(
                    nameFn = {
                        "${it.springs} ${it.damagedCount} should have ${it.expected} possible config"
                    },
                    SpringSpec("#.#.###", listOf(1, 1, 3), 1),
                    SpringSpec("???.###", listOf(1, 1, 3), 1),
                    SpringSpec(".??..??...?##.", listOf(1, 1, 3), 4),
                    SpringSpec("?#?#?#?#?#?#?#?", listOf(1, 3, 1, 6), 1),
                    SpringSpec("????.#...#...", listOf(4, 1, 1), 1),
                    SpringSpec("????.######..#####", listOf(1, 6, 5), 4),
                    SpringSpec("?###????????", listOf(3, 2, 1), 10),
                ) { (springs, damagedCount, expected) ->
                    Day12.possibleConfig(springs.toList(), damagedCount).shouldBe(expected)
                }
            }
        }

        describe("part 1") {
            it("example case") { Day12.computePart1("2023/day12-example.txt").shouldBe(21) }

            it("real case") { Day12.computePart1("2023/day12-real.txt").shouldBe(7460) }
        }

        describe("part 2") {
            it("example case") { Day12.computePart2("2023/day12-example.txt").shouldBe(0) }

            it("real case") { Day12.computePart2("2023/day12-real.txt").shouldBe(0) }
        }
    })
