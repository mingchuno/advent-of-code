package com.github.mingchuno.aoc.utils

import java.util.*

class PriorityMap<K, V>(comparator: Comparator<Pair<K, V>>) {
    private val map: MutableMap<K, V> = mutableMapOf()
    private val q: PriorityQueue<Pair<K, V>> = PriorityQueue(comparator)

    fun add(k: K, v: V): V? {
        q.add(k to v)
        return map.put(k, v)
    }

    fun isNotEmpty(): Boolean = map.isNotEmpty()

    fun poll(): Pair<K, V> {
        val pair = q.poll()
        val (k, _) = pair
        map.remove(k)
        return pair
    }

    fun get(k: K): V? = map[k]
}
