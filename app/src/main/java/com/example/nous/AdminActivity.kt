package com.example.nous

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AdminActivity : AppCompatActivity() {
    private lateinit var levelNameInput: EditText
    private lateinit var selectVideoButton: Button
    private lateinit var selectedVideoPath: TextView
    private lateinit var questionInput: EditText
    private lateinit var optionInputs: List<EditText>
    private lateinit var correctAnswerGroup: RadioGroup
    private lateinit var addQuestionButton: Button
    private lateinit var saveLevelButton: Button

    private var selectedVideoUri: Uri? = null
    private val questions = mutableListOf<Question>()

    companion object {
        private const val PICK_VIDEO_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        initializeViews()
        setupClickListeners()
    }

    private fun initializeViews() {
        levelNameInput = findViewById(R.id.levelNameInput)
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

    private fun setupClickListeners() {
        selectVideoButton.setOnClickListener {
            val intent = Intent().apply {
                type = "video/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(Intent.createChooser(intent, "Video Seç"), PICK_VIDEO_REQUEST)
        }

        addQuestionButton.setOnClickListener {
            addQuestion()
        }

        saveLevelButton.setOnClickListener {
            saveLevel()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedVideoUri = uri
                selectedVideoPath.text = uri.toString()
            }
        }
    }

    private fun addQuestion() {
        val questionText = questionInput.text.toString()
        val options = optionInputs.map { it.text.toString() }
        val correctIndex = correctAnswerGroup.indexOfChild(
            findViewById(correctAnswerGroup.checkedRadioButtonId)
        )

        if (questionText.isBlank() || options.any { it.isBlank() } || correctIndex == -1) {
            Toast.makeText(this, "Tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            return
        }

        questions.add(Question(
            questions.size.toString(),
            questionText,
            options,
            correctIndex,
            0 // temporary levelId
        ))

        // Clear inputs
        questionInput.text.clear()
        optionInputs.forEach { it.text.clear() }
        correctAnswerGroup.clearCheck()

        Toast.makeText(this, "Soru eklendi", Toast.LENGTH_SHORT).show()
    }

    private fun saveLevel() {
        val levelName = levelNameInput.text.toString()

        if (levelName.isBlank() || selectedVideoUri == null || questions.isEmpty()) {
            Toast.makeText(this, "Tüm gerekli alanları doldurun", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Save level to database or storage
        Toast.makeText(this, "Seviye kaydedildi", Toast.LENGTH_SHORT).show()
        finish()
    }
}