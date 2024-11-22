package com.example.lab05

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val lsCategories = arrayOf("Lacteos", "Limpieza", "Abarrotes")
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lsCategories)
        spCategory.adapter = adapter
    }

    fun registerProduct(view: View){
        val spCategory = findViewById<Spinner>(R.id.spCategory)
        var idCategory = 1
        when(spCategory.selectedItem.toString()){
            "Lacteos" -> idCategory = 1
            "Limpieza" -> idCategory = 2
            "Abarrotes" -> idCategory = 3
        }
        val productName = findViewById<EditText>(R.id.txtName).text.toString()
        val productPrice = findViewById<EditText>(R.id.txtPrice).text.toString().toFloat()
        val productDescription = findViewById<EditText>(R.id.txtDescription).text.toString()
        val stock = 100
        val priority = 1
        val image = ""

        val url = "https://i9acjuyjt5.execute-api.us-east-1.amazonaws.com/v1/producto"
        val jsonObject = JSONObject()
        try {
            jsonObject.put("idcategoria", idCategory)
            jsonObject.put("nombre", productName)
            jsonObject.put("precio", productPrice)
            jsonObject.put("stock", stock)
            jsonObject.put("descripcion", productDescription)
            jsonObject.put("importancia", priority)
            jsonObject.put("imagen", image)
            Log.i("REGISTRO", "Registro Correcto");
        }catch (e: JSONException){
            Log.i("=====>", e.message ?: "Ocurrió un error JSONException")
        }

        val jsonRequest = object : JsonObjectRequest(
            Method.POST, url, jsonObject, Response.Listener { response ->
                Log.i("REGISTRO", "Registro guardado con exito ${jsonObject}")
            },
            Response.ErrorListener { error: VolleyError ->
                Log.i("=====>", error.message ?: "Ocurrió un error")
            }) {}

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonRequest)
    }
}