package com.huawei.analyticskit.kotlin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var questions: Array<String>

    private val answers = booleanArrayOf(true, true, false, false, true)

    private var curQuestionIdx = 0
    private var score = 0

    private val wrapper: HiAnalyticsWrapper by lazy {
        HiAnalyticsWrapper(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wrapper.setUpUserId()

        // Retrieve questions from resources
        questions = resources.getStringArray(R.array.questions)

        // You can also use Context initialization
        tvQuestion.text = questions[curQuestionIdx]

        btnSettings.setOnClickListener {
            val i = Intent(this@MainActivity, SettingActivity::class.java)
            startActivityForResult(i, 0)
        }

        btnNext.setOnClickListener {
            curQuestionIdx = (curQuestionIdx + 1) % questions.size
            nextQuestion()
        }

        btnAnswerTrue.setOnClickListener {
            checkAnswer(true)
            val questions = tvQuestion.text.toString().trim { it <= ' ' }
            wrapper.reportAnswerEvt(questions, "true")
        }

        btnAnswerFalse.setOnClickListener {
            checkAnswer(false)
            val questions = tvQuestion.text.toString().trim { it <= ' ' }
            wrapper.reportAnswerEvt(questions, "false")
        }

        btnPostScore.setOnClickListener {
            wrapper.postScore(score)
            Toast.makeText(this, R.string.post_report_answer, Toast.LENGTH_SHORT).show()
        }
    }

    private fun nextQuestion() {
        tvQuestion.text = questions[curQuestionIdx]
    }

    private fun checkAnswer(answer: Boolean): Boolean {
        if (answer == answers[curQuestionIdx]) {
            score += 20
            Toast.makeText(this, R.string.correct_answer, Toast.LENGTH_SHORT).show()
            // Report a customized Event
        } else {
            Toast.makeText(this, R.string.wrong_answer, Toast.LENGTH_SHORT).show()
            // Report a customized Event
        }
        return answers[curQuestionIdx]
    }
}