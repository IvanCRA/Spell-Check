package com.example.spellcheck.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spellcheck.retrofit.CorrectedTextAPI
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel(private val corretedTextAPI: CorrectedTextAPI) : ViewModel() {
    private val _inputText = MutableLiveData<String>()
    val inputText: LiveData<String>
        get() = _inputText

    private val _textErrorOrAccepted = MutableLiveData<String>()
    val textErrorOrAccepted: LiveData<String>
        get() = _textErrorOrAccepted

    private val _correctedWords = MutableLiveData<List<String>>()
    val corretedWords: LiveData<List<String>>
        get() = _correctedWords

    fun checkText(word: String) {
        _inputText.value = word
        viewModelScope.launch {
            try {
                val corretedTexts = corretedTextAPI.getCorrectedTextByWord(word)
                if (corretedTexts.isNotEmpty()) {
                    _correctedWords.value = corretedTexts[0].s
                } else {
                    _textErrorOrAccepted.value = "Возможно слово правильное или такого слова не существует"
                }
            } catch (e: Exception) {
                _textErrorOrAccepted.value = "Ошибка: ${e.message}"
            }
        }
    }

}