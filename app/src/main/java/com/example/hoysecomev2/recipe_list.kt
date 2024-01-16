package com.example.hoysecomev2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoysecomev2.Interfaces.apiService
import com.example.hoysecomev2.databinding.FragmentRecipeListBinding
import com.google.gson.JsonParser
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val arg_ingredientArray = ArrayList<String>();
class recipe_list : Fragment() {
    private var ingredientArray: ArrayList<String>? = ArrayList();

    private var _binding: FragmentRecipeListBinding?=null;
    private val binding get()=_binding!!;
    private lateinit var recipeRvadapter: Recipes_RVAdapter;
    private lateinit var recipeList:MutableList<String>;
    private lateinit var imageList:MutableList<String>;
    private lateinit var idList:MutableList<String>;

    private var instance: recipe_list? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this;
        arguments?.let {
            ingredientArray = it.getStringArrayList(arg_ingredientArray.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeListBinding.inflate(layoutInflater);
        recipeList=mutableListOf();
        imageList= mutableListOf();
        idList= mutableListOf()
        initRecycler();
        insertIngredients();
        return binding.root;
    }

    companion object {

        @JvmStatic
        fun newInstance(ingredientArray: ArrayList<String>?) =
            recipe_list().apply {
                arguments = Bundle().apply {
                    putStringArrayList(arg_ingredientArray.toString(), ingredientArray);
                }
            }
    }

    private fun initRecycler(){
        recipeRvadapter = Recipes_RVAdapter(recipeList, imageList, idList)
        binding.recipesList.layoutManager = LinearLayoutManager(this.context)
        binding.recipesList.adapter = recipeRvadapter
    }

    private fun getRetrofit(base_url_resource:Int): Retrofit {
        return Retrofit.Builder()
            .baseUrl(resources.getString(base_url_resource))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun insertIngredients(){
        val trueIngredientArray:ArrayList<String> = ArrayList()

        for((index,value) in ingredientArray!!.withIndex()){
            if(index%2==0){
                trueIngredientArray.add(value);
            }
        }

        val yes = getRetrofit(R.string.api_recipes).create(apiService::class.java).consultRecipes("findByIngredients?apiKey=9a1052eefaea4ef4a60c86e20847c051&ingredients=" + ingredientArray.toString()).enqueue(object:
            Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                //handle error here
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                recipeList.clear()
                imageList.clear()
                idList.clear()
                //your raw string response
                val stringResponse = response.body()?.string()

                val JSon = JsonParser().parse(stringResponse)

                val ArrayJ = JSon.asJsonArray

                for (key in ArrayJ){
                    val art:String = key.asJsonObject.get("title").asString
                    recipeList.add(art)
                    val img:String = key.asJsonObject.get("image").asString
                    imageList.add(img);
                    val id:String = key.asJsonObject.get("id").asString
                    idList.add(id)
                }

                recipeRvadapter.notifyDataSetChanged()
            }

        })
    }
}