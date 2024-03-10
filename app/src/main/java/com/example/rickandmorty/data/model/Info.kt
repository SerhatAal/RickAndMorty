package com.example.rickandmorty.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)
