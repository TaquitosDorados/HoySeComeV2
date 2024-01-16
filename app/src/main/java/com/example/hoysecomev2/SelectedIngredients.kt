package com.example.hoysecomev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.hoysecomev2.databinding.ActivityMainBinding
import com.example.hoysecomev2.databinding.ActivitySelectedIngredientsBinding

class SelectedIngredients : AppCompatActivity() {
    lateinit var binding: ActivitySelectedIngredientsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectedIngredientsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Intent = intent

        val ingredientArray = Intent.getStringArrayListExtra("IngredientArray")

        val list = ingredient_selection_list.newInstance(ingredientArray)

        var frg: Fragment? = supportFragmentManager.findFragmentByTag("ingredientSelectionList")
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (frg != null) {
            ft.replace(frg.id,list,"ingredientSelectionList").commit()
        } else {
            Log.d("hola", "hola")
        }

        binding.btnBack.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.putExtra("IngredientArray", ingredientArray)
            startActivity(intent)
        }

        binding.btnToRecipes.setOnClickListener{
            val intent = Intent(applicationContext, ShowRecipes::class.java)
            intent.putExtra("IngredientArray", ingredientArray)
            startActivity(intent)
        }
    }
}