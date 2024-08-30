package com.example.recipeapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.CategoryRvBinding

class CategoryAdapter(var dataList: ArrayList<Recipe>, var context: Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: CategoryRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CategoryRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList[position]

        // Load the image using Glide
        Glide.with(context).load(recipe.img).into(holder.binding.img)

        // Set the title
        holder.binding.tittle.text = recipe.tittle

        // Split the 'ing' string by new lines and safely get the first line as time
        val temp = recipe.ing.split("\n").dropWhile { it.isEmpty() }
        holder.binding.time.text = if (temp.isNotEmpty()) temp[0] else "N/A"  // Handle cases where the time might be missing
        holder.binding.next.setOnClickListener{
            var intent= Intent(context,RecipeActivity::class.java)
            intent.putExtra("img",recipe.img)
            intent.putExtra("title",recipe.tittle)
            intent.putExtra("des",recipe.des)
            intent.putExtra("ing",recipe.ing)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

    }
}
