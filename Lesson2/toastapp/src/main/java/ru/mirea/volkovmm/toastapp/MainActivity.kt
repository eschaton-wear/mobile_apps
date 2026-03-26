package ru.mirea.volkovmm.toastapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editText)
        val btnCount = findViewById<Button>(R.id.btnCount)

        btnCount.setOnClickListener {
            val textLength = editText.text.length

            val resultMessage = "СТУДЕНТ № 11 ГРУППА БСБО-50-24 Количество символов - $textLength"

            Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show()
        }
    }
}