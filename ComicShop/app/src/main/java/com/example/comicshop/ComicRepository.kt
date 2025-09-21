package com.example.comicshop

import androidx.lifecycle.LiveData

class ComicRepository(private val dao: ComicDao) {

    val allComics = dao.getAllComics()

    suspend fun insert(comic: Comic) = dao.insertComic(comic)
    suspend fun update(comic: Comic) = dao.updateComic(comic)
    suspend fun delete(comic: Comic) = dao.deleteComic(comic)

    fun getComicById(id: Int) = dao.getComicById(id)
}