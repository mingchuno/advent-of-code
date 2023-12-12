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
                    SpringConfig().search(springs.toList(), damagedCount).shouldBe(expected)
                }
            }
        }

        describe("possible config part 2") {
            context("possibleConfig") {
                withData(
                    nameFn = {
                        "${it.springs} ${it.damagedCount} should have ${it.expected} possible config"
                    },
                    SpringSpec("???.###", listOf(1, 1, 3), 1),
                    SpringSpec(".??..??...?##.", listOf(1, 1, 3), 16384),
                    SpringSpec("?#?#?#?#?#?#?#?", listOf(1, 3, 1, 6), 1),
                    SpringSpec("????.#...#...", listOf(4, 1, 1), 16),
                    SpringSpec("????.######..#####.", listOf(1, 6, 5), 2500),
                    SpringSpec("?###????????", listOf(3, 2, 1), 506250),
                ) { (springs, damagedCount, expected) ->
                    SpringConfig().part2(springs, damagedCount.joinToString(",")).shouldBe(expected)
                }
            }
        }

        describe("search2") {
            it("atom case 1") { SpringConfig().search2(listOf("###"), listOf(3)).shouldBe(1) }
            it("atom case 2") { SpringConfig().search2(listOf("???"), listOf(1, 1)).shouldBe(1) }
            it("atom case 3") {
                SpringConfig().search2(listOf("???", "###"), listOf(1, 1, 3)).shouldBe(1)
            }
            it("atom case 4") {
                SpringConfig().search2(listOf("??", "??", "?##"), listOf(1, 1, 3)).shouldBe(4)
            }
            it("atom case 5") {
                SpringConfig().search2(listOf("?###????????"), listOf(3, 2, 1)).shouldBe(10)
            }
            it("atom case 6") { SpringConfig().search2(listOf("?????"), listOf(4)).shouldBe(2) }
        }

        describe("part 1") {
            it("example case") { Day12.computePart1("2023/day12-example.txt").shouldBe(21) }

            it("real case") { Day12.computePart1("2023/day12-real.txt").shouldBe(7460) }
        }

        describe("part 2") {
            it("example case") { Day12.computePart2("2023/day12-example.txt").shouldBe(525152) }

            it("real case") { Day12.computePart2("2023/day12-real.txt").shouldBe(6720660274964) }
        }

        describe("part 2 test") {
            context("part2") {
                withData(
                    nameFn = {
                        "${it.springs} ${it.damagedCount} should have ${it.expected} possible config"
                    },
                    SpringSpec("??#??????#..?????", listOf(9, 2, 1), 3888),
                    SpringSpec("????.????.", listOf(1, 1), 345615702),
                ) { (springs, damagedCount, expected) ->
                    SpringConfig().part2(springs, damagedCount.joinToString(",")).shouldBe(expected)
                }
            }

            context("possibleConfig") {
                withData(
                    nameFn = {
                        "${it.springs} ${it.damagedCount} should have ${it.expected} possible config"
                    },
                    SpringSpec("??#??????#..?????", listOf(9, 2, 1), 3),
                    SpringSpec("????.????.", listOf(1, 1), 22),
                ) { (springs, damagedCount, expected) ->
                    SpringConfig().search(springs.toList(), damagedCount).shouldBe(expected)
                }
            }
        }
    })
