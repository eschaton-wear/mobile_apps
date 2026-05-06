package ru.mirea.volkovmm.dialogapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Кнопка из примера в методичке
        val btnShowDialog = findViewById<Button>(R.id.btnShowDialog)
        btnShowDialog.setOnClickListener {
            val dialogFragment = MyDialogFragment()
            dialogFragment.show(supportFragmentManager, "mirea")
        }

        // В самостоятельной работе нужно добавить еще 3 кнопки в layout
        // Если ты прописал android:onClick в XML, методы ниже сработают автоматически
    }

    // Методы для обработки нажатий в базовом MyDialogFragment:
    fun onOkClicked() {
        Toast.makeText(applicationContext, "Вы выбрали кнопку \"Иду дальше\"!", Toast.LENGTH_LONG).show()
    }

    fun onCancelClicked() {
        Toast.makeText(applicationContext, "Вы выбрали кнопку \"Нет\"!", Toast.LENGTH_LONG).show()
    }

    fun onNeutralClicked() {
        Toast.makeText(applicationContext, "Вы выбрали кнопку \"На паузе\"!", Toast.LENGTH_LONG).show()
    }

    // --- МЕТОДЫ ДЛЯ САМОСТОЯТЕЛЬНОЙ РАБОТЫ (Раздел 4.4) ---[cite: 1]

    // Вызов диалога выбора времени[cite: 1]
    fun onClickTimeDialog(view: View) {
        val timeDialog = MyTimeDialogFragment()
        timeDialog.show(supportFragmentManager, "time_picker")

        // Пример использования Snackbar[cite: 1]
        Snackbar.make(view, "Открываем выбор времени", Snackbar.LENGTH_SHORT).show()
    }

    // Вызов диалога выбора даты[cite: 1]
    fun onClickDateDialog(view: View) {
        val dateDialog = MyDateDialogFragment()
        dateDialog.show(supportFragmentManager, "date_picker")
    }

    // Вызов диалога прогресса[cite: 1]
    fun onClickProgressDialog(view: View) {
        val progressDialog = MyProgressDialogFragment()
        progressDialog.show(supportFragmentManager, "progress")
    }
}