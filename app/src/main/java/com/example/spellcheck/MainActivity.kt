package com.example.spellcheck

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.spellcheck.databinding.ActivityMainBinding
import com.example.spellcheck.retrofit.CorrectedTextAPI
import com.example.spellcheck.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(view)

        val retrofit = viewModel.getConnectUrl()
        val CorrectedTextAPI = retrofit.create(CorrectedTextAPI::class.java)

        binding.checkBtn.setOnClickListener {
            viewModel.inputText = binding.inputWord.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val correctText = CorrectedTextAPI.getCorrectedTextByWord(viewModel.inputText)
                    runOnUiThread {
                        if (correctText.isNotEmpty()) {
                            binding.incorrectWord.text = viewModel.inputText
                            binding.correctWord.text = correctText[0].s.joinToString(", ")
                        } else {
                            binding.incorrectWord.text = viewModel.inputText
                            binding.correctWord.text = viewModel.textErrorOrAccepted
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