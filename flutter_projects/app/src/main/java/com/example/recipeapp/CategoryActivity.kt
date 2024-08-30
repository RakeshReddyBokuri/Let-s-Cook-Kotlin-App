package com.example.recipeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private  val binding by lazy{
        ActivityCategoryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title=intent.getStringExtra("TITLE")
        setUpRecyclerView()
    }
    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.rvCategory.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val db = Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()
        val recipes = daoObject.getAll()  // Ensure this line correctly calls getAll()
        binding.itemImage.setOnClickListener{
            finish()
        }

        if (recipes != null) {
            for (recipe in recipes) {
                recipe?.let {
                    if (it.category.contains(intent.getStringExtra("CATEGORY")!!)) {
                        dataList.add(it)
                    }
                }
            }
        }

        rvAdapter = CategoryAdapter(dataList, this)
        binding.rvCategory.adapter = rvAdapter
    }
}