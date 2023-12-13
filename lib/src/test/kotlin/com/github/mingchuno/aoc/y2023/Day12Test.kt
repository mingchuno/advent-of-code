package com.github.mingchuno.aoc.y2023

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe

private data class SpringSpec(val springs: String, val damagedCount: List<Int>, val expected: Int)

class Day12Test :
    DescribeSpec({
        describe("line example - scale=1") {
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
                SpringSpec("??#??????#..?????", listOf(9, 2, 1), 3),
                SpringSpec("????.????.", listOf(1, 1), 22),
                SpringSpec("??.??.?##", listOf(1, 1, 3), 4),
                SpringSpec("?????", listOf(4), 2),
            ) { (springs, damagedCount, expected) ->
                SpringConfig(1).search(springs, damagedCount.joinToString(",")).shouldBe(expected)
                SpringConfigV2(springs, damagedCount.joinToString(","), 1)
                    .search()
                    .shouldBe(expected)
            }
        }

        describe("line example - scale=5") {
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
                SpringSpec("###", listOf(3), 1),
                SpringSpec("???", listOf(1, 1), 1),
                SpringSpec("???.###", listOf(1, 1, 3), 1),
                SpringSpec("??#??????#..?????", listOf(9, 2, 1), 3888),
                SpringSpec("????.????.", listOf(1, 1), 345615702),
            ) { (springs, damagedCount, expected) ->
                SpringConfig(5).search(springs, damagedCount.joinToString(",")).shouldBe(expected)
                SpringConfigV2(springs, damagedCount.joinToString(","), 5)
                    .search()
                    .shouldBe(expected)
            }
        }

        describe("part 1") {
            it("example case") { Day12.computePart1("2023/day12-example.txt").shouldBe(21) }

            it("real case") { Day12.computePart1("2023/day12-real.txt").shouldBe(7460) }
        }

        describe("part 2") {
            it("example case") { Day12.computePart2("2023/day12-example.txt").shouldBe(525152) }

            it("real case") { Day12.computePart2("2023/day12-real.txt").shouldBe(6720660274964) }
        }
    })
