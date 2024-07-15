package com.example.spellcheck.retrofit

data class CorrectedText(
    val code: Int,
    val pos: Int,
    val row: Int,
    val col: Int,
    val len: Int,
    val word: String,
    val s: List<String>
)
