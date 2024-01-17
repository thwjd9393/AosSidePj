package com.mbsysoft.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var tvSelectDate : TextView? = null
    private var tvAgeInMin : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn : Button = findViewById(R.id.btn)
        tvSelectDate = findViewById(R.id.tv_select_date)
        tvAgeInMin = findViewById(R.id.tv_age_in_min)

        btn.setOnClickListener {
            cilickDatePicker()
        }
        
    }

    private fun cilickDatePicker() {

        val myCalander = Calendar.getInstance()
        val year = myCalander.get(Calendar.YEAR)
        val month = myCalander.get(Calendar.MONTH)
        val day = myCalander.get(Calendar.DAY_OF_MONTH)  //몇월 며칠

        val dpd = DatePickerDialog(this,
//            DatePickerDialog.OnDateSetListener(생략가능) { view, year, month, dayOfMonth ->
            { _, selectYear, selectMonth, selectDayOfMonth ->
                val selectDate = "$selectDayOfMonth/${selectMonth+1}/$selectYear"

                tvSelectDate?.text = selectDate

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.KOREA)
                val theDate = sdf.parse(selectDate)
                theDate?.let {//let이 날짜 선택여부 확인함, 오작동 방지
                    val selectedDateInMinutes = theDate.time / (1000 * 60 * 60 * 24)  //밀리세컨즈로 나와서 분으로 구해주기위해 나눔
                    //theDate.time 는 안정성과 버그에 취약함 null 방지 필요


                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) //1970기준

                    currentDate?.let {
                        val currentDateMinutes = currentDate.time / (1000 * 60 * 60 * 24)

                        val diffenceInMinutes = currentDateMinutes - selectedDateInMinutes

                        tvAgeInMin?.text = "$diffenceInMinutes"
                    }
                    
                }

            },
            year,
            month,
            day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 //기한 제한 , 어제날짜까지만 선택
        dpd.show()

    }
}