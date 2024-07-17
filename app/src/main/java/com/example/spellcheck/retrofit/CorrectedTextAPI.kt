package com.example.spellcheck.retrofit

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CorrectedTextAPI {
    @GET("/services/spellservice.json/checkText")
    suspend fun getCorrectedTextByWord(@Query("text") word: String) : List<CorrectedText>

}