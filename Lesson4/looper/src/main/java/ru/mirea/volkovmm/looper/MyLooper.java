package ru.mirea.volkovmm.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.concurrent.TimeUnit;

public class MyLooper extends Thread {
    public Handler mHandler;
    private Handler mainHandler;

    public MyLooper(Handler mainThreadHandler) {
        mainHandler = mainThreadHandler;
    }

    public void run() {
        Log.d("MyLooper", "run");
        Looper.prepare();

        mHandler = new Handler(Looper.myLooper()) {
            public void handleMessage(Message msg) {

                int age = msg.getData().getInt("AGE");
                String profession = msg.getData().getString("PROFESSION");

                Log.d("MyLooper", "Сообщение получено. Ждем " + age + " сек.");

                try {

                    TimeUnit.SECONDS.sleep(age);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", "Ваша профессия: " + profession + ". Обработка заняла: " + age + " сек.");
                message.setData(bundle);


                mainHandler.sendMessage(message);
            }
        };
        Looper.loop();
    }
}