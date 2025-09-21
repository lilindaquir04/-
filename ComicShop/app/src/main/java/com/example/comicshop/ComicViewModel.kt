package com.example.comicshop

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ComicViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var repository: ComicRepository

    val allComics: Flow<List<Comic>> by lazy {
        repository.allComics
    }

    init {
        val dao = AppDatabase.getDatabase(application).comicDao()
        repository = ComicRepository(dao)
    }

    fun insert(comic: Comic) = viewModelScope.launch {
        repository.insert(comic)
    }

    fun update(comic: Comic) = viewModelScope.launch {
        repository.update(comic)
    }

    fun delete(comic: Comic) = viewModelScope.launch {
        repository.delete(comic)
    }

    fun getComicById(id: Int) = repository.getComicById(id)
}