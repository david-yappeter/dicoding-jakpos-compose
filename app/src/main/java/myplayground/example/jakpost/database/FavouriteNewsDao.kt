package myplayground.example.jakpost.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteNewsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favouriteNews: FavouriteNewsEntity)

    @Delete
    fun delete(favouriteNews: FavouriteNewsEntity)

    @Query("SELECT * FROM favourite_news")
    suspend fun fetchAll(): List<FavouriteNewsEntity>

    @Query("SELECT * FROM favourite_news WHERE id = :id")
    suspend fun getById(id: Int): FavouriteNewsEntity?
}