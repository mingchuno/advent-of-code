package com.github.mingchuno.aoc.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

/** map over collection in parallel using different thread */
suspend fun <T, V> Iterable<T>.pmap(fn: (t: T) -> V): List<V> = coroutineScope {
    map { async(Dispatchers.Default) { fn(it) } }.awaitAll()
}

/** flatMap over collection in parallel using different thread */
suspend fun <T, V> Iterable<T>.pflatMap(fn: (t: T) -> Collection<V>): List<V> = coroutineScope {
    map { async(Dispatchers.Default) { fn(it) } }.awaitAll().flatten()
}
