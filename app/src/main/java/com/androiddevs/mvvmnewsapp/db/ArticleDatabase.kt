package com.androiddevs.mvvmnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.androiddevs.mvvmnewsapp.models.Article

//define entities for room database
//every entity has own table
@Database(
    entities = [Article::class],
    version = 1
)
//define type converters
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    //create a instance for DB
    //Volatile gives other threads a signal that this function is running
    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        //invoke is called on ArticleDatabase initialization
        //if instance = null crateDatabase is called
        //synchronized gives that this function is locked to one thread only
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()
    }
}