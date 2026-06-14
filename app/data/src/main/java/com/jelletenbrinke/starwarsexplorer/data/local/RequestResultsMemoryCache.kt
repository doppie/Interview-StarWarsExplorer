package com.jelletenbrinke.starwarsexplorer.data.local

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * A simple reusable utility to store a request results in memory.
 */
class RequestResultsMemoryCache<V> {
    private val cache = mutableMapOf<String, V>()

    // helps safe concurrent access to the mutable cache map.
    private val mutex = Mutex()

    suspend fun get(key: String): V? = mutex.withLock {
        cache[key]
    }

    suspend fun put(key: String, value: V) = mutex.withLock {
        cache[key] = value
    }
}