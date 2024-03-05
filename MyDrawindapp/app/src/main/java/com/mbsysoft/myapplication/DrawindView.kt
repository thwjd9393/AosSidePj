package com.mbsysoft.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawindView(context: Context, attrs:AttributeSet) : View(context, attrs) {
    // 뷰로 사용 할것
    // 액티비티로 사용하지 않는 이유는 메인 액티비티에서 사용하는 AppCompatActivity()에 포함된 기능을 나중에 사용할 수 있기 때문
    // 무언가를 그리고싶다면 뷰 타입을 사용해야하기 때문에 액티비티로 만들지않음

    // AttributeSet : 속성을 추가할수 있도록 도와주는 넘

    private var mDrawPath : CustomPath? = null
    private var mCanvasBitmap : Bitmap? = null
    private var mDrawPaint:Paint? = null
    private var mCanvasPaint: Paint? = null
    private var mbrushSize: Float = 0f
    private var color = Color.BLACK
    private var convas : Canvas? = null
    private val mPaths = ArrayList<CustomPath>()

    init {
        setUpDrawing()
    }

    private fun setUpDrawing() {
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color, mbrushSize)
        mDrawPaint!!.color = color
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND //스트록 시작
        mDrawPaint!!.strokeCap = Paint.Cap.ROUND //스트록 끝 Cap : 선 끝의 위치를 정함
        mCanvasPaint = Paint(Paint.DITHER_FLAG) //페인트(색상)를 정하기
//        mbrushSize = 20f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) { //화면 크리가 바뀔때마다 불러오는 메소드
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        //ARGB_8888 각 픽셀이 4바이트에 저장되고 RGB및 투명도는 8비트의 정밀도로 저장 (사용할 수 있는 색상 분량을 말하는 것)

        convas = Canvas(mCanvasBitmap!!) //캔버스 설정
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(mCanvasBitmap!!, 0f,0f, mCanvasPaint)

        for (path in mPaths) {
            mDrawPaint!!.strokeWidth = path.brushThickness
            mDrawPaint!!.color = path.color //선마다 글자두꼐나 색이 다를 수 있으니 mDrawPath 대신 각각의 path에 붙여줌
            canvas.drawPath(path, mDrawPaint!!)
        }

        if(!mDrawPath!!.isEmpty){
            //브러쉬 사이즈 지정
            mDrawPaint!!.strokeWidth = mDrawPath!!.brushThickness
            mDrawPaint!!.color = mDrawPath!!.color
            canvas.drawPath(mDrawPath!!, mDrawPaint!!)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        val touchX = event?.x
        val touchY = event?.y

        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                //눌렀을 때
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mbrushSize

                mDrawPath!!.reset()
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.moveTo(touchX,touchY)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (touchX != null) {
                    if (touchY != null) {
                        mDrawPath!!.lineTo(touchX,touchY)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                mPaths.add(mDrawPath!!)
                mDrawPath = CustomPath(color, mbrushSize)
            }
            else -> return false
        }

        invalidate() //전체 뷰의 모든 이미지 없앰, 무효화

        return true
    }

    fun setSizeForBrush(newSize:Float) {
        mbrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            newSize, resources.displayMetrics)
            //TypedValue.COMPLEX_UNIT_DIP => 픽셀의 밀도 정함
            //resources.displayMetrics => 화면의 측정 단위에 따라 조정됨
        mDrawPaint!!.strokeWidth = mbrushSize
    }

    //internal inner : CustomPath 내부에서만 사용, 변수를 가져오고 내보낼 수 있음
    internal inner class CustomPath(var color:Int,
                                    var brushThickness:Float) : Path(){

    }
}