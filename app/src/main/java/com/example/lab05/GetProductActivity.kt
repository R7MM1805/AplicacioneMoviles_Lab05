package com.example.lab05

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class GetProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        searchProduct()
    }

    fun searchProduct(){
        val url = "https://e2aca7ccd8.execute-api.us-east-1.amazonaws.com/v1/productos"
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null, {
                    response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    Log.i("=====>", jsonArray.toString())
                    val items = mutableListOf<String>("Seleccione")
                    val productIds = mutableListOf<String>()
                    for (i in 0 until jsonArray.length()){
                        val product = jsonArray.getJSONObject(i)
                        items.add(product.getString("nombre"))
                        productIds.add(product.getString("id"))
                    }
                    val spProduct = findViewById<Spinner>(R.id.spProducts)
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spProduct.adapter = adapter

                    spProduct.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                            if (position == 0) return

                            val selectedProductId = productIds[position - 1]

                            val intent = Intent(this@GetProductActivity, DetailProductActivity::class.java)
                            intent.putExtra("PRODUCT_ID", selectedProductId)
                            startActivity(intent)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
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