package com.github.mingchuno.aoc.y2023.d20

import com.github.mingchuno.aoc.utils.product
import java.util.*

sealed interface Module {
    val self: String
    val output: List<String>
    val mailingRoom: CentralMailingRoom

    fun isAtInitialState(): Boolean

    fun receivePulse(pulse: Pulse, from: String)

    fun sendPulseDown(pulse: Pulse) =
        output.forEach { out ->
            mailingRoom.receiveMail(Mail(from = self, to = out, pulse = pulse))
        }
}

enum class OnOffState {
    ON,
    OFF
}

enum class Pulse {
    HIGH,
    LOW
}

class FlipFlopModule(
    override val self: String,
    override val output: List<String>,
    override val mailingRoom: CentralMailingRoom
) : Module {

    companion object {
        private val initialState = OnOffState.OFF
    }

    private var state: OnOffState = initialState

    override fun isAtInitialState(): Boolean {
        return state == initialState
    }

    override fun receivePulse(pulse: Pulse, from: String) {
        when (pulse) {
            Pulse.LOW -> {
                when (state) {
                    OnOffState.OFF -> {
                        sendPulseDown(Pulse.HIGH)
                        state = OnOffState.ON
                    }
                    OnOffState.ON -> {
                        sendPulseDown(Pulse.LOW)
                        state = OnOffState.OFF
                    }
                }
            }
            Pulse.HIGH -> {}
        }
    }

    override fun toString(): String {
        return "FLIP(self=$self,output=$output)"
    }
}

class ConjunctionModule(
    override val self: String,
    override val output: List<String>,
    override val mailingRoom: CentralMailingRoom
) : Module {
    private var state = mutableMapOf<String, Pulse>()

    companion object {
        const val SPECIAL_MODULE = "lb" // for part 2
    }

    fun setInputsAndInit(input: Set<String>) {
        this.state = input.associateWith { Pulse.LOW }.toMutableMap()
        if (self == SPECIAL_MODULE) {
            mailingRoom.initStateForPart2(this.state.mapValues { -1L })
        }
    }

    override fun isAtInitialState(): Boolean {
        return state.all { (_, p) -> p == Pulse.LOW }
    }

    override fun receivePulse(pulse: Pulse, from: String) {
        // special for part 2
        val shouldRegisterPart2Count =
            state[from] != pulse && self == SPECIAL_MODULE && mailingRoom.part2State()[from]!! < 0
        if (shouldRegisterPart2Count) {
            println(
                "buttonPressCount=${mailingRoom.buttonPressCount()};from=${from};pulse=${pulse}"
            )
            mailingRoom.setPart2Count(from)
        }
        state[from] = pulse
        val allHigh = state.all { (_, p) -> p == Pulse.HIGH }
        sendPulseDown(if (allHigh) Pulse.LOW else Pulse.HIGH)
    }

    override fun toString(): String {
        return "CON(self=${self},output=$output)"
    }
}

class BroadcastModule(
    override val output: List<String>,
    override val mailingRoom: CentralMailingRoom
) : Module {
    override val self: String = "broadcast"

    override fun isAtInitialState(): Boolean = true

    override fun receivePulse(pulse: Pulse, from: String) {
        sendPulseDown(pulse)
    }

    override fun toString(): String {
        return "BROADCAST(output=$output)"
    }
}

data class Mail(val to: String, val pulse: Pulse, val from: String)

/** Make it blocking so that it can handle mail one by one! */
class CentralMailingRoom {
    private val q = LinkedList<Mail>()

    private var modules = mapOf<String, Module>()

    private var highCount = 0L
    private var lowCount = 0L
    private var buttonPressCount = 0L
    private var stateForPart2 = mutableMapOf<String, Long>()

    fun initStateForPart2(state: Map<String, Long>) {
        stateForPart2 = state.toMutableMap()
    }

    fun part2State(): Map<String, Long> = stateForPart2

    fun setPart2Count(key: String) {
        stateForPart2[key] = buttonPressCount
    }

    private fun part2Terminate(): Boolean = stateForPart2.all { (_, v) -> v > 0 }

    fun buttonPressCount(): Long {
        return buttonPressCount
    }

    private fun pressButton() {
        buttonPressCount++
        receiveMail(Mail(to = "broadcast", pulse = Pulse.LOW, from = "button"))
    }

    fun registerModules(modules: List<Module>) {
        val map = modules.associateBy { it.self }
        modules.filterIsInstance<ConjunctionModule>().forEach { con ->
            val inputs = modules.filter { it.output.contains(con.self) }.map { it.self }.toSet()
            con.setInputsAndInit(inputs)
        }
        this.modules = map
    }

    fun receiveMail(mail: Mail) {
        q.add(mail)
    }

    private fun processMail() {
        while (q.isNotEmpty()) {
            val mail = q.remove()
            if (mail.pulse == Pulse.HIGH) highCount++
            if (mail.pulse == Pulse.LOW) lowCount++
            modules[mail.to]?.receivePulse(mail.pulse, mail.from)
        }
    }

    companion object {
        private const val COUNT = 1000
    }

    fun triggerPart1(): Long {
        assert(modules.isNotEmpty()) { "forgot to call registerModules?" }
        while (buttonPressCount < COUNT) {
            pressButton()
            processMail()
        }
        return lowCount * highCount
    }

    fun triggerPart2(): Long {
        assert(modules.isNotEmpty()) { "forgot to call registerModules?" }
        while (!part2Terminate()) {
            pressButton()
            processMail()
        }
        return stateForPart2.values.product()
    }
}
