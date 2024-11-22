package com.example.lab05

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSearch = findViewById<Button>(R.id.btnSearch);
        btnSearch.setOnClickListener {
            val intent = Intent(this, SearchProductActivity::class.java)
            startActivity(intent)
        }

        val btnGet = findViewById<Button>(R.id.btnGet);
        btnGet.setOnClickListener {
            val intent = Intent(this, GetProductActivity::class.java)
            startActivity(intent)
        }

        val btnRegister = findViewById<Button>(R.id.btnRegister);
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}