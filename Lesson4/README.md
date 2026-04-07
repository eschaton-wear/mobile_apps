# Отчет по практической работе №4
**Тема:** Интеллектуальные мобильные приложения. Асинхронная работа в ОС Android.
**Студент:** Волков М.М.
**Группа:** БСБО-50-24

---

## 1. Введение и ViewBinding (модуль `app`)
В ходе работы был изучен механизм `ViewBinding`, который позволяет безопасно связывать XML-разметку с Java-кодом, исключая использование устаревшего метода `findViewById`. В модуле реализована форма ввода, где при нажатии на кнопку текст из `EditText` переносится в `TextView` с логированием в Logcat.

### Фрагмент кода `MainActivity.java`:
```java
package ru.mirea.volkovmm.lesson4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.volkovmm.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = binding.editTextMirea.getText().toString();
                binding.textViewMirea.setText("Вы ввели: " + input);
                Log.d(MainActivity.class.getSimpleName(), "Кнопка нажата!");
            }
        });
    }
}
```


## 2. Работа с базовыми потоками (модуль `thread`)
Был создан новый модуль `thread`. Главной задачей было перенести тяжелые вычисления (подсчет среднего количества пар с искусственной задержкой) в фоновый поток `Thread`, чтобы не блокировать UI-поток приложения (Main Thread).

При нажатии на кнопку запускается фоновый `Runnable`, который усыпляет поток на **20 секунд** (согласно методике), после чего результат возвращается на экран через `runOnUiThread`.

### Фрагмент кода:
```java
binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        new Thread(new Runnable() {
            public void run() {
                int numberThread = counter++;
                Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы %s номер по списку № %d", numberThread, "БСБО-50-24", 11));

                try {
                    float pairs = Float.parseFloat(binding.editTextPairs.getText().toString());
                    float days = Float.parseFloat(binding.editTextDays.getText().toString());
                    float res = pairs / days;

                    // Имитация сложных вычислений (20 секунд)
                    long endTime = System.currentTimeMillis() + 20 * 1000;
                    while (System.currentTimeMillis() < endTime) {
                        synchronized (this) {
                            try { wait(endTime - System.currentTimeMillis()); } catch (Exception e) {}
                        }
                    }
                    Log.d("ThreadProject", "Выполнен поток № " + numberThread);

                    runOnUiThread(() -> {
                        binding.textViewResult.setText(String.format("Среднее количество пар: %.2f", res));
                    });
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }
});
```

## 3. Передача данных в UI (модуль `data_thread`)
В модуле `data_thread` изучены методы вызова UI-потока из фонового: `runOnUiThread`, `post` и `postDelayed`. Текст в `TextView` последовательно обновляется из фонового потока с заданными интервалами.

```java
Thread t = new Thread(() -> {
    try {
        TimeUnit.SECONDS.sleep(2);
        runOnUiThread(runn1);
        TimeUnit.SECONDS.sleep(1);
        binding.tvInfo.postDelayed(runn3, 2000);
        binding.tvInfo.post(runn2);
    } catch (InterruptedException e) { e.printStackTrace(); }
});
t.start();
```

---

## 4. Очереди сообщений (модуль `looper`)
Реализован механизм передачи данных между потоками с использованием `Looper` и `Handler`. Фоновый класс `MyLooper` принимает сообщение с возрастом и профессией, делает паузу, равную возрасту (в сек.), и возвращает результат.

### Код `handleMessage` в `MyLooper`:
```java
public void handleMessage(Message msg) {
    int age = msg.getData().getInt("AGE");
    String profession = msg.getData().getString("PROFESSION");
    try {
        TimeUnit.SECONDS.sleep(age);
    } catch (InterruptedException e) { e.printStackTrace(); }

    Message message = new Message();
    Bundle bundle = new Bundle();
    bundle.putString("result", "Ваша профессия: " + profession + ". Обработка заняла: " + age + " сек.");
    message.setData(bundle);
    mainHandler.sendMessage(message);
}
```

## 5. Асинхронные загрузчики (модуль `cryptoloader`)
Реализован `AsyncTaskLoader` для фоновой дешифровки. Текст **"Секретный код дизайнера Zarbaft"** шифруется алгоритмом AES в главном потоке и передается в Loader, где расшифровывается.

## 6. Фоновые сервисы (модуль `serviceapp`)
Создан `Foreground Service` для воспроизведения музыки. Реализовано обязательное уведомление в шторке. Согласно методике, в уведомлении указано уникальное название композиции: **"Zarbaft Lookbook OST - Lamassu Theme"**.

### Код настройки уведомления:
```java
NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentText("Playing...")
        .setSmallIcon(R.mipmap.ic_launcher)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Zarbaft Lookbook OST - Lamassu Theme"))
        .setContentTitle("Music Player");
startForeground(1, builder.build());
```

## 7. Современный планировщик задач (модуль `workmanager`)
Использован `WorkManager` для гарантированного выполнения фоновой задачи `UploadWorker`. Установлены ограничения (`Constraints`): работа только по Wi-Fi и при подключенной зарядке.

### Код настройки задачи:
```java
Constraints constraints = new Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresCharging(true)
        .build();

WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
        .setConstraints(constraints)
        .build();

WorkManager.getInstance(this).enqueue(uploadWorkRequest);
```

![img.png](../../Users/%D0%9F%D0%BE%D0%BB%D1%8C%D0%B7%D0%BE%D0%B2%D0%B0%D1%82%D0%B5%D0%BB%D1%8C/Desktop/img.png)
---

## Вывод
В ходе работы освоены механизмы многопоточности в ОС Android: от базовых потоков до фоновых сервисов и `WorkManager`. Получены навыки выноса тяжелых задач за пределы UI-потока и асинхронного взаимодействия компонентов приложения.
