package ru.mirea.volkovmm.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    private EditText editTextUserBook;
    private EditText editTextUserQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        TextView devBookText = findViewById(R.id.textViewDevBook);
        editTextUserBook = findViewById(R.id.editTextUserBook);
        editTextUserQuote = findViewById(R.id.editTextUserQuote);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String bookName = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quoteName = extras.getString(MainActivity.QUOTES_KEY);
            devBookText.setText(String.format("Любимая книга разработчика: %s\nЦитата из книги: %s", bookName, quoteName));
        }
    }

    public void sendUserData(View view) {
        String userBook = editTextUserBook.getText().toString();
        String userQuote = editTextUserQuote.getText().toString();

        String textToSend = "Название Вашей любимой книги: " + userBook + "\nЦитата: " + userQuote;

        Intent data = new Intent();
        data.putExtra(MainActivity.USER_MESSAGE, textToSend);

        setResult(Activity.RESULT_OK, data);

        finish();
    }
}