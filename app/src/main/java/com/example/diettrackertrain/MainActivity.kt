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

        var nameIn = findViewById<EditText>(R.id.f_name)
        var categoryIn = findViewById<EditText>(R.id.f_category)
        var caloriesIn = findViewById<EditText>(R.id.f_calories)
        var fibresIn = findViewById<EditText>(R.id.f_fibres)

        submitBtn.setOnClickListener {
            val name = nameIn.text.toString().trim()
            val category = categoryIn.text.toString().trim()
            val calories = caloriesIn.text.toString().trim().toIntOrNull()
            val fibres = fibresIn.text.toString().trim().toIntOrNull()

            if ( calories == null || fibres == null || name.isEmpty() || category.isEmpty()){
                Toast.makeText(this@MainActivity,"Please make sure all the fields are entered",
                    Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val foodEntity = FoodEntity(name = name, calories = calories, fibres = fibres, category = category)

            lifecycleScope.launch {
                foodDao.addFood(foodEntity)

                nameIn.text = null
                categoryIn.text = null
                fibresIn.text = null
                caloriesIn.text = null
                Toast.makeText(this@MainActivity,"Food added",
                    Toast.LENGTH_SHORT)
                    .show()
                navBtn.performClick()


            }

        }

        navBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ListActivity::class.java)
            startActivity(intent)
        }

    }
}