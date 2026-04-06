# Отчёт по практической работе №3
**Дисциплина:** Интеллектуальные мобильные приложения 
**Студент:** Волков М.М.  
**Группа:** БСБО-50-24

## Цель работы
Глубокое изучение принципов межкомпонентного взаимодействия в операционной системе Android. Получение практических навыков работы с явными и неявными намерениями (Intents), передачей параметров между активностями, современными механизмами возврата результатов (`ActivityResultLauncher`), а также освоение жизненного цикла фрагментов и построения сложной навигации (Navigation Drawer).

---

## 1. Проект SystemIntentsApp (Неявные намерения)
**Описание:** Приложение создано для демонстрации работы неявных намерений (Implicit Intents), когда система сама подбирает приложение, способное обработать запрос пользователя.

**Ход работы:**
1. В файле разметки `activity_main.xml` был создан графический интерфейс с использованием `LinearLayout`, содержащий три кнопки: «Открыть браузер», «Открыть карту» и «Позвонить».
2. В классе `MainActivity.java` были реализованы обработчики нажатий (onClick) для каждой из кнопок.
3. Для открытия веб-страницы был сформирован интент с действием `Intent.ACTION_VIEW` и передан URI адрес сайта.
4. Для открытия карт использовался тот же `ACTION_VIEW`, но в URI передавались географические координаты (`geo:55.749479,37.613944`).
5. Для вызова звонилки был применен `Intent.ACTION_DIAL` с URI формата `tel:`.

**Пример реализации вызова браузера:**
```java
public void onClickOpenWeb(View view) {
    Intent intent = new Intent(Intent.ACTION_VIEW);
    intent.setData(Uri.parse("[https://mirea.ru](https://mirea.ru)"));
    startActivity(intent);
}
```
**Итог:** Успешно протестирован вызов стандартных системных приложений Android напрямую из разрабатываемого приложения.

---

## 2. Проект IntentApp (Явные намерения и передача данных)
**Описание:** Разработка приложения для передачи данных между двумя конкретными активностями (Explicit Intents) внутри одного проекта.

**Ход работы:**
1. Проект состоит из двух экранов: `MainActivity` и `SecondActivity`.
2. В `MainActivity` реализован алгоритм получения точного системного времени в миллисекундах с помощью `System.currentTimeMillis()`.
3. Полученное время было отформатировано в удобочитаемую строку с использованием класса `SimpleDateFormat` (формат `"yyyy-MM-dd HH:mm:ss"`).
4. Данная строка была "упакована" в интент с помощью метода `intent.putExtra("date", dateString)`.
5. В `SecondActivity` был реализован прием данных через `getIntent().getStringExtra("date")` и вывод полученного значения в текстовое поле `TextView`.

**Фрагмент кода отправки данных:**
```java
long dateInMillis = System.currentTimeMillis();
String format = "yyyy-MM-dd HH:mm:ss";
final SimpleDateFormat sdf = new SimpleDateFormat(format);
String dateString = sdf.format(new Date(dateInMillis));

Intent intent = new Intent(this, SecondActivity.class);
intent.putExtra("date", dateString);
startActivity(intent);
```
**Итог:** Изучен механизм передачи примитивных типов данных и строк между компонентами приложения через объект `Intent`.

---

## 3. Проект SimpleFragmentApp (Работа с фрагментами)
**Описание:** Изучение фрагментов (Fragments) как способа создания модульного и гибкого пользовательского интерфейса.

**Ход работы:**
1. В главной разметке `activity_main.xml` были добавлены две кнопки для переключения и контейнер `FragmentContainerView` (или `FrameLayout`), выступающий "якорем" для отображения фрагментов.
2. Были созданы два отдельных класса: `FirstFragment` и `SecondFragment`, наследуемые от `androidx.fragment.app.Fragment`. Для каждого из них был создан свой уникальный XML-макет.
3. В `MainActivity.java` была реализована логика динамической подмены фрагментов в контейнере при нажатии на кнопки. Для этого использовался класс `FragmentManager` и метод `beginTransaction()`.

**Пример транзакции фрагмента:**
```java
public void onClickFragment1(View view) {
    Fragment fragment1 = new FirstFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction()
        .replace(R.id.fragmentContainer, fragment1)
        .commit();
}
```
**Итог:** Освоено динамическое управление UI без необходимости создания тяжеловесных Activities для каждого экрана.

---

## 4. Проект FavoriteBook (Двусторонний обмен данными)
**Описание:** Демонстрация работы современного API `ActivityResultLauncher` для запуска активности с ожиданием возврата результата (взамен устаревшего метода `startActivityForResult`).

**Ход работы:**
1. Созданы две активности: `MainActivity` (главный экран) и `ShareActivity` (экран ввода).
2. В `ShareActivity` добавлено поле `EditText`, куда пользователь вводит название любимой книги, и кнопка "Отправить". При нажатии формируется новый Интент, в него кладется текст, и вызывается `setResult(Activity.RESULT_OK, intent)`, после чего активность закрывается методом `finish()`.
3. В `MainActivity` был зарегистрирован `ActivityResultLauncher`. Он отслеживает момент закрытия `ShareActivity`, извлекает полученный интент, достает оттуда строковое значение и обновляет `TextView` на главном экране.

**Регистрация обработчика результата в MainActivity:**
```java
ActivityResultLauncher<Intent> activityResultLauncher;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // ...
    activityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String bookName = data.getStringExtra("MESSAGE");
                    textView.setText("Моя любимая книга: " + bookName);
                }
            }
        }
    );
}
```

---

## 5. Контрольное задание: MireaProject
**Описание:** Создание полноценного многомодульного приложения с использованием компонента Navigation Drawer. Настройка ручной сборки проекта, разрешение конфликтов конфигурации Gradle и работа со встроенным компонентом `WebView`.

**Ход работы и преодоление технических сложностей:**
1. **Настройка окружения:** Проект был создан с использованием современного каталога версий (Version Catalogs). Для корректной работы Navigation Architecture Component потребовалась ручная корректировка файла `build.gradle.kts` модуля, замена ссылок на прямые зависимости и синхронизация (Clean/Rebuild Project).
2. **Создание структуры навигации:** Вручную были сформированы директории `res/navigation` и `res/menu`. Созданы файлы графа навигации `mobile_navigation.xml` и меню шторки `activity_main_drawer.xml`.
3. **Настройка MainActivity:** Был реализован макет с использованием `DrawerLayout`, `NavigationView` и `Toolbar`. В Java-классе инициализирован `NavController`, который связал боковое меню с графом навигации для автоматического переключения фрагментов.
4. **Реализация DataFragment (Дизайн одежды):** Разработан интерфейс, презентующий индустрию Streetwear. Разметка включает в себя информацию о симбиозе графического дизайна, создании полноценного брендинга и организации профессиональных фотосессий для лукбуков.
5. **Реализация WebViewFragment:** Добавлен фрагмент со встроенным веб-браузером.
    * В `AndroidManifest.xml` выдано разрешение `<uses-permission android:name="android.permission.INTERNET" />`.
    * Для корректного открытия ссылок внутри приложения, а не во внешнем браузере устройства, были применены настройки `setJavaScriptEnabled(true)` и переопределен `WebViewClient`.

**Инициализация контроллера навигации (MainActivity.java):**
```java
DrawerLayout drawer = findViewById(R.id.drawer_layout);
NavigationView navigationView = findViewById(R.id.nav_view);

mAppBarConfiguration = new AppBarConfiguration.Builder(
        R.id.nav_data, R.id.nav_webview)
        .setOpenableLayout(drawer)
        .build();

NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
NavigationUI.setupWithNavController(navigationView, navController);
```

## Вывод
В результате выполнения практической работы №3 был пройден полный цикл изучения межкомпонентного взаимодействия в Android. Реализовано 5 самостоятельных проектов, каждый из которых демонстрирует отдельный механизм системы: от простых интентов до современной архитектуры навигации через графы и фрагменты. Контрольное задание `MireaProject` успешно завершено: решены конфликты сборки Gradle, настроено боковое меню и интегрированы требуемые компоненты. Все требования к практической работе выполнены в полном объеме.
## 



