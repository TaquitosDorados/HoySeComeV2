package com.example.hoysecomev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hoysecomev2.databinding.ActivitySelectedIngredientsBinding

class SelectedIngredients : AppCompatActivity() {
    lateinit var binding: ActivitySelectedIngredientsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedIngredientsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}