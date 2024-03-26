package com.mbsysoft.myexerciseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.mbsysoft.myexerciseapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding : ActivityMainBinding? = null
    //상호 연결되는 화면만 참조하기 때문에 안전함

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.flStart?.setOnClickListener {
            startActivity(Intent(this, ExerciseActivity::class.java))
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //binding 을 사용 할 땐 onDestroy에서 binding = null로 재설정!!!

        binding = null //메모리 누수 방지
    }

}