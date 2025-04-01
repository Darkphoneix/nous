package data

// data/PhilosophyTopic.kt
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "philosophy_topics") // ✅ Tablo adını belirtin
data class PhilosophyTopic(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val philosopher: String,
    val difficulty: String,
    val isCompleted: Boolean = false
)