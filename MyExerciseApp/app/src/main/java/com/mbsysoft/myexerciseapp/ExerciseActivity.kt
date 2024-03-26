package com.mbsysoft.myexerciseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.mbsysoft.myexerciseapp.databinding.ActivityExerciseBinding

class ExerciseActivity : AppCompatActivity() {

    private var binding : ActivityExerciseBinding? = null

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarExercise)

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true) //백버튼
        }

        binding?.toolbarExercise?.setNavigationOnClickListener {
            onBackPressed()
        }

//        binding?.flProgressbar?.visibility = View.INVISIBLE
        setUpRestView()
    }

    private fun setUpRestView() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        setRestProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressbar?.progress = restProgress

        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding?.progressbar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()
                Log.i("TAG","${10 - restProgress}")
            }

            override fun onFinish() {
                setUpExerciseView()
            }

        }.start()
    }

    private fun setUpExerciseView() {
        binding?.flProgressbar?.visibility  = View.INVISIBLE
        binding?.tvTitle?.text = "운동 이름"
        binding?.flProgressbar2?.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding?.progressbar?.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressbarEx?.progress = 30 - exerciseProgress
                binding?.tvTimerEx?.text = (30 - exerciseProgress).toString()
                Log.i("TAG","${30 - exerciseProgress}")
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "운동 끝", Toast.LENGTH_SHORT).show()
            }

        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }

        binding = null
    }
}