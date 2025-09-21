package com.example.comicshop

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.example.comicshop.Comic
import com.example.comicshop.ComicAdapter
import com.example.comicshop.ComicViewModel
import kotlinx.coroutines.flow.firstOrNull

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ComicAdapter
    private lateinit var viewModel: ComicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ComicViewModel(application)

        // Заполним тестовыми данными (если пусто)
        lifecycleScope.launch {
            val firstList = viewModel.allComics.firstOrNull()
            if (firstList?.isEmpty() == true) {
                viewModel.insert(Comic(title = "Бэтмен: Убийственная шутка", author = "Алан Мур", price = 999.99, description = "«Чтобы превратить самого здравого человека на свете в полного психа, нужен\nвсего один плохой день», — утверждает Джокер. Он снова сбежал из лечебницы\nАркхем — лишь для того, чтобы доказать этот тезис. А помочь ему должны, сами\nтого не желая, комиссар полиции Джим Гордон и его красавица-дочь Барбара.", coverUrl = "batmanjoker"))
                viewModel.insert(Comic(title = "Синий Жук. Древние комиксы", author = "Стив Дитко", price = 799.50, description = "«Древние комиксы» – это забытая классика американских комиксов в новом цвете.\nПервый выпуск посвящён Стиву Дитко, который хорошо известен благодаря Человек-Пауку и Доктору Стрэнджу. Однако расцвет его\nкарьеры пришёлся на годы работы в издательстве CHARLTON, где он создал плеяду красочных персонажей, позднее перешедших в\nбиблиотеку DC. Синий Жук и Вопрос— яркие примеры таланта Дитко в создании супергероев. Образы этих борцов с преступностью\nлегли в основу графического романа «Хранители» Алана Мура!", coverUrl = "bluebug"))
                viewModel.insert(Comic(title = "Люди Икс: Дни минувшего будущего", author = "Алекс Ирвин", price = 899.00, description = "В темном и опасном будущем Америкой правят Стражи - охотящиеся на мутантов роботы. Большинство мутантов и суперлюдей были\nуничтожены, и всего несколько заключенных мутантов продолжают бороться с угнетающими их роботами. Чтобы предотвратить это\nужасное будущее, Кейт Прайд, одной из Людей Икс, предстоит отправиться в прошлое и предупредить товарищей о надвигающейся\nопасности. .",coverUrl = "xmanfuture"))
                viewModel.insert(Comic(title = "Человек-Паук. Страшная угроза", author = "Дэниел Кванц", price = 849.00, description = "– 6 увлекательных комиксов для фанатов Человека-Паука!\n– Смотрите фильмы, играйте в игры и читайте комиксы – только в комиксах у\nЧеловека-Паука больше всего противников и приключений!\n– Потрясающие драки с суперзлодеями и заклятыми врагами Человека-Паука:\nСтервятником, Электро, Осьминогом.",coverUrl = "spidermanscary"))
                viewModel.insert(Comic(title = "Лунный Рыцарь. Псих", author = "Джефф Лемир", price = 950.00, description = "Кто он - Лунный Рыцарь или просто псих?\nМарк Спектор - он же Лунный Рыцарь/Джейк Локли/Стивен Грант - боролся с преступностью и стоял на защите Нью-Йорка на\nпротяжении многих лет… или нет? Когда Марк просыпается в психиатрической больнице, без сил и с толстой медицинской картой, все\nего личности подвергаются сомнению. Он окружён чужими лицами: надменный врач, враждебные санитары, пациенты с\nотсутствующими взглядами.\nНо, быть может, все эти лица - просто маски. За некоторыми из них скрываются друзья, за другими - враги. Или хуже того - боги и\nмонстры! Марку придётся выбираться оттуда с боем. Придёт ли его покровитель на помощь в этот раз? Луна уже высоко, маска\nнадета, но, если удастся сбежать, найдет ли он что-то, кроме песчаного города? И что произойдет, когда Марк Спектор столкнётся\nлицом к лицу с… Лунным Рыцарем? Всё, что вы знаете, может оказаться неправдой - и было бы сумасшествием не узнать наверняка!",coverUrl = "moonknight"))
            }
        }

        // Подписка на обновления списка
        lifecycleScope.launch {
            viewModel.allComics.collect { comics ->
                adapter = ComicAdapter(comics) { comic ->
                    val intent = Intent(this@MainActivity, ItemActivity::class.java)
                    intent.putExtra("comic_id", comic.id)
                    startActivity(intent)
                }
                recyclerView.adapter = adapter
            }
        }

        // Кнопка корзины
        findViewById<Button>(R.id.btnGoToCart).setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}