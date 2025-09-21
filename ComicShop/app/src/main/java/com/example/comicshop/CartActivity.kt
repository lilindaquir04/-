package com.example.comicshop

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import android.view.View

class CartActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvTotal: TextView
    private lateinit var viewModel: ComicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        recyclerView = findViewById(R.id.recyclerViewCart)
        tvTotal = findViewById(R.id.tvTotal)
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ComicViewModel(application)

        lifecycleScope.launch {
            viewModel.allComics.collect { allComics ->
                val cartComics = allComics.filter { it.isInCart }
                val adapter = CartComicAdapter(cartComics) { comic ->
                    // Удаляем из корзины — обновляем isInCart = false
                    val updatedComic = comic.copy(isInCart = false)
                    viewModel.update(updatedComic)
                    // Адаптер обновится автоматически через collect
                }
                recyclerView.adapter = adapter

                val total = cartComics.sumOf { it.price }
                tvTotal.text = "Итого: ${String.format("%.2f", total)} ₽"
            }
        }

        findViewById<Button>(R.id.btnBack).setOnClickListener {
            finish()
        }
        lifecycleScope.launch {
            viewModel.allComics.collect { allComics ->
                val cartComics = allComics.filter { it.isInCart }
                val total = cartComics.sumOf { it.price }

                // Находим View
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewCart)
                val tvTotal = findViewById<TextView>(R.id.tvTotal)
                val tvEmptyCart = findViewById<TextView>(R.id.tvEmptyCart)

                if (cartComics.isEmpty()) {
                    // Корзина пуста — показываем надпись, скрываем список и сумму
                    tvEmptyCart.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                    tvTotal.visibility = View.GONE
                } else {
                    // Есть товары — показываем список и сумму, скрываем надпись
                    tvEmptyCart.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                    tvTotal.visibility = View.VISIBLE

                    // Обновляем адаптер
                    val adapter = CartComicAdapter(cartComics) { comic ->
                        val updatedComic = comic.copy(isInCart = false)
                        viewModel.update(updatedComic)
                    }
                    recyclerView.adapter = adapter

                    // Обновляем сумму
                    tvTotal.text = "Итого: ${String.format("%.2f", total)} ₽"
                }
            }
        }
    }
}