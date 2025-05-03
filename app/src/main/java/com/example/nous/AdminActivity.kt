package com.example.nous

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nous.models.Lecture
import com.example.nous.models.Question

class AdminActivity : AppCompatActivity() {
    private lateinit var lectureNameInput: EditText
    private lateinit var lectureDescriptionInput: EditText
    private lateinit var selectVideoButton: Button
    private lateinit var selectedVideoPath: TextView
    private lateinit var questionInput: EditText
    private lateinit var optionInputs: List<EditText>
    private lateinit var correctAnswerGroup: RadioGroup
    private lateinit var addQuestionButton: Button
    private lateinit var saveLectureButton: Button
    private lateinit var lecturesListView: ListView

    private val lectures = mutableListOf<Lecture>()
    private var selectedVideoUri: Uri? = null
    private var currentLecture: Lecture? = null
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
        lectureNameInput = findViewById(R.id.lectureNameInput)
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
        saveLectureButton = findViewById(R.id.saveLectureButton)
        lecturesListView = findViewById(R.id.lecturesListView)
    }

    private fun setupClickListeners() {
        selectVideoButton.setOnClickListener {
            val intent = Intent().apply {
                type = "video/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST)
        }

        addQuestionButton.setOnClickListener {
            addQuestion()
        }

        saveLectureButton.setOnClickListener {
            saveLecture()
        }
    }

    private fun addQuestion() {
        val questionText = questionInput.text.toString()
        val options = optionInputs.map { it.text.toString() }
        val correctIndex = correctAnswerGroup.indexOfChild(
            findViewById(correctAnswerGroup.checkedRadioButtonId)
        )

        if (questionText.isBlank() || options.any { it.isBlank() } || correctIndex == -1) {
            Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val question = Question(
            id = questions.size.toString(),
            levelId = currentLecture?.id.toString(),
            question = questionText,
            options = options,
            correctOptionIndex = correctIndex,
            explanation = ""
        )
        questions.add(question)
        Toast.makeText(this, "Question added", Toast.LENGTH_SHORT).show()

        // Clear inputs
        questionInput.text.clear()
        optionInputs.forEach { it.text.clear() }
        correctAnswerGroup.clearCheck()
    }

    private fun saveLecture() {
        val lectureName = lectureNameInput.text.toString()
        val lectureDescription = lectureDescriptionInput.text.toString()

        if (lectureName.isBlank() || selectedVideoUri == null) {
            Toast.makeText(this, "Fill all fields and select a video", Toast.LENGTH_SHORT).show()
            return
        }

        val lecture = Lecture(
            id = lectures.size,
            name = lectureName,
            description = lectureDescription,
            videoPath = selectedVideoUri.toString(),
            questions = questions.toMutableList()
        )

        lectures.add(lecture)
        currentLecture = null
        questions.clear()

        Toast.makeText(this, "Lecture saved", Toast.LENGTH_SHORT).show()
        updateLecturesList()
    }

    private fun updateLecturesList() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            lectures.map { "${it.name} (${it.questions.size} questions)" }
        )
        lecturesListView.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_VIDEO_REQUEST && resultCode == Activity.RESULT_OK) {
            selectedVideoUri = data?.data
            selectedVideoPath.text = selectedVideoUri?.path
        }
    }
}