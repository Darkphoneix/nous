package com.example.nous

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nous.data.model.Lecture
import com.example.nous.data.model.Question
import com.example.nous.repository.NousRepository

class MainActivity : AppCompatActivity() {
    private lateinit var lectureTextView: TextView
    private lateinit var videoView: VideoView
    private lateinit var progressTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var questionTextView: TextView
    private lateinit var optionsLayout: LinearLayout
    private lateinit var optionButtons: List<Button>

    private var lectures: List<Lecture> = listOf()
    private var currentLectureIndex = 0
    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    // Repository to fetch data
    private val repository = NousRepository()

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
        // Fetch lectures directly from the repository
        lectures = repository.getLevels()

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
            Toast.makeText(
                this,
                "Lecture completed! Correct answers: $correctAnswers/${lecture.questions.size}",
                Toast.LENGTH_LONG
            ).show()
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
