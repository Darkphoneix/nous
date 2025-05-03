package com.example.nous

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nous.data.model.Lecture
import com.example.nous.data.model.Question

class MainActivity : AppCompatActivity() {
    private lateinit var lectureTextView: TextView
    private lateinit var videoView: VideoView
    private lateinit var progressTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var questionTextView: TextView
    private lateinit var optionsLayout: LinearLayout
    private lateinit var optionButtons: List<Button>

    private var lectures: List<Lecture> = listOf() // List of lectures
    private var currentLectureIndex = 0
    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        loadLectures()
    }

    private fun initializeViews() {
        lectureTextView = findViewById(R.id.lectureTextView)
        videoView = findViewById(R.id.videoView)
        progressTextView = findViewById(R.id.progressTextView)
        progressBar = findViewById(R.id.progressBar)
        questionTextView = findViewById(R.id.questionTextView)
        optionsLayout = findViewById(R.id.optionsLayout)
        optionButtons = listOf(
            findViewById(R.id.option1Button),
            findViewById(R.id.option2Button),
            findViewById(R.id.option3Button),
            findViewById(R.id.option4Button)
        )
    }

    private fun loadLectures() {
        // Load lectures from a local source (simulating AdminActivity's saved lectures)
        lectures = listOf(
            Lecture(
                id = 1,
                name = "Introduction to Philosophy",
                description = "A beginner's guide to philosophy.",
                videoPath = "android.resource://${packageName}/raw/intro_video",
                questions = mutableListOf(
                    Question(
                        id = "1",
                        levelId = "1",
                        question = "What is philosophy?",
                        options = listOf("Love of wisdom", "Science of nature", "Study of stars", "None of the above"),
                        correctOptionIndex = 0,
                        explanation = "Philosophy means love of wisdom."
                    )
                )
            ),
            Lecture(
                id = 2,
                name = "Philosophy and Logic",
                description = "An introduction to logical thinking.",
                videoPath = "android.resource://${packageName}/raw/logic_video",
                questions = mutableListOf(
                    Question(
                        id = "2",
                        levelId = "2",
                        question = "What is logic?",
                        options = listOf("Study of arguments", "Study of wisdom", "Study of emotions", "Study of stars"),
                        correctOptionIndex = 0,
                        explanation = "Logic is the study of arguments."
                    )
                )
            )
        )

        // Load the first lecture
        loadCurrentLecture()
    }

    private fun loadCurrentLecture() {
        if (currentLectureIndex < lectures.size) {
            val lecture = lectures[currentLectureIndex]
            lectureTextView.text = lecture.name
            videoView.setVideoURI(Uri.parse(lecture.videoPath))
            videoView.setOnCompletionListener {
                startQuestions(lecture)
            }
            videoView.start()
        } else {
            Toast.makeText(this, "No more lectures available.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startQuestions(lecture: Lecture) {
        progressBar.max = lecture.questions.size
        currentQuestionIndex = 0
        correctAnswers = 0
        setupQuestion(lecture)
    }

    private fun setupQuestion(lecture: Lecture) {
        if (currentQuestionIndex < lecture.questions.size) {
            val question = lecture.questions[currentQuestionIndex]
            questionTextView.text = question.question

            question.options.forEachIndexed { index, option ->
                optionButtons[index].apply {
                    text = option
                    setOnClickListener { checkAnswer(lecture, index) }
                }
            }

            progressTextView.text = "Progress: $currentQuestionIndex/${lecture.questions.size}"
            progressBar.progress = currentQuestionIndex
        } else {
            Toast.makeText(this, "Lecture completed! Correct answers: $correctAnswers/${lecture.questions.size}", Toast.LENGTH_LONG).show()
            currentLectureIndex++
            loadCurrentLecture()
        }
    }

    private fun checkAnswer(lecture: Lecture, selectedIndex: Int) {
        val question = lecture.questions[currentQuestionIndex]

        optionButtons.forEach { it.isEnabled = false }
        if (selectedIndex == question.correctOptionIndex) {
            correctAnswers++
        }

        android.os.Handler().postDelayed({
            optionButtons.forEach { it.isEnabled = true }
            currentQuestionIndex++
            setupQuestion(lecture)
        }, 1500)
    }
}