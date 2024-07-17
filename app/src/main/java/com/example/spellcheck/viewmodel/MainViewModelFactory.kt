package com.example.spellcheck.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.spellcheck.retrofit.CorrectedText
import com.example.spellcheck.retrofit.CorrectedTextAPI

class MainViewModelFactory(private val corretedTextAPI: CorrectedTextAPI) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(corretedTextAPI) as T
        }
    throw IllegalAccessException("Unknown ViewModel class")
    }
}