package com.example.spellcheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.spellcheck.databinding.ActivityMainBinding
import com.example.spellcheck.retrofit.CorrectedTextAPI
import com.example.spellcheck.viewmodel.MainViewModel
import com.example.spellcheck.viewmodel.MainViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val correctedTextAPI = createCorrectedTextAPI()
        val viewModelFactory = MainViewModelFactory(correctedTextAPI)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.checkBtn.setOnClickListener {
            val inputWord = binding.inputWord.text.toString()
            viewModel.checkText(inputWord)
        }

        viewModel.inputText.observe(this, Observer { word ->
            binding.incorrectWord.text = word
        })

        viewModel.corretedWords.observe(this, Observer { correctedWords ->
            binding.correctWord.text = correctedWords.joinToString(", ")
        })

        viewModel.textErrorOrAccepted.observe(this, Observer { message ->
            binding.correctWord.text = message
        })
    }

    private fun createCorrectedTextAPI(): CorrectedTextAPI {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://speller.yandex.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CorrectedTextAPI::class.java)
    }
}