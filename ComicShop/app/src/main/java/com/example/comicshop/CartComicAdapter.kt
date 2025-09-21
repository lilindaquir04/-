package com.example.comicshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CartComicAdapter(
    private val comics: List<Comic>,
    private val onRemoveFromCart: (Comic) -> Unit
) : RecyclerView.Adapter<CartComicAdapter.CartComicViewHolder>() {

    class CartComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val author: TextView = view.findViewById(R.id.tvAuthor)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val btnRemove: Button = view.findViewById(R.id.btnRemoveFromCart)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartComicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart_comic, parent, false)
        return CartComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartComicViewHolder, position: Int) {
        val comic = comics[position]
        holder.title.text = comic.title
        holder.author.text = "Автор: ${comic.author}"
        holder.price.text = "${comic.price} ₽"

        holder.btnRemove.setOnClickListener {
            onRemoveFromCart(comic)
        }
    }

    override fun getItemCount() = comics.size
}