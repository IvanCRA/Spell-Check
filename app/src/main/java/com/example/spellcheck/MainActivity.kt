package com.example.spellcheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.spellcheck.databinding.ActivityMainBinding
import com.example.spellcheck.retrofit.CorrectedTextAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://speller.yandex.net")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val CorrectedTextAPI = retrofit.create(CorrectedTextAPI::class.java)

        binding.checkBtn.setOnClickListener {
            val inputText = binding.inputWord.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val correctText = CorrectedTextAPI.getCorrectedTextByWord(inputText)
                    runOnUiThread {
                        if (correctText.isNotEmpty()) {
                            binding.incorrectWord.text = inputText
                            binding.correctWord.text = correctText[0].s.joinToString(", ")
                        } else {
                            binding.incorrectWord.text = inputText
                            binding.correctWord.text = "Возможно слово правильное или такого слова не существует"
                        }
                    }
                } catch (e: Exception) {
                    runOnUiThread {
                        binding.correctWord.text = "Ошибка ${e.message}"
                    }
                }
            }
        }

    }
}