package ru.mirea.volkovmm.multiactivity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val textViewResult = findViewById<TextView>(R.id.textViewResult)

        val text = intent.getStringExtra("key")

        if (text != null) {
            textViewResult.text = text
        }
    }
}