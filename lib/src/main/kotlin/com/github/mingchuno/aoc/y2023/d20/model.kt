package com.github.mingchuno.aoc.y2023.d20

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

    private val initialState = OnOffState.OFF

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
}

class ConjunctionModule(
    override val self: String,
    override val output: List<String>,
    override val mailingRoom: CentralMailingRoom
) : Module {
    private var input: Set<String> = setOf()

    private var state = mutableMapOf<String, Pulse>()

    fun setInputsAndInit(input: Set<String>) {
        this.input = input
        this.state = input.associateWith { Pulse.LOW }.toMutableMap()
    }

    override fun isAtInitialState(): Boolean {
        return state.all { (_, p) -> p == Pulse.LOW }
    }

    override fun receivePulse(pulse: Pulse, from: String) {
        state[from] = pulse
        val allHigh = state.all { (_, p) -> p == Pulse.HIGH }
        sendPulseDown(if (allHigh) Pulse.LOW else Pulse.HIGH)
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
}

data class Mail(val to: String, val pulse: Pulse, val from: String)

/** Make it blocking so that it can handle mail one by one! */
class CentralMailingRoom {
    private val q = LinkedList<Mail>()

    private var modules = mutableMapOf<String, Module>()

    private var highCount = 0L
    private var lowCount = 0L
    private var buttonPressCount = 0L

    private fun resetCount() {
        highCount = 0
        lowCount = 0
        buttonPressCount = 0
    }

    private fun pressButton() {
        buttonPressCount++
        receiveMail(Mail(to = "broadcast", pulse = Pulse.LOW, from = "button"))
    }

    fun registerModules(modules: List<Module>) {
        val map = modules.associateBy { it.self }.toMutableMap()
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
            // fire all the mail in this batch
            val mail = q.remove()
            if (mail.pulse == Pulse.HIGH) highCount++
            if (mail.pulse == Pulse.LOW) lowCount++
            //            println("from=${mail.from};pulse=${mail.pulse};to=${mail.to}")
            modules[mail.to]?.receivePulse(mail.pulse, mail.from)
        }
    }

    private fun backToStartingState(): Boolean = modules.all { (_, m) -> m.isAtInitialState() }

    private val COUNT = 1000

    fun selfTrigger(): Long {
        loop@ while (buttonPressCount < COUNT) {
            pressButton()
            processMail()
            if (backToStartingState()) {
                break@loop
            }
        }
        println("buttonPressCount=$buttonPressCount;lowCount=$lowCount;highCount=$highCount")
        val l = lowCount
        val h = highCount
        val remainingCount = COUNT.mod(buttonPressCount)
        val q = COUNT / buttonPressCount
        // reset -------------
        resetCount()
        for (i in 1..remainingCount) {
            pressButton()
            processMail()
        }
        println("buttonPressCount=$buttonPressCount;lowCount=$lowCount;highCount=$highCount")
        val low = q * l + lowCount
        val high = q * h + highCount
        return low * high
    }
}
