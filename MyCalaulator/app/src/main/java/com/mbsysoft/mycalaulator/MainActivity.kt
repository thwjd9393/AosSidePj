package com.mbsysoft.mycalaulator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var tvInput :TextView? = null
    var isLastNum : Boolean = false
    var isLastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)

    }
    
    fun onDigit(view: View) {
        tvInput?.append((view as Button).text) //nullable덕에 if문으로 확인하지않아도 됨
        // view는 text를 가지지 않아서 바로 찾을 수 없다 view는 buttom으로 형변환 후 찾아줘야함

        isLastNum = true
        isLastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (isLastNum && !isLastDot) {
            //마지막이 숫자임
            tvInput?.append(".")
            isLastNum = false
            isLastDot = true
        }
    }

    fun onOperator(view: View) {
        tvInput?.text.let {//tvInput 에 값이 있어야 움직임
            if (isLastNum && !isOperatorAdded(it.toString())) {
                //마지막이 숫자인지 체크
                tvInput?.append((view as Button).text)
                isLastNum = false
                isLastDot = false
            }
        }
        
    }

    fun onEqual(view: View) {
        if (isLastNum) {
            var tvValue = tvInput?.text.toString()
            var prefix = ""

            try {

                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1) //음수 떼기
                }

                if(tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]
//                var result = one.toDouble() - two.toDouble()
//                tvInput?.text = result.toString()

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text =removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                } else if(tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                } else if(tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                } else if(tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                }

            } catch (e:ArithmeticException) {
                //산술에러
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String) : String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length -2)
        return  value
    }

    private fun isOperatorAdded(value:String) : Boolean {
        //숫자 앞에 기호가 있는지 체크해주는 메소드
        return if (value.startsWith("-")) { //음수 제외
            false
        } else {
            value.contains("/" )
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
    }
}