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

    private var currentLevel: Level? = null
    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        loadLevel()
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

    private fun loadLevel() {
        // TODO: Load level data from database or storage
        // For now, we'll use dummy data
        currentLevel = Level(
            1,
            "Giriş: Felsefe Nedir?",
            "android.resource://${packageName}/raw/intro_video",
            listOf(
                Question(1, "Felsefe kelimesinin kökeni nedir?",
                    listOf("Philia", "Philosophia", "Sophia", "Philo"), 1, 1)
                // Add more questions
            )
        )

        setupVideo()
    }

    private fun setupVideo() {
        currentLevel?.let { level ->
            levelTextView.text = level.name
            videoView.setVideoURI(Uri.parse(level.videoPath))
            videoView.setOnCompletionListener {
                startQuestions()
            }
            videoView.start()
        }
    }

    private fun startQuestions() {
        currentLevel?.let { level ->
            progressBar.max = level.questions.size
            setupQuestion()
        }
    }

    private fun setupQuestion() {
        currentLevel?.let { level ->
            if (currentQuestionIndex < level.questions.size) {
                val question = level.questions[currentQuestionIndex]
                questionTextView.text = question.question

                question.options.forEachIndexed { index, option ->
                    optionButtons[index].apply {
                        text = option
                        setOnClickListener { checkAnswer(index) }
                    }
                }

                progressTextView.text = "İlerleme: $currentQuestionIndex/${level.questions.size}"
                progressBar.progress = currentQuestionIndex
            } else {
                showResults()
            }
        }
    }

    private fun checkAnswer(selectedIndex: Int) {
        currentLevel?.let { level ->
            val question = level.questions[currentQuestionIndex]

            optionButtons.forEach { it.isEnabled = false }

            if (selectedIndex == question.correctIndex) {
                optionButtons[selectedIndex].setBackgroundColor(getColor(R.color.green))
                correctAnswers++
            } else {
                optionButtons[selectedIndex].setBackgroundColor(getColor(R.color.red))
                optionButtons[question.correctIndex].setBackgroundColor(getColor(R.color.green))
            }

            android.os.Handler().postDelayed({
                optionButtons.forEach {
                    it.setBackgroundColor(getColor(R.color.purple_500))
                    it.isEnabled = true
                }

                currentQuestionIndex++
                setupQuestion()
            }, 1500)
        }
    }

    private fun showResults() {
        currentLevel?.let { level ->
            questionTextView.text = "Tebrikler!\nDoğru cevap sayınız: $correctAnswers/${level.questions.size}"
            optionsLayout.visibility = LinearLayout.GONE
        }
    }
}