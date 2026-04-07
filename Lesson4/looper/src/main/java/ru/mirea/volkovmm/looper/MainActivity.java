package ru.mirea.volkovmm.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.volkovmm.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("result");
                Log.d(MainActivity.class.getSimpleName(), "Результат из MyLooper: " + result);


                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        };


        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();


        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ageStr = binding.editTextAge.getText().toString();
                String profStr = binding.editTextProfession.getText().toString();

                if (ageStr.isEmpty() || profStr.isEmpty()) return;

                int age = Integer.parseInt(ageStr);


                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("AGE", age);
                bundle.putString("PROFESSION", profStr);
                msg.setData(bundle);


                if (myLooper != null && myLooper.mHandler != null) {
                    myLooper.mHandler.sendMessage(msg);
                    Toast.makeText(MainActivity.this, "Задача отправлена в фон!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}