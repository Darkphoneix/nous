package data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import data.UserProgress

// database/PhilosophyDatabase.kt
@Database(
    entities = [PhilosophyTopic::class, UserProgress::class], // ✅ Her iki entity'yi ekleyin
    version = 1,
    exportSchema = false
)
abstract class PhilosophyDatabase : RoomDatabase() {
    abstract fun philosophyDao(): PhilosophyDao

    companion object {
        @Volatile
        private var INSTANCE: PhilosophyDatabase? = null

        fun getDatabase(context: Context): PhilosophyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PhilosophyDatabase::class.java,
                    "philosophy_db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}