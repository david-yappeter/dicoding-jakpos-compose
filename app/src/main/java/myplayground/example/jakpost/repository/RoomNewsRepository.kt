package myplayground.example.jakpost.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import myplayground.example.jakpost.database.FavouriteNewsDao
import myplayground.example.jakpost.database.FavouriteNewsEntity
import myplayground.example.jakpost.ui.utils.AppExecutors

interface LocalNewsRepository {
    fun fetchAll(): Flow<List<FavouriteNewsEntity>>
    fun insert(favouriteNews: FavouriteNewsEntity)
    fun delete(favouriteNews: FavouriteNewsEntity)

    fun getById(id: Int): Flow<FavouriteNewsEntity?>
}

class RoomLocalNewsRepository(
    private val favouriteNewsDao: FavouriteNewsDao,
    private val appExecutor: AppExecutors,
) : LocalNewsRepository {
    override fun fetchAll(): Flow<List<FavouriteNewsEntity>> {
        return flow {
            delay(1500)

            val data = favouriteNewsDao.fetchAll()

            emit(data)
        }
    }

    override fun insert(favouriteNews: FavouriteNewsEntity) {
        appExecutor.diskIO.execute {
            favouriteNewsDao.insert(favouriteNews)
        }
    }

    override fun delete(favouriteNews: FavouriteNewsEntity) {
        appExecutor.diskIO.execute {
            favouriteNewsDao.delete(favouriteNews)
        }
    }

    override fun getById(id: Int): Flow<FavouriteNewsEntity?> {
        return flow {
            val data = favouriteNewsDao.getById(id)

            emit(data)
        }
    }


    companion object {
        @Volatile
        private var instance: RoomLocalNewsRepository? = null
        fun getInstance(
            favouriteNewsDao: FavouriteNewsDao,
            appExecutor: AppExecutors,
        ): RoomLocalNewsRepository =
            instance ?: synchronized(this) {
                instance ?: RoomLocalNewsRepository(favouriteNewsDao, appExecutor)
            }.also { instance = it }
    }
}
