package com.github.mingchuno.aoc.y2023.d20

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day20 : Problem<Long> {
    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        // parse and prep state
        val mailingRoom = CentralMailingRoom()
        val modules = inputs.map { it.parseModule(mailingRoom) }
        mailingRoom.registerModules(modules)
        // start the loop
        return mailingRoom.selfTrigger()
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        TODO("Not yet implemented")
    }
}

private val broadcastRegexp = """broadcaster ->(.+)""".toRegex()
private val flipflopRegexp = """\%(\w+) ->(.+)""".toRegex()
private val conjunctionRegexp = """\&(\w+) ->(.+)""".toRegex()

private fun String.parseModule(mailingRoom: CentralMailingRoom): Module {
    return if (this.contains("broadcaster")) {
        val outputs = this.findMatches(broadcastRegexp).first().parseOutputs()
        BroadcastModule(output = outputs, mailingRoom)
    } else if (this.contains("%")) {
        val (key, outputs) = this.findMatches(flipflopRegexp)
        FlipFlopModule(self = key, output = outputs.parseOutputs(), mailingRoom)
    } else if (this.contains("&")) {
        val (key, outputs) = this.findMatches(conjunctionRegexp)
        ConjunctionModule(self = key, output = outputs.parseOutputs(), mailingRoom = mailingRoom)
    } else {
        throw Exception("This should not happen!")
    }
}

private fun String.findMatches(regex: Regex): List<String> =
    regex.find(this)?.groupValues?.drop(1)!!

private fun String.parseOutputs(): List<String> = this.split(",").map { it.trim() }
