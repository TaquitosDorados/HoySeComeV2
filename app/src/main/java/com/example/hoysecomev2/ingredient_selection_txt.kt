package com.example.hoysecomev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hoysecomev2.databinding.ActivityIngredientSelectionTxtBinding
import com.example.hoysecomev2.databinding.ActivityRecipetxtBinding

class ingredient_selection_txt : AppCompatActivity() {
    lateinit var binding: ActivityIngredientSelectionTxtBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientSelectionTxtBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}