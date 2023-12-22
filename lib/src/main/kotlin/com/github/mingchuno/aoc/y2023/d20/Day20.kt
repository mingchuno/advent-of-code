package com.github.mingchuno.aoc.y2023.d20

import com.github.mingchuno.aoc.interfaceing.Problem
import com.github.mingchuno.aoc.utils.ThisShouldNotHappenException
import com.github.mingchuno.aoc.utils.readFileFromResource

object Day20 : Problem<Long> {
    override fun computePart1(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        // parse and prep state
        val mailingRoom = CentralMailingRoom()
        val modules = inputs.map { it.parseModule(mailingRoom) }
        mailingRoom.registerModules(modules)
        // start the loop
        return mailingRoom.triggerPart1()
    }

    override fun computePart2(inputFile: String): Long {
        val inputs = inputFile.readFileFromResource()
        // parse and prep state
        val mailingRoom = CentralMailingRoom()
        val modules = inputs.map { it.parseModule(mailingRoom) }
        mailingRoom.registerModules(modules)
        // start the loop
        return mailingRoom.triggerPart2()
    }
}

private fun String.parseModule(mailingRoom: CentralMailingRoom): Module {
    return if (this.contains("broadcaster")) {
        val (_, outputs) = findMatches(this)
        BroadcastModule(output = outputs, mailingRoom)
    } else if (this.contains("%")) {
        val (key, outputs) = findMatches(this)
        FlipFlopModule(self = key, output = outputs, mailingRoom = mailingRoom)
    } else if (this.contains("&")) {
        val (key, outputs) = findMatches(this)
        ConjunctionModule(self = key, output = outputs, mailingRoom = mailingRoom)
    } else {
        throw ThisShouldNotHappenException()
    }
}

private val parseRegex = """(\w+)""".toRegex()

private fun findMatches(it: String): Pair<String, List<String>> {
    val matches = parseRegex.findAll(it).map { it.value }.toList()
    val key = matches.first()
    val outputs = matches.drop(1)
    return key to outputs
}
