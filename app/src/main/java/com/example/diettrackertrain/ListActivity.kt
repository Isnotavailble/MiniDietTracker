package com.example.diettrackertrain

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.util.Objects
import java.util.zip.Inflater

class ListActivity : AppCompatActivity() {
    private lateinit var foodContainer : LinearLayout
    private lateinit var foodDao: FoodDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        foodDao = AppDatabase.getDataBase(this).foodDao()
        setContentView(R.layout.list_view_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        foodContainer = findViewById<LinearLayout>(R.id.foodContainer)
        val addBtn = findViewById<Button>(R.id.addBtn)
        val searchBar = findViewById<EditText>(R.id.searchBar)



        addBtn.setOnClickListener {
            finish()
        }
        searchBar.doAfterTextChanged{
            val input = searchBar.text.toString().trim()

            if (input.isEmpty()){
                loadList("")
                return@doAfterTextChanged
            }

            loadList(input)
        }
        loadList("")



    }
    fun loadList(category: String) {
        lifecycleScope.launch {
            val foodEntityList : List<FoodEntity> = if (category.isEmpty()) foodDao.findAllFood()
                                                    else foodDao.findByCategory(category)
            foodContainer.removeAllViews()


            for ( f in foodEntityList){
                val foodCard = layoutInflater.inflate(R.layout.food_card_layout,foodContainer,false)
                val name = foodCard.findViewById<TextView>(R.id.name)
                val category = foodCard.findViewById<TextView>(R.id.category)
                val calories = foodCard.findViewById<TextView>(R.id.calories)
                val fibres = foodCard.findViewById<TextView>(R.id.fibres)
                name.text = f.name
                category.text = f.category
                calories.text = "${f.calories} calories"
                fibres.text = "${f.fibres} fibres"
                foodContainer.addView(foodCard)

                }


        }





    }

}