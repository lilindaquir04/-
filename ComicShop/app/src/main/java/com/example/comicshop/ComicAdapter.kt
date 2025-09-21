package com.example.comicshop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ComicAdapter(
    private val comics: List<Comic>,
    private val onItemClick: (Comic) -> Unit
) : RecyclerView.Adapter<ComicAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val author: TextView = view.findViewById(R.id.tvAuthor)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val ivCover: ImageView = view.findViewById(R.id.ivCover)  // 👈 ДОБАВЛЕНО
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_comic, parent, false)
        return ComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = comics[position]
        holder.title.text = comic.title
        holder.author.text = "Автор: ${comic.author}"
        holder.price.text = "${comic.price} ₽"

        // Загружаем обложку из drawable по имени
        Glide.with(holder.itemView.context)
            .load(getDrawableResourceId(holder.itemView.context, comic.coverUrl))
            .placeholder(R.drawable.placeholder)  // ← серый плейсхолдер
            .error(R.drawable.error_image)        // ← если ошибка
            .into(holder.ivCover)

        holder.itemView.setOnClickListener {
            onItemClick(comic)
        }
    }

    override fun getItemCount() = comics.size

    // 👇 ЭТА ФУНКЦИЯ — ПРЕОБРАЗУЕТ ИМЯ ФАЙЛА В ID РЕСУРСА
    private fun getDrawableResourceId(context: Context, name: String): Int {
        return context.resources.getIdentifier(name, "drawable", context.packageName)
    }
}