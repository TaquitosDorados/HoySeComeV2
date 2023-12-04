package com.example.hoysecomev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hoysecomev2.databinding.ActivityRecipetxtBinding

class recipetxt : AppCompatActivity() {
    lateinit var binding: ActivityRecipetxtBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipetxtBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}