package com.example.comicshop

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comics")
data class Comic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val author: String,
    val price: Double,
    val description: String,
    val coverUrl: String,
    var isInCart: Boolean = false  // для корзины
)