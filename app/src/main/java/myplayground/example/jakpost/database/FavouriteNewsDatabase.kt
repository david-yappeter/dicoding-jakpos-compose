package myplayground.example.jakpost.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FavouriteNewsEntity::class], version = 1)
abstract class FavouriteNewsDatabase : RoomDatabase() {
    abstract fun favouriteNewsDao(): FavouriteNewsDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteNewsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavouriteNewsDatabase {
            if (INSTANCE == null) {
                synchronized(FavouriteNewsDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext, FavouriteNewsDatabase::class.java, "db"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return INSTANCE as FavouriteNewsDatabase
        }
    }
}