package com.example.hoysecomev2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.hoysecomev2.databinding.ActivitySelectedIngredientsBinding

class SelectedIngredients : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_recipes)

        val Intent = intent

        val ingredientArray = Intent.getStringArrayListExtra("IngredientArray")
        val ingredientIdArray = Intent.getStringArrayListExtra("IngredientIdArray")

        val list = ingredient_selection_list.newInstance(ingredientArray, ingredientIdArray)

        var frg: Fragment? = supportFragmentManager.findFragmentByTag("ingredientSelectionList")
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (frg != null) {
            ft.replace(frg.id,list,"ingredientSelectionList").commit()
        } else {
            Log.d("hola", "hola")
        }
    }
}