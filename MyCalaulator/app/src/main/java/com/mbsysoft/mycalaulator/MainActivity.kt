package com.mbsysoft.mycalaulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var tvInput :TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)

    }
    
    fun onDigit(view: View) {
        tvInput?.append((view as Button).text) //nullable덕에 if문으로 확인하지않아도 됨
        // view는 text를 가지지 않아서 바로 찾을 수 없다 view는 buttom으로 형변환 후 찾아줘야함

    }

    fun onClear(view: View) {
        tvInput?.text = "0"
    }
    
}