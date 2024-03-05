package com.mbsysoft.myapplication

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {

    private var drawingView:DrawindView? = null
    private var mIvCurrentPaint : ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //메인 액티비티에서 볼 수 있도록 drawingView클래스 만듦

        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(10f)

        //색상 바꾸기!!!
        val linearLayoutPaintColor = findViewById<LinearLayout>(R.id.ll_color_selector)
        mIvCurrentPaint = linearLayoutPaintColor[1] as ImageButton //리니어 레이아웃 안에 있는 item을 index처럼 다루기
        // import androidx.core.view.get 필요


        findViewById<ImageButton>(R.id.ib_brush).setOnClickListener {
            showingBrushSizeChooserDialog() }


    }

    private fun showingBrushSizeChooserDialog() {
        val brushDialog = Dialog(this)
        brushDialog.setContentView(R.layout.dialog_brush_size)
        brushDialog.setTitle("BrushSize: ")

        val smallBtn : ImageButton =  brushDialog.findViewById(R.id.ib_small_brush)
        smallBtn.setOnClickListener {
            drawingView!!.setSizeForBrush(10f)
            brushDialog.dismiss()
        }

        val mediumBtn : ImageButton =  brushDialog.findViewById(R.id.ib_medium_brush)
        mediumBtn.setOnClickListener {
            drawingView!!.setSizeForBrush(20f)
            brushDialog.dismiss()
        }

        val largeBtn : ImageButton =  brushDialog.findViewById(R.id.ib_large_brush)
        largeBtn.setOnClickListener {
            drawingView!!.setSizeForBrush(30f)
            brushDialog.dismiss()
        }

        brushDialog.show()
    }
    
    fun paintClicked(view:View) {
        if (view !== mIvCurrentPaint) {
            val ivBtn = view as ImageButton
            val colorTag = ivBtn.tag.toString() // color.xml에 적힌 정보값 가져옴
            drawingView?.setColor(colorTag)

            ivBtn.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_pressed)
            ) //현재 선택한 버튼을 pess UI로 변경

            mIvCurrentPaint?.setImageDrawable(
                ContextCompat.getDrawable(this, R.drawable.pallet_normal) //선택 안된 버튼 ui 초기화
            )

            mIvCurrentPaint = view //현재 선택된 버튼으로 덮어 써줌(이전 버튼 초기화) => 이거 안하면 버튼이 전부 선택된 ui가 되어버림
        }
    }

}