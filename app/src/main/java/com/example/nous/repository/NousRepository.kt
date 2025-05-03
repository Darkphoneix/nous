package com.example.nous.repository

import com.example.nous.data.model.*

class NousRepository {
    // Local data or in-memory storage logic can be added here
    // Example: Mocked data for levels
    private val lecture = mutableListOf(
        Lecture(id = 1, name = "Level 1", description = "1", videoPath = "app/src/main/res/raw/small.mp4"),
        Lecture(id = 2, name = "Level 2", "","")
    )

    fun getLevels(): List<Lecture> {
        return lecture
    }

    fun createLevel(level: Lecture): Lecture {
        lecture.add(level)
        return level
    }
}

