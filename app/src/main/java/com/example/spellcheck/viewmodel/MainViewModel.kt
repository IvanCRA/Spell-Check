package com.example.spellcheck.viewmodel

import androidx.lifecycle.ViewModel
import com.example.spellcheck.retrofit.CorrectedTextAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel : ViewModel() {
    var inputText: String = ""
    val textErrorOrAccepted: String = "Возможно слово правильное или такого слова не существует"

    init {

    }
    fun getConnectUrl() = Retrofit
        .Builder()
        .baseUrl("https://speller.yandex.net")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun createRetrofit(retrofit: Retrofit) = retrofit.create(CorrectedTextAPI::class.java)


}