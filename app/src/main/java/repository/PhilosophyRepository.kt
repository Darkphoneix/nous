package repository

// repository/PhilosophyRepository.kt
class PhilosophyRepository(private val dao: PhilosophyDao) {
    fun getAllTopics(): Flow<List<PhilosophyTopic>> = dao.getAllTopics()
    fun getUserProgress(): Flow<UserProgress> = dao.getUserProgress("default_user")

    suspend fun completeTopic(topicId: Int) {
        dao.updateTopic(PhilosophyTopic(id = topicId, isCompleted = true))
        val progress = dao.getUserProgress("default_user").first()
        dao.updateUserProgress(progress.copy(dailyStreak = progress.dailyStreak + 1))
    }
}