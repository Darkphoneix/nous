package com.example.nous

import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.nous.models.Level
import com.example.nous.models.Question

class MainActivity : AppCompatActivity() {
    private lateinit var levelTextView: TextView
    private lateinit var videoView: VideoView
    private lateinit var progressTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var questionTextView: TextView
    private lateinit var optionsLayout: LinearLayout
    private lateinit var optionButtons: List<Button>

    private var levels: List<Level> = listOf() // List of levels (episodes)
    private var currentLevelIndex = 0
    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        loadLevels()
    }

    private fun initializeViews() {
        levelTextView = findViewById(R.id.levelTextView)
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

    private fun loadLevels() {
        // Fetch levels sorted by episodeNumber from storage or backend (currently TODO)
        levels = listOf(
            Level(1, "Intro to Philosophy", "android.resource://${packageName}/raw/intro_video", listOf(), 1),
            Level(2, "Philosophy and Logic", "android.resource://${packageName}/raw/logic_video", listOf(), 2)
        ).sortedBy { it.episodeNumber }

        loadCurrentLevel()
    }

    private fun loadCurrentLevel() {
        if (currentLevelIndex < levels.size) {
            val level = levels[currentLevelIndex]
            levelTextView.text = level.name
            videoView.setVideoURI(Uri.parse(level.videoPath))
            videoView.setOnCompletionListener {
                startQuestions(level)
            }
            videoView.start()
        } else {
            Toast.makeText(this, "No more episodes", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startQuestions(level: Level) {
        progressBar.max = level.questions.size
        currentQuestionIndex = 0
        setupQuestion(level)
    }

    private fun setupQuestion(level: Level) {
        if (currentQuestionIndex < level.questions.size) {
            val question = level.questions[currentQuestionIndex]
            questionTextView.text = question.question

            question.options.forEachIndexed { index, option ->
                optionButtons[index].apply {
                    text = option
                    setOnClickListener { checkAnswer(level, index) }
                }
            }

            progressTextView.text = "Progress: $currentQuestionIndex/${level.questions.size}"
            progressBar.progress = currentQuestionIndex
        } else {
            currentLevelIndex++
            loadCurrentLevel()
        }
    }

    private fun checkAnswer(level: Level, selectedIndex: Int) {
        val question = level.questions[currentQuestionIndex]

        optionButtons.forEach { it.isEnabled = false }

        if (selectedIndex == question.correctIndex) {
            correctAnswers++
        }

        android.os.Handler().postDelayed({
            optionButtons.forEach { it.isEnabled = true }
            currentQuestionIndex++
            setupQuestion(level)
        }, 1500)
    }
}