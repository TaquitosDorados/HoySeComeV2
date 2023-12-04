package com.example.hoysecomev2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.hoysecomev2.databinding.ActivityRecipetxtBinding
import java.util.concurrent.Executors

class Recipes_RVAdapter(val recipes:List<String>, val imagenes:List<String>, val id:List<String>): RecyclerView.Adapter<Recipes_RVAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = ActivityRecipetxtBinding.bind(view)

        fun bind(receta: String, imagen: String, id:String){
            binding.txtTexto.text = receta

            val executor = Executors.newSingleThreadExecutor()

            var handler = Handler(Looper.getMainLooper());

            var mIcon11: Bitmap? = null

            executor.execute{
                try {
                    val `in` = java.net.URL("" + imagen).openStream()
                    mIcon11 = BitmapFactory.decodeStream(`in`);

                    handler.post(){
                        binding.imgIngrediente.setImageBitmap(mIcon11)
                    }
                } catch (e: Exception) {
                    Log.e("Error", e.message!!)
                    e.printStackTrace()
                }
            }

            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context,RecipeDetails::class.java).apply {
                    putExtra("ID", id)
                }
                it.context.startActivity(intent)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Recipes_RVAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_recipetxt,parent,false))
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: Recipes_RVAdapter.ViewHolder, position: Int) {
        holder.bind(recipes[position], imagenes[position], id[position])
    }
}