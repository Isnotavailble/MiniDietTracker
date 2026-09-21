package com.example.diettrackertrain

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var foodDao: FoodDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        foodDao = AppDatabase.getDataBase(this).foodDao()

        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val navBtn = findViewById<Button>(R.id.navToListBtn)
        val submitBtn = findViewById<Button>(R.id.submitBtn)

        submitBtn.setOnClickListener {
            val foodName = findViewById<EditText>(R.id.foodNameIn)
            val foodCategory = findViewById<EditText>(R.id.foodCategoryIn)
            val foodProteins = findViewById<EditText>(R.id.foodProteinsIn)
            val foodFiber = findViewById<EditText>(R.id.foodFiberIn)
            val foodCalories = findViewById<EditText>(R.id.foodCaloriesIn)

            val name = foodName.text.toString().trim()
            val category = foodCategory.text.toString().trim()
            val proteins = foodProteins.text.toString().trim()
            val fiber = foodFiber.text.toString().trim()
            val calories = foodCalories.text.toString().trim()

            if (name.isEmpty() || category.isEmpty()){
                Toast.makeText(this,"Name or category should not be null", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val p = proteins.toIntOrNull()
            val f = fiber.toIntOrNull()
            val c = calories.toIntOrNull()
            if (p == null || f == null || c == null){
                Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val foodEntity = FoodEntity(name = name, category = category, proteins = p, calories = c , fibers = f)

            lifecycleScope.launch {
                foodDao.addFood(foodEntity)
                foodName.text = null
                foodProteins.text = null
                foodCalories.text = null
                foodFiber.text = null
                foodCategory.text = null
            }
            navBtn.performClick()

        }

        navBtn.setOnClickListener {
            val nextActivity = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(nextActivity)
        }

    }
}