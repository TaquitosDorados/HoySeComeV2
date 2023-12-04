package com.example.hoysecomev2

import android.app.Application

class global: Application() {

    var ingredientArray = ArrayList<String>()

    fun getIngredientes(): ArrayList<String> {
        return ingredientArray
    }
}