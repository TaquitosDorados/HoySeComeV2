package com.example.hoysecomev2

import android.os.Bundle
import android.util.Log
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.hoysecomev2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var IngredientsArray = ArrayList<String>()
        var IngredientsIDArray = ArrayList<String>();

        binding.btnSearch.setOnClickListener{
                val searchName: String = binding.txbFindIngredient.text.toString()

                val list = ingredient_list.newInstance(searchName,IngredientsArray,IngredientsIDArray)

                var frg: Fragment? = supportFragmentManager.findFragmentByTag("ingredientList")
                val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                if (frg != null) {
                    ft.replace(frg.id,list,"ingredientList").commit()
                } else {
                    Log.d("hola", "hola")
                }
            Log.d("AYUDA", IngredientsArray.toString())
        }

        binding.btnNext.setOnClickListener{
            val intent = Intent(applicationContext, SelectedIngredients::class.java)
            intent.putExtra("IngredientArray", IngredientsArray)
            intent.putExtra("IngredientIdArray", IngredientsIDArray)
            startActivity(intent)
        }
    }


}