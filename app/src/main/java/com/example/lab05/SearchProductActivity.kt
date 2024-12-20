package com.example.lab05

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class SearchProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun searchProduct(view: View){
        val txtFilter = (findViewById<View>(R.id.txtFilter) as EditText).text.toString()
        val url = "https://i9acjuyjt5.execute-api.us-east-1.amazonaws.com/v1/productos?criterio=$txtFilter"
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null, {
                response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    Log.i("=====>", jsonArray.toString())
                    val items = mutableListOf<String>()
                    val productIds = mutableListOf<String>()
                    for (i in 0 until jsonArray.length()){
                        val product = jsonArray.getJSONObject(i)
                        items.add("${product.getString("categoria")} - ${product.getString("nombre")}")
                        productIds.add(product.getString("id"))
                    }
                    val lvProduct = findViewById<ListView>(R.id.lvProduct)
                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
                    lvProduct.adapter = adapter

                    lvProduct.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
                        val selectedProductId = productIds[position]

                        val intent = Intent(this, DetailProductActivity::class.java)
                        intent.putExtra("PRODUCT_ID", selectedProductId)
                        startActivity(intent)
                    }

                } catch (e: JSONException){
                    Log.i("=====>", "Error: ")
                    Log.i("=====>", e.message.toString())
                }
            }, {
                error ->
                    Log.i("=====>", error.toString())
            }
        )
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(stringRequest)
    }
}