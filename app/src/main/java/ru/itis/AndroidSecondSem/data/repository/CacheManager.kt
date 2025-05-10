package ru.itis.AndroidSecondSem.data.repository

class CacheManager {

    private data class CacheItem(
        val timestamp: Long,
        val result: Double,
        var requestCount: Int = 0
    )

    private val cache = mutableMapOf<String, CacheItem>()
    private val cooldownMillis = 5 * 60 * 1000

    fun getCachedResult(key: String): Double? {
        val item = cache[key] ?: return null

        if (System.currentTimeMillis() - item.timestamp > cooldownMillis) {
            return null
        }

        if (item.requestCount >= 3) {
            return null
        }

        item.requestCount++
        return item.result
    }

    fun saveToCache(key: String, result: Double) {
        cache.forEach { (k, v) ->
            if (k != key) {
                v.requestCount++
            }
        }
        cache[key] = CacheItem(System.currentTimeMillis(), result)
    }
}