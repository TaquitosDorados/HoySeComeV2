package com.example.hoysecomev2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoysecomev2.Interfaces.apiService
import com.example.hoysecomev2.databinding.FragmentIngredientListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val arg_name = "name";
private val arg_ingredientArray = ArrayList<String>();
//private val arg_ingredientIdArray = ArrayList<String>();
class ingredient_list : Fragment() {
    private var name: String? = "apple";
    private var ingredientArray: ArrayList<String>? = ArrayList();
    //private var ingredientIdArray: ArrayList<String>? = ArrayList();

    private var _binding: FragmentIngredientListBinding?=null;
    private val binding get()=_binding!!;
    private lateinit var ingredientesRvadapter: Ingredientes_RVAdapter;
    private lateinit var ingredientesList:MutableList<String>;
    private lateinit var imageList:MutableList<String>;
    private lateinit var idList:MutableList<String>;

    private var instance: ingredient_list? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this;
        arguments?.let {
            name = it.getString(arg_name)
            //ingredientIdArray = it.getStringArrayList(arg_ingredientIdArray.toString())
            ingredientArray = it.getStringArrayList(arg_ingredientArray.toString())

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentIngredientListBinding.inflate(layoutInflater);
        ingredientesList=mutableListOf();
        imageList= mutableListOf();
        idList= mutableListOf();
        initRecycler();
        insertIngredients();
        return binding.root;
    }

    companion object {

        @JvmStatic
        fun newInstance(name: String, ingredientArray: ArrayList<String>, /*ingredientIdArray: ArrayList<String>*/) =
            ingredient_list().apply {
                arguments = Bundle().apply {
                    putString(arg_name, name);
                    putStringArrayList(arg_ingredientArray.toString(), ingredientArray);
                    //putStringArrayList(arg_ingredientIdArray.toString(), ingredientIdArray);
                    Log.d("ArrayContentsnewIns", "IngredientsArray: $ingredientArray")
                }
            }
    }

    private fun initRecycler(){
        ingredientesRvadapter = Ingredientes_RVAdapter(ingredientesList, imageList, idList, ingredientArray)
        Log.d("ArrayContentsinit", "IngredientsArray: $ingredientArray")
        binding.ingredientesList.layoutManager = LinearLayoutManager(this.context)
        binding.ingredientesList.adapter = ingredientesRvadapter
    }

    private fun getRetrofit(base_url_resource:Int): Retrofit {
        return Retrofit.Builder()
            .baseUrl(resources.getString(base_url_resource))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    fun insertIngredients(){
        Log.d("ArrayContentsInsert", "IngredientsArray: $ingredientArray")
        CoroutineScope(Dispatchers.IO).launch {
            val response = getRetrofit(R.string.api_ingredients).create(apiService::class.java).consultIngredient("search?apiKey=9a1052eefaea4ef4a60c86e20847c051&query=" + name)
            val ingredients = response.body()
            activity?.runOnUiThread{
                if(response.isSuccessful){
                    if(ingredients?.results?.size() != 0){
                        ingredientesList.clear()
                        for (key in ingredients!!.results!!.asJsonArray){
                            val art:String = key.asJsonObject.get("name").asString
                            ingredientesList.add(art)
                            val img:String = key.asJsonObject.get("image").asString
                            imageList.add(img);
                            val id:String = key.asJsonObject.get("id").asString
                            idList.add(id)
                        }
                    }else{
                        ingredientesList.clear()
                        imageList.clear()
                        idList.clear()
                    }
                    ingredientesRvadapter.notifyDataSetChanged()

                }else{
                    ingredientesList.clear()
                    imageList.clear()
                    idList.clear()
                    ingredientesRvadapter.notifyDataSetChanged()
                }
            }
        }
    }
}