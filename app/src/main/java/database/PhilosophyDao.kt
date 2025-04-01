package database

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

// database/PhilosophyDao.kt
@Dao
interface PhilosophyDao {
    @Query("SELECT * FROM philosophy_topics")
    fun getAllTopics(): Flow<List<PhilosophyTopic>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopics(topics: List<PhilosophyTopic>)

    @Update
    suspend fun updateTopic(topic: PhilosophyTopic)

    @Query("SELECT * FROM user_progress WHERE userId = :userId")
    fun getUserProgress(userId: String): Flow<UserProgress>

    @Update
    suspend fun updateUserProgress(progress: UserProgress)
}