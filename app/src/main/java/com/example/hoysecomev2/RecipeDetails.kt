package com.example.hoysecomev2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.hoysecomev2.Interfaces.apiService
import com.example.hoysecomev2.databinding.ActivityMainBinding
import com.example.hoysecomev2.databinding.ActivityRecipeDetailsBinding
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import kotlin.math.log

class RecipeDetails : AppCompatActivity() {
    lateinit var binding: ActivityRecipeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val Intent = intent

        val RecetaID = Intent.getStringExtra("ID")

        Log.d("ID", RecetaID.toString())

        val yes = getRetrofit(R.string.api_recipes).create(apiService::class.java).consultRecipeDetails("" + RecetaID + "/information?apiKey=9a1052eefaea4ef4a60c86e20847c051").enqueue(object:
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //handle error here
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                //your raw string response
                val stringResponse = response.body()?.string()

                val JSon = JsonParser().parse(stringResponse)

                val obj = JSon.asJsonObject

                //titulo
                binding.txtTitle.text = obj.get("title").asString

                //imagen
                val executor = Executors.newSingleThreadExecutor()
                var handler = Handler(Looper.getMainLooper());
                var mIcon11: Bitmap? = null
                executor.execute{
                    try {
                        val `in` = java.net.URL("" + obj.get("image").asString).openStream()
                        mIcon11 = BitmapFactory.decodeStream(`in`);

                        handler.post(){
                            binding.imgReceta.setImageBitmap(mIcon11)
                        }
                    } catch (e: Exception) {
                        Log.e("Error", e.message!!)
                        e.printStackTrace()
                    }
                }

                //Tiempo
                binding.txtTime.text = obj.get("readyInMinutes").asString + " min"
                Log.d("minutes", obj.get("readyInMinutes").asString)

                //Instrucciones
                //binding.txtIntrucciones.text = "INSTRUCTIONS\n " + obj.get("instructions").asString

                binding.txtIntrucciones.text = "INSTRUCTIONS\n "
                val instrucciones = obj.get("analyzedInstructions").asJsonArray
                for(llave in instrucciones){
                    val steps = llave.asJsonObject.get("steps").asJsonArray
                    for(key in steps){
                        val currentString:String = binding.txtIntrucciones.text.toString()
                        binding.txtIntrucciones.text = currentString + key.asJsonObject.get("number").asString + ".- " + key.asJsonObject.get("step").asString + "\n"
                    }
                }

                //Ingredientes
                val ingredientes = obj.get("extendedIngredients").asJsonArray
                binding.txtIngredientes.text = "INGREDIENTS\n"

                if(ingredientes != null) {

                    for (key in ingredientes) {
                        val currentString: String = binding.txtIngredientes.text.toString()

                        var name:String = try{
                            key.asJsonObject.get("nameClean").asString
                        } catch (e: Exception) {
                            key.asJsonObject.get("name").asString
                        }


                        binding.txtIngredientes.text =
                            currentString + key.asJsonObject.get("amount").asString + " " + key.asJsonObject.get("unit").asString + " " + name + "\n"
                    }
                }
            }

        })
    }

    private fun getRetrofit(base_url_resource:Int): Retrofit {
        return Retrofit.Builder()
            .baseUrl(resources.getString(base_url_resource))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}