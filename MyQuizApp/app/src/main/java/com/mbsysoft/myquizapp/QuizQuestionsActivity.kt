package com.mbsysoft.myquizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class QuizQuestionsActivity : AppCompatActivity(),View.OnClickListener {

    private var progressBar : ProgressBar? = null
    private var tvProgress: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage : ImageView? = null

    private var tvQu1: TextView? = null
    private var tvQu2: TextView? = null
    private var tvQu3: TextView? = null
    private var tvQu4: TextView? = null

    private var btnSubmit : Button? = null

    private var mCurrentPosition : Int = 1
    private var mQuestionList : ArrayList<Question>? = null
    private var mSelectOptionPosition:Int = 0 //버튼을 눌렀을 때 재정의 하기 위한 변수, 어떤 버튼을 눌렀는지 알기 위한 것

    private var mUserName : String? = null
    private var mCorrectAnsers : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_questions)

        mUserName = intent.getStringExtra(Contants.USER_NAME)
        mCorrectAnsers = 0

        progressBar = findViewById(R.id.progressbar)
        tvProgress = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage = findViewById(R.id.iv_image)

        tvQu1 = findViewById(R.id.tv_qu1)
        tvQu2 = findViewById(R.id.tv_qu2)
        tvQu3 = findViewById(R.id.tv_qu3)
        tvQu4 = findViewById(R.id.tv_qu4)

        btnSubmit = findViewById(R.id.btn_submit)

        tvQu1?.setOnClickListener(this)
        tvQu2?.setOnClickListener(this)
        tvQu3?.setOnClickListener(this)
        tvQu4?.setOnClickListener(this)

        btnSubmit?.setOnClickListener(this)

        mQuestionList = Contants.getQuestion()

        setQuestion()
    }

    private fun setQuestion() {

        defaultOptionView()

        val question: Question = mQuestionList!![mCurrentPosition - 1]

        ivImage?.setImageResource(question.image)

        progressBar?.progress = mCurrentPosition
        tvProgress?.text = "$mCurrentPosition / ${progressBar?.max}"
        tvQuestion?.text = question.question

        tvQu1?.text = question.optionOne
        tvQu2?.text = question.optionTwo
        tvQu3?.text = question.optionThree
        tvQu4?.text = question.optionFour

        if (mCurrentPosition == mQuestionList!!.size) {
            btnSubmit?.text = "FINISH"
        } else {
            btnSubmit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionView() {
        val options = ArrayList<TextView>()
        tvQu1?.let {
            options.add(0, it)
        }
        tvQu2?.let {
            options.add(1, it)
        }
        tvQu3?.let {
            options.add(2, it)
        }
        tvQu4?.let {
            options.add(3, it)
        }

        for (option in options) {
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT //디폴트값을 설정해서 옵션을 선택했을 때 바뀌게 하는 용도
            option.background = ContextCompat.getDrawable(
                this, R.drawable.default_option_border_bg
            )
        }
    }

    private fun selectOptionView(tv:TextView, selectedOptionNum: Int) {
        defaultOptionView()

        mSelectOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface , Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this, R.drawable.selected_option_border_bg
        )
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.tv_qu1 -> {
                tvQu1?.let {
                    selectOptionView(it,1)
                }
            }
            R.id.tv_qu2 -> {
                tvQu2?.let {
                    selectOptionView(it,2)
                }
            }
            R.id.tv_qu3 -> {
                tvQu3?.let {
                    selectOptionView(it,3)
                }
            }
            R.id.tv_qu4 -> {
                tvQu4?.let {
                    selectOptionView(it,4)
                }
            }
            R.id.btn_submit -> {
                if (mSelectOptionPosition == 0) {
                    mCurrentPosition++

                    when {
                        mCurrentPosition <= mQuestionList!!.size -> {
                            setQuestion()
                        }
                        else -> {
                            Toast.makeText(this, "The End!", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Contants.USER_NAME, mUserName)
                            intent.putExtra(Contants.CORRECT_ANSWERS,mCorrectAnsers)
                            intent.putExtra(Contants.TOTAL_QUESTIONS,mQuestionList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    // 질문 불러오기
                    val question = mQuestionList?.get(mCurrentPosition-1)

                    if(question!!.correctAnswer != mSelectOptionPosition) {
                        anserView(mSelectOptionPosition, R.drawable.wrong_option_border_bg)
                    } else {
                        mCorrectAnsers++
                    }
                    anserView(question.correctAnswer , R.drawable.correct_option_border_bg) //무엇을 선택하든 정답을 보여주기 위해 else밖에 씀

                    if (mCurrentPosition == mQuestionList!!.size) {
                        btnSubmit?.text = "FINISH"
                    } else {
                        btnSubmit?.text = "GO TO NEXT QUESTION"
                    }

                    mSelectOptionPosition = 0
                }
            }
        }
    }

    private fun anserView(answer:Int, drawableView:Int) {
        when(answer) {
            1 -> {
                tvQu1?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            2 -> {
                tvQu2?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            3 -> {
                tvQu3?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
            4 -> {
                tvQu4?.background = ContextCompat.getDrawable(
                    this,
                    drawableView
                )
            }
        }
    }
}