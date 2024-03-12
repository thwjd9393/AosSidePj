package com.mbsysoft.myapplication

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get

class MainActivity : AppCompatActivity() {

    private var drawingView:DrawindView? = null
    private var mIvCurrentPaint : ImageButton? = null

    val openGallertLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val imageBackGround : ImageView = findViewById(R.id.iv_background)

                imageBackGround.setImageURI(result.data?.data) //데이터의 위치를 받아 사용
            }
        }

    private val requestPermission : ActivityResultLauncher<Array<String>>
        = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            permissions ->
            permissions.entries.forEach {

                val permissionName = it.key
                val isGranted = it.value

                if (isGranted) {
                    // 갤러리에서 이미지 가져오기
                    if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE) {
                        Toast.makeText(this, "스토리지 퍼미션 완료", Toast.LENGTH_SHORT).show()
                    }

                    val pickIntent = Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    openGallertLauncher.launch(pickIntent) //이미지 런처를 통해 가져옴
                } else {
                    if (permissionName == Manifest.permission.READ_EXTERNAL_STORAGE) {
                        Toast.makeText(this, "스토리지 퍼미션 거부", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.M)
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
            showingBrushSizeChooserDialog()
        }

        findViewById<ImageButton>(R.id.ib_gallery).setOnClickListener {
            showGallery()
        }

        findViewById<ImageButton>(R.id.ib_undo).setOnClickListener {
            // DrawingView 클래서 안에 있는 onClickUndo 메서드를 사용하기 위해선 엑세스 한 후 사용할 수 있도록 확인해야 한다
            drawingView?.onClickUndo()
        }

        findViewById<ImageButton>(R.id.ib_redo).setOnClickListener {
            // DrawingView 클래서 안에 있는 onClickUndo 메서드를 사용하기 위해선 엑세스 한 후 사용할 수 있도록 확인해야 한다
            drawingView?.onClickRedo()
        }

        findViewById<ImageButton>(R.id.ib_save).setOnClickListener {

        }


    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showGallery() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showRationaleDialog("스토리지 퍼미션", "액세스 거부되어 실행 불가")
        } else {
            requestPermission.launch(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,
                    // TODO : 외부 저장소 데이터 출력 추가
                    ))
        }
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

    private fun showRationaleDialog(
        title : String,
        message : String,) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

}