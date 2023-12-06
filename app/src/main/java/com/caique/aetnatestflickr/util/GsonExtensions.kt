package com.caique.aetnatestflickr.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Reader

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this)

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

inline fun <reified T> T.toJson(): String = Gson().toJson(this)

inline fun <reified T> Reader.fromJson(): T = Gson().fromJson(this)

inline fun <reified T> Gson.fromJson(reader: Reader): T =
    fromJson<T>(reader, object : TypeToken<T>() {}.type)
