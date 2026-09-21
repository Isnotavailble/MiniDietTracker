package com.example.diettrackertrain

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class MainActivity2 : AppCompatActivity() {
    private lateinit var foodListContainer : LinearLayout
    private lateinit var foodEntityList : List<FoodEntity>
    private lateinit var foodDao: FoodDao
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        foodDao = AppDatabase.getDataBase(this).foodDao()
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        foodListContainer = findViewById<LinearLayout>(R.id.foodListContainer)
        val addFoodBtn = findViewById<Button>(R.id.addFoodBtn)
        val searchView = findViewById<EditText>(R.id.categorySearchBar)

        loadList("")

        addFoodBtn.setOnClickListener {
            finish()
        }

        searchView.doAfterTextChanged {
            val category = searchView.text.toString().trim()
            loadList(category)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun loadList(category : String){
        lifecycleScope.launch {

            foodListContainer.removeAllViews()

            foodEntityList = if (category.isEmpty()) foodDao.findAll() else foodDao.findByCategory(category.trim())
            val debounce = category.isEmpty()
            //delay((if (debounce) 600 else  400).milliseconds)

            if (foodEntityList.isEmpty()){
                //Toast.makeText(this@MainActivity2,"There is no Food to show",Toast.LENGTH_SHORT).show()
            }
            else {
                for (food in foodEntityList) {
                    val foodCardView = layoutInflater.inflate(
                        R.layout.food_item_card_layout,
                        foodListContainer,
                        false
                    )
                    val foodName = foodCardView.findViewById<TextView>(R.id.foodName)
                    val foodCalories = foodCardView.findViewById<TextView>(R.id.foodCalories)
                    val foodFiber = foodCardView.findViewById<TextView>(R.id.foodFiber)
                    val foodProteins = foodCardView.findViewById<TextView>(R.id.foodProteins)

                    foodName.text = "${food.name.trim()} (${food.category.trim()})"
                    foodProteins.text = "Proteins : ${food.proteins}"
                    foodFiber.text = "Fibers : ${food.fibers}"
                    foodCalories.text = "Calories : ${food.calories}"
                    foodListContainer.addView(foodCardView)
                }
            }

        }
    }

}