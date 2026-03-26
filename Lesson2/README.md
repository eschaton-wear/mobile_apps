# Отчет по практическому заданию № 2 
**Выполнил:** Волков М.М., группа БСБО-50-24

***

## 1. ActivityLifecycle — жизненный цикл Activity

### 1.1. Назначение Activity
Activity — это экран приложения, с которым взаимодействует пользователь: форма входа, список заметок, экран настроек и т.п. Каждая Activity проходит фиксированную последовательность состояний — от создания до уничтожения, и система Android сама вызывает соответствующие методы.

### 1.2. Основные методы жизненного цикла

* `onCreate(Bundle?)` — вызывается один раз при создании Activity. Здесь выполняется инициализация: установка макета, поиск View, подписка на слушатели кнопок.
* `onStart()` — Activity становится видимой пользователю.
* `onResume()` — Activity находится на переднем плане, имеет фокус ввода и готова к взаимодействию.
* `onPause()` — вызывается, когда Activity частично скрывается (например, поверх открыт диалог).
* `onStop()` — Activity полностью скрыта с экрана (приложение свернуто).
* `onRestart()` — вызывается перед `onStart()`, когда Activity возвращается на экран после остановки.
* `onDestroy()` — Activity уничтожается системой или завершается вручную.

### 1.3. Практическая реализация
В ходе выполнения модуля **ActivityLifecycle** мной были выполнены следующие действия:
1. Создан модуль, в котором в классе `MainActivity` переопределены все методы жизненного цикла.
2. В каждый метод добавлен вывод в лог через `Log.d` с уникальным тегом `LifecycleLog`.
3. С помощью инструмента **Logcat** проанализирована последовательность вызовов при запуске, сворачивании и повороте экрана (что приводит к пересозданию Activity).

**Листинг кода (MainActivity.kt):**
```kotlin
package ru.mirea.volkovmm.activitylifecycle
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
class MainActivity : AppCompatActivity() { 
    private val TAG = "LifecycleLog"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate invoked")
    }
    override fun onStart() { super.onStart(); Log.d(TAG, "onStart invoked") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume invoked") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause invoked") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop invoked") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy invoked") }
    override fun onRestart() { super.onRestart(); Log.d(TAG, "onRestart invoked") } 
}
```

## 2. MultiActivity — явные намерения и передача данных

### 2.1. Понятие явного намерения (Explicit Intent)
Intent — это объект сообщения, описывающий действие. **Явный** Intent прямо указывает класс Activity назначения. Система сразу запускает нужный экран внутри нашего приложения без поиска сторонних обработчиков.

### 2.2. Практическая реализация
1. Реализован интерфейс с полем ввода `EditText` и кнопкой отправки.
2. Создана вторая активность `SecondActivity` для отображения принятых данных.
3. Реализована передача строки через `putExtra`.

**Листинг кода отправки (MainActivity.kt):**
```kotlin
val btnSend = findViewById<Button>(R.id.buttonSend)
val editText = findViewById<EditText>(R.id.editTextData)

btnSend.setOnClickListener {
    val intent = Intent(this, SecondActivity::class.java)
    intent.putExtra("key", "MIREA ${editText.text}")
    startActivity(intent)
}
```

**Листинг кода приема (SecondActivity.kt):**
```kotlin
val textViewResult = findViewById<TextView>(R.id.textViewResult)
val text = intent.getStringExtra("key")
textViewResult.text = text ?: "Данные не получены"
```

***

## 3. IntentFilter — неявные намерения

### 3.1. Неявные намерения (Implicit Intent)
Неявный Intent не указывает конкретный класс, а определяет действие (например, `ACTION_VIEW`). Система предлагает пользователю список приложений, способных обработать этот запрос.

### 3.2. Практическая реализация
1. Реализована кнопка для открытия внешнего веб-ресурса (сайта МИРЭА).
2. Реализована кнопка "Поделиться" для отправки ФИО студента в сторонние приложения.

**Листинг кода (MainActivity.kt):**
```kotlin
// Открытие браузера
val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("[https://www.mirea.ru/](https://www.mirea.ru/)"))
startActivity(browserIntent)

// Функция "Поделиться"
val sendIntent = Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, "Волков М.М.")
}
startActivity(Intent.createChooser(sendIntent, "Поделиться через:"))
```

***

## 4. ToastApp и NotificationApp — уведомления

### 4.1. Toast и Push-уведомления
Toast — это кратковременное сообщение поверх интерфейса. Системные уведомления (Notifications) отображаются в шторке и требуют создания специального канала (Channel).

### 4.2. Практическая реализация
1. В модуле **ToastApp** реализован подсчет символов введенного текста с выводом результата в Toast.
2. В модуле **NotificationApp** реализовано создание канала уведомлений для Android 8.0+ и запрос разрешений для Android 13+.

**Листинг кода (Notification):**
```kotlin
val builder = NotificationCompat.Builder(this, CHANNEL_ID)
    .setSmallIcon(android.R.drawable.ic_dialog_info)
    .setContentTitle("Практика 2")
    .setContentText("Уведомление от Волкова М.М.")
    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

NotificationManagerCompat.from(this).notify(1, builder.build())
```

***

## 5. Dialog — диалоговые окна и пикеры

### 5.1. Назначение диалогов
Диалоговые окна используются для подтверждения действий или выбора специфических данных (дата/время). Они перекрывают текущий экран и требуют реакции пользователя.

### 5.2. Практическая реализация
1. Реализован `AlertDialog` с тремя кнопками выбора.
2. Внедрены `DatePickerDialog` и `TimePickerDialog` для выбора даты и времени.

**Листинг кода пикеров (MainActivity.kt):**
```kotlin
// Выбор даты
DatePickerDialog(this, { _, year, month, day ->
    Toast.makeText(this, "Выбрано: $day.${month + 1}.$year", Toast.LENGTH_SHORT).show()
}, 2026, 2, 25).show()

// Выбор времени
TimePickerDialog(this, { _, hour, minute ->
    Toast.makeText(this, "Выбрано: $hour:$minute", Toast.LENGTH_SHORT).show()
}, 12, 0, true).show()
```

***

## Заключение
В ходе работы были освоены основные механизмы навигации и оповещения в Android. Все модули реализованы на языке **Kotlin** и протестированы на физическом устройстве. Проект демонстрирует навыки работы с жизненным циклом Activity, интентами и системными диалогами.
```