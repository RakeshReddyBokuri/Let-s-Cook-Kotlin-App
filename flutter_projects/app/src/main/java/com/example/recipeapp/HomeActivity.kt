package com.example.recipeapp
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.recipeapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdapter: PopularAdapter
    private lateinit var dataList: ArrayList<Recipe>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        binding.editTextText2.setOnClickListener{
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }
        binding.salad.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Salad")
            myIntent.putExtra("CATEGORY","Salad")
            startActivity(myIntent)
        }
        binding.maindish.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Main Dish")
            myIntent.putExtra("CATEGORY","Dish")
            startActivity(myIntent)
        }
        binding.drinks.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Drinks")
            myIntent.putExtra("CATEGORY","Drinks")
            startActivity(myIntent)
        }
        binding.more.setOnClickListener{
            var dialog=Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)

            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)
        }
        binding.desert.setOnClickListener{
            var myIntent=Intent(this@HomeActivity,CategoryActivity::class.java)
            myIntent.putExtra("TITLE","Dessert")
            myIntent.putExtra("CATEGORY","Dessert")
            startActivity(myIntent)
        }
    }

    private fun setUpRecyclerView() {
        dataList = ArrayList()
        binding.rvPopuler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        val daoObject = db.getDao()
        val recipes = daoObject.getAll()  // Ensure this line correctly calls getAll()

        if (recipes != null) {
            for (recipe in recipes) {
                recipe?.let {
                    if (it.category.contains("Popular")) {
                        dataList.add(it)
                    }
                }
            }
        }

        rvAdapter = PopularAdapter(dataList, this)
        binding.rvPopuler.adapter = rvAdapter
    }
}
