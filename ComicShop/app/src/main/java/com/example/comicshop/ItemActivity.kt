package com.example.comicshop

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemActivity : AppCompatActivity() {

    private lateinit var viewModel: ComicViewModel
    private lateinit var ivCover: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvPrice: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnAddToCart: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        val comicId = intent.getIntExtra("comic_id", -1)
        if (comicId == -1) {
            finish()
            return
        }

        viewModel = ComicViewModel(application)

        ivCover = findViewById(R.id.ivCover)
        tvTitle = findViewById(R.id.tvTitle)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvPrice = findViewById(R.id.tvPrice)
        tvDescription = findViewById(R.id.tvDescription)
        btnAddToCart = findViewById(R.id.btnAddToCart)
        btnBack = findViewById(R.id.btnBack)

        lifecycleScope.launch {
            viewModel.getComicById(comicId).collect { comic ->
                if (comic != null) {
                    tvTitle.text = comic.title
                    tvAuthor.text = "Автор: ${comic.author}"
                    tvPrice.text = "${comic.price} ₽"
                    tvDescription.text = comic.description

                    // Загружаем обложку
                    Glide.with(this@ItemActivity)
                        .load(getDrawableResourceId(comic.coverUrl))
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error_image)
                        .into(ivCover)

                    btnAddToCart.setOnClickListener {
                        val updatedComic = comic.copy(isInCart = true)
                        viewModel.update(updatedComic)
                        finish()
                    }
                }
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }

    // 👇 Упрощённая версия — использует this (текущий контекст)
    private fun getDrawableResourceId(name: String): Int {
        return resources.getIdentifier(name, "drawable", packageName)
    }
}