package com.example.recipeapp

import android.annotation.SuppressLint
import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var rvAdapter: SearchAdapter
    private lateinit var dataList: ArrayList<Recipe>
    private var recipes: List<Recipe?>? = null

    @SuppressLint("ServiceCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ensure the search field is focused
        binding.search.requestFocus()

        // Initialize the database and DAO
        val db = Room.databaseBuilder(
            this@SearchActivity,
            AppDatabase::class.java,
            "db_name"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()
        // Safely retrieve the recipes from the database
        recipes = daoObject.getAll()

        setUpRecyclerView()
        binding.goBackHome.setOnClickListener{
            finish()
        }

        // Add a text change listener to the search input field
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // No action needed before text changes
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    filterData(s.toString())
                }else{
                    setUpRecyclerView()


                }
            }

            override fun afterTextChanged(s: Editable?) {
                // No action needed after text changes
            }
        })


    }

    private fun filterData(filterText: String) {
        val filteredData = ArrayList<Recipe>()
        recipes?.let {
            for (recipe in it) {
                recipe?.let {
                    if (it.tittle.lowercase().contains(filterText.lowercase())) {
                        filteredData.add(it)
                    }
                }
            }
        }
        rvAdapter.filterList(filteredData)
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.rvSearch.layoutManager = LinearLayoutManager(this)

        // Filter recipes based on category and populate the initial dataList
        recipes?.let {
            for (recipe in it) {
                recipe?.let {
                    if (it.category.contains("Popular")) {
                        dataList.add(it)
                    }
                }
            }
        }

        rvAdapter = SearchAdapter(dataList, this)
        binding.rvSearch.adapter = rvAdapter
    }
}
