package ru.mirea.volkovmm.dialogapp

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnShowDialog = findViewById<Button>(R.id.btnShowDialog)

        // Показываем диалог при нажатии
        btnShowDialog.setOnClickListener {
            val dialogFragment = MyDialogFragment()
            dialogFragment.show(supportFragmentManager, "mirea")
        }
    }

    // Вот эти методы искал диалог:
    fun onOkClicked() {
        Toast.makeText(applicationContext, "Вы выбрали кнопку \"Иду дальше\"!", Toast.LENGTH_LONG).show()
    }

    fun onCancelClicked() {
        Toast.makeText(applicationContext, "Вы выбрали кнопку \"Нет\"!", Toast.LENGTH_LONG).show()
    }

    fun onNeutralClicked() {
        Toast.makeText(applicationContext, "Вы выбрали кнопку \"На паузе\"!", Toast.LENGTH_LONG).show()
    }
}