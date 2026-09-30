package com.example.diettrackertrain

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Objects
import java.util.zip.Inflater

class ListActivity : AppCompatActivity() {
    private lateinit var foodContainer : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.list_view_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        foodContainer = findViewById<LinearLayout>(R.id.foodContainer)
        val addBtn = findViewById<Button>(R.id.addBtn)
        addBtn.setOnClickListener {
            finish()
        }
        loadList()



    }
    fun loadList() {
        val food1 = layoutInflater.inflate(R.layout.food_card_layout,foodContainer,false)
        val food2 = layoutInflater.inflate(R.layout.food_card_layout,foodContainer,false)
        val food3 = layoutInflater.inflate(R.layout.food_card_layout,foodContainer,false)
        val temp = listOf(food1, food2, food3)
        for (t in temp){
            foodContainer.addView(t)
        }



    }

}