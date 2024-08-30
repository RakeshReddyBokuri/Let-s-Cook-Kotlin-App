package com.example.recipeapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.databinding.SearchRvBinding

class SearchAdapter(var dataList: ArrayList<Recipe>?, var context: Context) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: SearchRvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SearchRvBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        // Handle null dataList
        return dataList?.size ?: 0
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterList: ArrayList<Recipe>?) {
        // Handle null filterList
        dataList = filterList ?: arrayListOf()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList?.getOrNull(position)

        recipe?.let {
            // Safely load image and set text
            Glide.with(context)
                .load(it.img)
                .apply(RequestOptions().placeholder(R.drawable.card_recipe)) // optional: add a placeholder
                .into(holder.binding.searchImg)

            holder.binding.searchTxt.text = it.tittle ?: "Untitled"
            holder.itemView.setOnClickListener{
                var intent=Intent(context,RecipeActivity::class.java)
                intent.putExtra("img",recipe.img)
                intent.putExtra("title",recipe.tittle)
                intent.putExtra("des",recipe.des)
                intent.putExtra("ing",recipe.ing)
                intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        } ?: run {
            // Handle null recipe or other data accordingly
            holder.binding.searchImg.setImageResource(R.drawable.card_recipe) // placeholder image
            holder.binding.searchTxt.text = "Untitled"

        }
    }
}
