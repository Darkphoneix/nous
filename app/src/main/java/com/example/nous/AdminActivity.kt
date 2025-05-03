package com.example.nous

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nous.models.Question
import com.example.nous.models.Level

class AdminActivity : AppCompatActivity() {
    private lateinit var levelNameInput: EditText
    private lateinit var episodeNumberInput: EditText // New input for episode number
    private lateinit var selectVideoButton: Button
    private lateinit var selectedVideoPath: TextView
    private lateinit var questionInput: EditText
    private lateinit var optionInputs: List<EditText>
    private lateinit var correctAnswerGroup: RadioGroup
    private lateinit var addQuestionButton: Button
    private lateinit var saveLevelButton: Button

    private var selectedVideoUri: Uri? = null
    private val questions = mutableListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        levelNameInput = findViewById(R.id.levelNameInput)
        episodeNumberInput = findViewById(R.id.episodeNumberInput) // Initialize new input
        selectVideoButton = findViewById(R.id.selectVideoButton)
        selectedVideoPath = findViewById(R.id.selectedVideoPath)
        questionInput = findViewById(R.id.questionInput)
        optionInputs = listOf(
            findViewById(R.id.option1Input),
            findViewById(R.id.option2Input),
            findViewById(R.id.option3Input),
            findViewById(R.id.option4Input)
        )
        correctAnswerGroup = findViewById(R.id.correctAnswerGroup)
        addQuestionButton = findViewById(R.id.addQuestionButton)
        saveLevelButton = findViewById(R.id.saveLevelButton)
    }

    private fun saveLevel() {
        val levelName = levelNameInput.text.toString()
        val episodeNumber = episodeNumberInput.text.toString().toIntOrNull() ?: 0 // Get episode number

        if (levelName.isBlank() || selectedVideoUri == null || questions.isEmpty() || episodeNumber == 0) {
            Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val newLevel = Level(
            id = 0, // Temporary ID, should be replaced by actual ID from the database
            name = levelName,
            videoPath = selectedVideoUri.toString(),
            questions = questions,
            episodeNumber = episodeNumber
        )

        // Save the level to the database or backend here (currently TODO)
        Toast.makeText(this, "Level saved", Toast.LENGTH_SHORT).show()
        finish()
    }
}