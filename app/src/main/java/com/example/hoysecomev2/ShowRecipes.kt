package com.example.hoysecomev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.hoysecomev2.databinding.ActivityShowRecipesBinding

class ShowRecipes : AppCompatActivity() {
    lateinit var binding: ActivityShowRecipesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Intent = intent

        val ingredientArray = Intent.getStringArrayListExtra("IngredientArray")

        val list = recipe_list.newInstance(ingredientArray)

        var frg: Fragment? = supportFragmentManager.findFragmentByTag("recipeList")
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (frg != null) {
            ft.replace(frg.id,list,"recipeList").commit()
        } else {
            Log.d("hola", "hola")
        }

        binding.btnToHome.setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }
}