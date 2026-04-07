package ru.mirea.volkovmm.thread;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import ru.mirea.volkovmm.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Работа с главным потоком
        Thread mainThread = Thread.currentThread();
        binding.textView.setText("Имя текущего потока: " + mainThread.getName());

        mainThread.setName("МОЙ НОМЕР ГРУППЫ: БСБО-50-24, НОМЕР ПО СПИСКУ: 11, МОЙ ЛЮБИМЫЙ ФИЛЬМ: 1+1");
        binding.textView.append("\nНовое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace())); // [cite: 321]


        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        int numberThread = counter++;
                        Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы %s номер по списку № %d", numberThread, "БСБО-50-24", 11)); // [cite: 373, 374]

                        try {
                            float pairs = Float.parseFloat(binding.editTextPairs.getText().toString());
                            float days = Float.parseFloat(binding.editTextDays.getText().toString());
                            float res = pairs / days;


                            long endTime = System.currentTimeMillis() + 20 * 1000; // [cite: 328]
                            while (System.currentTimeMillis() < endTime) { // [cite: 329]
                                synchronized (this) { // [cite: 330]
                                    try { // [cite: 331]
                                        wait(endTime - System.currentTimeMillis()); // [cite: 332]
                                        Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime); // [cite: 380]
                                    } catch (Exception e) { // [cite: 333]
                                        throw new RuntimeException(e); // [cite: 334]
                                    }
                                }
                            }

                            Log.d("ThreadProject", "Выполнен поток № " + numberThread); // [cite: 387]


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    binding.textViewResult.setText(String.format("Среднее количество пар: %.2f", res));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start(); // [cite: 390]
            }
        });
    }
}