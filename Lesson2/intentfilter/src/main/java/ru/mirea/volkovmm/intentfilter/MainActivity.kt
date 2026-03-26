package ru.mirea.volkovmm.intentfilter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnOpenBrowser = findViewById<Button>(R.id.btnOpenBrowser)
        val btnShare = findViewById<Button>(R.id.btnShare)


        btnOpenBrowser.setOnClickListener {
            val address = Uri.parse("https://www.mirea.ru/")
            val openLinkIntent = Intent(Intent.ACTION_VIEW, address)
            startActivity(openLinkIntent)
        }


        btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "MIREA")
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Волков М.М.")
            startActivity(Intent.createChooser(shareIntent, "МОИ ФИО"))
        }
    }
}