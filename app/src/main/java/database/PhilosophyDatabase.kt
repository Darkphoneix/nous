package database

// database/PhilosophyDatabase.kt
@Database(
    entities = [PhilosophyTopic::class, UserProgress::class],
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