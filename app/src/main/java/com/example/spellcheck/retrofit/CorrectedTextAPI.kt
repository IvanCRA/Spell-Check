package com.example.spellcheck.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface CorrectedTextAPI {
    /*@GET("/services/spellservice.json/checkText?text={word}")
    suspend fun getCorrectedTextByWord(@Path("word") word: String) : CorrectedText*/
    @GET("/services/spellservice.json/checkTexts?text=синхрафазатрон")
    suspend fun getCorrectedTextByWord() : CorrectedText

}