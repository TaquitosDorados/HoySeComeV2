package com.example.hoysecomev2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hoysecomev2.databinding.ActivityIngredientSelectionTxtBinding
import java.util.concurrent.Executors


class ingredientes_selection_RVAdapter (val ingredientes:List<String>, val imagenes:List<String>, val idIngrediente:List<String>, val ingredientArray: ArrayList<String>?):
    RecyclerView.Adapter<ingredientes_selection_RVAdapter.ViewHolder>() {
    inner class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val binding = ActivityIngredientSelectionTxtBinding.bind(view)
        fun bind(ingrediente: String, imagen: String, id: String){
            binding.txtTexto.text = ingrediente

            val executor = Executors.newSingleThreadExecutor()

            var handler = Handler(Looper.getMainLooper());

            var mIcon11: Bitmap? = null

            binding.btnRemove.setOnClickListener{
                ingredientArray?.removeAt( ingredientArray.indexOf(id) -1)
                ingredientArray?.remove(id)

                Log.d("simbi", ingredientArray.toString())

                binding.btnRemove.isClickable = false;
            }


            executor.execute{
                try {
                    val `in` = java.net.URL("https://spoonacular.com/cdn/ingredients_250x250/" + imagen).openStream()
                    mIcon11 = BitmapFactory.decodeStream(`in`);

                    handler.post(){
                        binding.imgIngrediente.setImageBitmap(mIcon11)
                    }
                } catch (e: Exception) {
                    Log.e("Error", e.message!!)
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_ingredient_selection_txt,parent,false))
    }

    override fun getItemCount(): Int {
        return ingredientes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingredientes[position], imagenes[position], idIngrediente[position])
    }
}
