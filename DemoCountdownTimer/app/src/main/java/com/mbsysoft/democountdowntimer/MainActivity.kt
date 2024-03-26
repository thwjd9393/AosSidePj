package com.mbsysoft.democountdowntimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.mbsysoft.democountdowntimer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null

    private var countDownTimer: CountDownTimer? = null
    private var timerDuration:Long = 60000
    // pauseOffset = timerDuration - time
    private var pauseOffset:Long = 0 //일시 정지 후 다시 시작하는 경우를 대비한 변수

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.tvTimer?.text = "${(timerDuration/1000).toString()}"

        binding?.btnStart?.setOnClickListener {
            startTimer(pauseOffset)
        }

        binding!!.btnPause.setOnClickListener {
            pauseTitme()
        }

        binding!!.btnReset.setOnClickListener {
            resetTimer()
        }
    }

    private fun startTimer(mPauseOffsetL: Long) {
        countDownTimer = object : CountDownTimer(timerDuration-mPauseOffsetL, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                pauseOffset = timerDuration - millisUntilFinished
                binding!!.tvTimer.text = (millisUntilFinished/1000).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Timer is Finished", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun pauseTitme() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
    }

    private fun resetTimer() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
            binding!!.tvTimer.text = "${(timerDuration/1000).toString()}"
            countDownTimer = null
            pauseOffset = 0
        }
    }
}