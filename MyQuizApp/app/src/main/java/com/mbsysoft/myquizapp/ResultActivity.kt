package com.mbsysoft.myquizapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvName : TextView = findViewById(R.id.tv_userName)
        val tvResultScore : TextView = findViewById(R.id.tv_result_score)
        val btnResult : Button = findViewById(R.id.btn_finish)

        val correctNums = intent.getIntExtra(Contants.CORRECT_ANSWERS, 0)
        val totalQuestions = intent.getIntExtra(Contants.TOTAL_QUESTIONS,0)

        tvName.text = intent.getStringExtra(Contants.USER_NAME)

        tvResultScore.text = "Your Score is $correctNums out of $totalQuestions"

        btnResult.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}