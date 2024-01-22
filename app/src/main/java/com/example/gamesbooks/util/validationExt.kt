package com.example.gamesbooks.util


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun isNullOrEmpty(value: String?): Boolean =
    value == null || value.trim() == "" || value.isEmpty() || value.equals(
        "null",
        ignoreCase = true
    )

inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)