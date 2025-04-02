package com.example.nous

import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var progressTextView: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var questionTextView: TextView
    private lateinit var option1Button: Button
    private lateinit var option2Button: Button
    private lateinit var option3Button: Button
    private lateinit var option4Button: Button

    private var currentQuestionIndex = 0
    private var correctAnswers = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupQuestion()
        setupClickListeners()
    }

    private fun initializeViews() {
        progressTextView = findViewById(R.id.progressTextView)
        progressBar = findViewById(R.id.progressBar)
        questionTextView = findViewById(R.id.questionTextView)
        option1Button = findViewById(R.id.option1Button)
        option2Button = findViewById(R.id.option2Button)
        option3Button = findViewById(R.id.option3Button)
        option4Button = findViewById(R.id.option4Button)

        progressBar.max = questions.size
    }

    private fun setupQuestion() {
        val currentQuestion = questions[currentQuestionIndex]
        questionTextView.text = currentQuestion.question
        option1Button.text = currentQuestion.options[0]
        option2Button.text = currentQuestion.options[1]
        option3Button.text = currentQuestion.options[2]
        option4Button.text = currentQuestion.options[3]

        progressTextView.text = "İlerleme: $currentQuestionIndex/${questions.size}"
        progressBar.progress = currentQuestionIndex
    }

    private fun setupClickListeners() {
        val buttons = listOf(option1Button, option2Button, option3Button, option4Button)

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                checkAnswer(index)
            }
        }
    }

    private fun checkAnswer(selectedIndex: Int) {
        val currentQuestion = questions[currentQuestionIndex]
        val buttons = listOf(option1Button, option2Button, option3Button, option4Button)

        buttons.forEach { it.isEnabled = false }

        if (selectedIndex == currentQuestion.correctIndex) {
            buttons[selectedIndex].setBackgroundColor(Color.GREEN)
            correctAnswers++
        } else {
            buttons[selectedIndex].setBackgroundColor(Color.RED)
            buttons[currentQuestion.correctIndex].setBackgroundColor(Color.GREEN)
        }

        android.os.Handler().postDelayed({
            buttons.forEach {
                it.setBackgroundColor(getColor(R.color.purple_500))
                it.isEnabled = true
            }

            currentQuestionIndex++
            if (currentQuestionIndex < questions.size) {
                setupQuestion()
            } else {
                showResults()
            }
        }, 1500)
    }

    private fun showResults() {
        questionTextView.text = "Tebrikler!\nDoğru cevap sayınız: $correctAnswers/${questions.size}"
        option1Button.visibility = Button.GONE
        option2Button.visibility = Button.GONE
        option3Button.visibility = Button.GONE
        option4Button.visibility = Button.GONE
    }

    companion object {
        val questions = listOf(
            Question(
                "Sokrates'in ünlü sözü nedir?",
                listOf(
                    "Bildiğim tek şey, hiçbir şey bilmediğimdir",
                    "Düşünüyorum, öyleyse varım",
                    "İnsan her şeyin ölçüsüdür",
                    "Kendini bil"
                ),
                0
            ),
            Question(
                "Determinizm nedir?",
                listOf(
                    "Her şeyin tesadüf olduğunu savunan görüş",
                    "Her olayın bir nedeni olduğunu savunan görüş",
                    "İnsanın özgür olduğunu savunan görüş",
                    "Tanrının varlığını savunan görüş"
                ),
                1
            ),
            // Daha fazla soru ekleyebilirsiniz
        )
    }
}