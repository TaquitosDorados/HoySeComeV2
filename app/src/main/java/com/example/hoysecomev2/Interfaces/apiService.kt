package com.example.hoysecomev2.Interfaces

import com.example.hoysecomev2.responses.ingredientsResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface apiService {
    @GET
    suspend fun consultIngredient(@Url url:String):Response<ingredientsResponse>
    @GET
    fun consultRecipes(@Url url:String): Call<ResponseBody>
    @GET
    fun consultRecipeDetails(@Url url:String): Call<ResponseBody>
    @GET
    fun consultIngredientDetails(@Url url:String):Call<ResponseBody>
}