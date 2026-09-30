package com.example.diettrackertrain

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val navBtn = findViewById<Button>(R.id.navBtn)
        val submitBtn = findViewById<Button>(R.id.submitBtn)
        val iName = findViewById<EditText>(R.id.i_name)
        val iCategory = findViewById<EditText>(R.id.i_category)
        val iCalories = findViewById<EditText>(R.id.i_calories)
        val iFibres = findViewById<EditText>(R.id.i_fibres)

        submitBtn.setOnClickListener {
            val name = iName.text.toString().trim()
            val category = iCategory.text.toString().trim()
            val calories = iCalories.text.toString().trim().toIntOrNull()
            val fibres = iFibres.text.toString().trim().toIntOrNull()

            if ( name.isEmpty() || category.isEmpty() || calories == null || fibres == null) {
                Toast.makeText(this@MainActivity,"Cannot create with null values",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val foodEntity = FoodEntity(name = name, category = category, calories = calories, fibres = fibres)
            lifecycleScope.launch {
                foodDao.addFood(foodEntity)
                iName.text = null
                iCalories.text = null
                iCategory.text = null
                iFibres.text = null
            }
            Toast.makeText(this@MainActivity,"Food added",Toast.LENGTH_SHORT).show()
            navBtn.performClick()
        }
        navBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)
        }

    }
}