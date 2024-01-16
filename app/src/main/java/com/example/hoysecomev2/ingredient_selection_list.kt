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
import com.example.hoysecomev2.databinding.FragmentIngredientSelectionListBinding
import com.example.hoysecomev2.databinding.FragmentRecipeListBinding
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val arg_ingredientArray = ArrayList<String>();

class ingredient_selection_list : Fragment() {
    private var ingredientArray: ArrayList<String>? = ArrayList();

    private var _binding: FragmentIngredientSelectionListBinding?=null;
    private val binding get()=_binding!!;
    private lateinit var ingredientSelectionRvadapter: ingredientes_selection_RVAdapter;
    private lateinit var ingredientList:MutableList<String>;
    private lateinit var imageList:MutableList<String>;
    private lateinit var idList:MutableList<String>;
    private lateinit var ingredientIdArray:MutableList<String>;

    private var instance: ingredient_selection_list? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        instance = this
        arguments?.let {
            ingredientArray = it.getStringArrayList(arg_ingredientArray.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientSelectionListBinding.inflate(layoutInflater);
        ingredientList=mutableListOf();
        imageList= mutableListOf();
        idList= mutableListOf();
        ingredientIdArray= mutableListOf();
        initRecycler();
        findIngredients();
        return binding.root;
    }

    companion object {

        @JvmStatic
        fun newInstance(ingredientArray: ArrayList<String>?) =
            ingredient_selection_list().apply {
                arguments = Bundle().apply {
                    putStringArrayList(arg_ingredientArray.toString(), ingredientArray);
                }
            }
    }

    private fun initRecycler(){
        ingredientSelectionRvadapter = ingredientes_selection_RVAdapter(ingredientList, imageList, idList, ingredientArray)
        binding.selectedIngredientsList.layoutManager = LinearLayoutManager(this.context)
        binding.selectedIngredientsList.adapter = ingredientSelectionRvadapter
    }

    private fun getRetrofit(base_url_resource:Int): Retrofit {
        return Retrofit.Builder()
            .baseUrl(resources.getString(base_url_resource))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun findIngredients(){
        CoroutineScope(Dispatchers.IO).launch {
            ingredientList.clear()
            imageList.clear()
            idList.clear()
            ingredientIdArray.clear()

            for((index,value) in ingredientArray!!.withIndex()){
                if(index%2==1){
                    ingredientIdArray.add(value);
                }
            }

            for(id:String in ingredientIdArray){
                val response = getRetrofit(R.string.api_ingredients).create(apiService::class.java).consultIngredientDetails(id + "/information?apiKey=9a1052eefaea4ef4a60c86e20847c051").enqueue(object:
                    Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("noo", "nimodo")
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        val stringResponse = response.body()?.string()

                        val JSon = JsonParser().parse(stringResponse)

                        val obj = JSon.asJsonObject

                        ingredientList.add(obj.get("name").asString)
                        imageList.add(obj.get("image").asString)
                        idList.add(obj.get("id").asString)

                        ingredientSelectionRvadapter.notifyDataSetChanged()
                    }
                    }
                )
            }
        }
    }
}