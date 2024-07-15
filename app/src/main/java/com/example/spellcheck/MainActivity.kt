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
            CoroutineScope(Dispatchers.IO).launch {
                val correctText = CorrectedTextAPI.getCorrectedTextByWord()
                runOnUiThread {
                    binding.incorrectWord.text = binding.inputWord.text.toString()
                    binding.correctWord.text = correctText.s[0]
                }
            }
        }

    }
}