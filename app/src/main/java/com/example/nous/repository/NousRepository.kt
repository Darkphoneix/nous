package com.example.nous.repository

import com.example.nous.data.model.*

class NousRepository {
    // Local data for lectures with video paths pointing to res/raw/ directory
    private val lectures = mutableListOf(
        Lecture(
            id = 1,
            name = "Introduction to Philosophy",
            description = "A beginner's guide to philosophy.",
            videoPath = "android.resource://com.example.nous/raw/lecture1.mp4",
            questions = mutableListOf(
                Question(
                    id = "1",
                    levelId = "1",
                    question = "What is philosophy?",
                    options = listOf("Love of wisdom", "Science of nature", "Study of stars", "None of the above"),
                    correctOptionIndex = 0,
                    explanation = "deneme"
                )

            )
        ),
        Lecture(
            id = 2,
            name = "Philosophy and Logic",
            description = "An introduction to logical thinking.",
            videoPath = "android.resource://com.example.nous/raw/lecture2",
            questions = mutableListOf(
                Question(
                    id = "2",
                    levelId = "2",
                    question = "What is logic?",
                    options = listOf("Study of arguments", "Study of wisdom", "Study of emotions", "Study of stars"),
                    correctOptionIndex = 0,
                    explanation = "deneme"
                )
            )
        )
    )

    fun getLectures(): List<Lecture> {
        return lectures
    }

    fun createLecture(lecture: Lecture): Lecture {
        lectures.add(lecture)
        return lecture
    }
}