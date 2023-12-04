package com.example.hoysecomev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

class ShowRecipes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_recipes)

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
    }
}