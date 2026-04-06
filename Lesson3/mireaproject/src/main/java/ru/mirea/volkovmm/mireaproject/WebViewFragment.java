package ru.mirea.volkovmm.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WebViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 1. Раздуваем макет (берем визуальный файл)
        View root = inflater.inflate(R.layout.fragment_web_view, container, false);

        // 2. Находим WebView по ID, который мы указали в XML
        WebView webView = root.findViewById(R.id.webView);

        // 3. Настраиваем: включаем JavaScript и говорим открывать ссылки внутри приложения
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        // 4. Загружаем стартовую страницу
        webView.loadUrl("https://www.google.com");

        return root;
    }
}