package com.example.comicshop

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ComicDao {
    @Query("SELECT * FROM comics")
    fun getAllComics(): Flow<List<Comic>>

    @Query("SELECT * FROM comics WHERE id = :id")
    fun getComicById(id: Int): Flow<Comic?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComic(comic: Comic)

    @Update
    suspend fun updateComic(comic: Comic)

    @Delete
    suspend fun deleteComic(comic: Comic)
}