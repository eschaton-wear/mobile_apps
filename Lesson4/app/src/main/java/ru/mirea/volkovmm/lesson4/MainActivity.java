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


        binding.editTextMirea.setText("Мой номер по списку №11");
    }
}