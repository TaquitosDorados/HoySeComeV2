package com.example.hoysecomev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.hoysecomev2.databinding.ActivityIngredientetxtBinding

class ingredientetxt : AppCompatActivity() {
    lateinit var binding: ActivityIngredientetxtBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientetxtBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}