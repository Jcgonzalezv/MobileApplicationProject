package com.example.isis3520_202220_team25_kotlin.data

import android.graphics.Bitmap
import androidx.collection.LruCache

class MyCache private constructor() {

    private object HOLDER {
        val INSTANCE = MyCache()
    }

    companion object {
        val instance: MyCache by lazy { HOLDER.INSTANCE }
    }
    val lru: LruCache<Any, Any>

    init {

        lru = LruCache(1024)

    }

    fun saveBitmapToCache(key: String, bitmap: Bitmap) {

        try {
            MyCache.instance.lru.put(key, bitmap)
        } catch (e: Exception) {
        }

    }

    fun retrieveBitmapFromCache(key: String): Bitmap? {

        try {
            return MyCache.instance.lru.get(key) as Bitmap?
        } catch (e: Exception) {
        }

        return null
    }

    fun saveStringToCache(key: String, bitmap: String) {

        try {
            MyCache.instance.lru.put(key, bitmap)
        } catch (e: Exception) {
        }

    }

    fun retrieveStringFromCache(key: String): String? {

        try {
            return MyCache.instance.lru.get(key) as String?
        } catch (e: Exception) {
        }

        return null
    }

}