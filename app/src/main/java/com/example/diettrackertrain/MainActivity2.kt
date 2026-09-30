package com.example.diettrackertrain

import android.os.Bundle
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

class MainActivity2 : AppCompatActivity() {
    private lateinit var foodDao: FoodDao
    private lateinit var foodListContainer : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        foodDao = AppDatabase.getDataBase(this).foodDao()
        setContentView(R.layout.activity_main2)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addFoodBtn = findViewById<Button>(R.id.addFoodBtn)
        val searchBar = findViewById<EditText>(R.id.searchBar)
        addFoodBtn.setOnClickListener {
            finish()
        }
        searchBar.doAfterTextChanged {
            val input = searchBar.text.toString().trim()
            if (input.isEmpty()){
                loadList("")
                return@doAfterTextChanged
            }
            loadList(input)

        }

        foodListContainer = findViewById<LinearLayout>(R.id.foodContainer)
        loadList("")

    }
    fun loadList(category: String){
        lifecycleScope.launch {
            foodListContainer.removeAllViews()
            val foodEntityList = if (category.isEmpty()) foodDao.findAll() else foodDao.findByCategory(category)
            for (f in foodEntityList){
                val foodCard = layoutInflater.inflate(R.layout.food_card_layout,foodListContainer,false)
                val name = foodCard.findViewById<TextView>(R.id.name)
                val category = foodCard.findViewById<TextView>(R.id.category)
                val calories = foodCard.findViewById<TextView>(R.id.calories)
                val fibres = foodCard.findViewById<TextView>(R.id.fibres)

                name.text = f.name
                category.text = f.category
                calories.text = "${f.calories} calories"
                fibres.text = "${f.fibres} fibres"
                foodListContainer.addView(foodCard)

            }
        }
    }
}