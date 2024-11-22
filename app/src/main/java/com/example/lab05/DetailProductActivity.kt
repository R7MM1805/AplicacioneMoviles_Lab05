package com.example.lab05

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class DetailProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val productId = intent.getStringExtra("PRODUCT_ID")
        Log.i("ProductDetailActivity", "ID del producto recibido: $productId")
        getProductByID(productId.toString())

    }

    fun getProductByID(productId: String){
        val url = "https://i9acjuyjt5.execute-api.us-east-1.amazonaws.com/v1/producto?id_producto=$productId"
        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null, {
                    response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    Log.i("=====>", jsonArray.toString())

                    if (jsonArray.length() > 0) {
                        val product = jsonArray.getJSONObject(0)
                        val txtProductID = findViewById<TextView>(R.id.txtProductID)
                        val txtProductCategory = findViewById<TextView>(R.id.txtProductCategory)
                        val txtProductName = findViewById<TextView>(R.id.txtProductName)
                        val txtProductDescription = findViewById<TextView>(R.id.txtProductDescription)
                        val txtProductPrice = findViewById<TextView>(R.id.txtProductPrice)
                        val txtProductStock = findViewById<TextView>(R.id.txtProductStock)

                        txtProductID.text = "ID: ${product.getString("id")}"
                        txtProductCategory.text = "Categoria: ${product.getString("categoria")}"
                        txtProductName.text = "Nombre: ${product.getString("nombre")}"
                        txtProductDescription.text = "Descripción: ${product.getString("descripcion")}"
                        txtProductPrice.text = "Precio: ${product.getString("precio")}"
                        txtProductStock.text = "Stock: ${product.getString("stock")}"
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