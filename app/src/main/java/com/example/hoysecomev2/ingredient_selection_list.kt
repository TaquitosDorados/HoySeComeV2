package com.example.hoysecomev2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hoysecomev2.databinding.FragmentRecipeListBinding

private val arg_ingredientArray = ArrayList<String>();
private val arg_ingredientIdArray = ArrayList<String>();

class ingredient_selection_list : Fragment() {
    private var ingredientArray: ArrayList<String>? = ArrayList();

    private var _binding: FragmentIngredientSelectionListBinding?=null;
    private val binding get()=_binding!!;
    private lateinit var ingredientSelectionRvadapter: Ingredient_Selection_RVAdapter;
    private lateinit var ingredientList:MutableList<String>;
    private lateinit var imageList:MutableList<String>;

    private var instance: ingredient_selection_list? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredient_selection_list, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ingredient_selection_list().apply {
                arguments = Bundle().apply {

                }
            }
    }
}