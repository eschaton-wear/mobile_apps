package ru.mirea.volkovmm.notificationapp

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Уникальный ID канала (обязательно для Android 8+)
    private val CHANNEL_ID = "com.mirea.asd.notification.ANDROID"
    private val PermissionCode = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSendNotification = findViewById<Button>(R.id.btnSendNotification)

        // Запрашиваем разрешение у пользователя при запуске приложения (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "Разрешения получены")
            } else {
                Log.d("MainActivity", "Нет разрешений!")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), PermissionCode)
            }
        }

        // По клику отправляем пуш
        btnSendNotification.setOnClickListener {
            sendNotification()
        }
    }

    private fun sendNotification() {
        // Дополнительная проверка разрешения перед отправкой
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return
        }

        // 1. Создаем канал уведомлений (Требование системы для Android 8.0 и выше)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, "Student FIO Notification", importance)
            channel.description = "MIREA Channel"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // 2. Настраиваем внешний вид уведомления
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentText("красавчик, держи увеломление")
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Используем встроенную иконку Android, чтобы не было ошибок
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Much longer text that cannot fit one line..."))
            .setContentTitle("Mirea")

        // 3. Отправляем уведомление
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1, builder.build())
    }
}