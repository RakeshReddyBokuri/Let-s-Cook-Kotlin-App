package com.example.recipeapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.PopularRvItemBinding

class PopularAdapter(
    private val dataList: ArrayList<Recipe>,
    private val context: Context
) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PopularRvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList.getOrNull(position)

        // Safely access the properties of recipe
        recipe?.let { nonNullRecipe ->
            Glide.with(context).load(nonNullRecipe.img).into(holder.binding.popularImg)
            holder.binding.pupularText.text = nonNullRecipe.tittle
            var time=dataList.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val firstIngredient = nonNullRecipe.ing.lines().firstOrNull().orEmpty()
            holder.binding.popularTime.text = firstIngredient
            holder.itemView.setOnClickListener {
                val intent = Intent(context, RecipeActivity::class.java).apply {
                    putExtra("img", nonNullRecipe.img)
                    putExtra("title", nonNullRecipe.tittle)
                    putExtra("des", nonNullRecipe.des)
                    putExtra("ing", nonNullRecipe.ing)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.startActivity(intent)
            }
        }
    }
}
