package com.example.spellcheck.retrofit

import retrofit2.http.GET

interface CorrectedTextAPI {
    @GET("/services/spellservice.json/checkText?text=дибил")
    fun getCorrectedText() : CorrectedText

}