package com.mbsysoft.myapplication

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaScannerConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.FrameLayout
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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private var drawingView:DrawindView? = null
    private var mIvCurrentPaint : ImageButton? = null
    var customProgressDialog : Dialog? = null

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

            if (isReadStorageAllowed()) {
                showProgressDialog()
                lifecycleScope.launch {
                    val flDrawingView : FrameLayout = findViewById(R.id.fl_drawing_view_container)
//                    val myBitmap = getBitmapFromView(flDrawingView)
//                    saveBitmapFile(myBitmap)
                    saveBitmapFile(getBitmapFromView(flDrawingView))//프레임 레이아웃을 전달

                }
            }
        }

    }

    //모든 버전에 두 함수 모두 포함되었는지 확인하는 매소드
    private fun isReadStorageAllowed() : Boolean {
        val result = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE,)

        return result == PackageManager.PERMISSION_GRANTED
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
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
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

    private fun getBitmapFromView(view: View) : Bitmap {
        val returnBitmap = Bitmap.createBitmap(view.width,
            view.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(returnBitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }

        view.draw(canvas)

        return returnBitmap
    }

    private suspend fun saveBitmapFile(mBitmap: Bitmap?):String {
        var result = "" //이미지 저장할 변수
        withContext(Dispatchers.IO) {
            if (mBitmap != null) {
                try {// OutputStream 을 사용할 땐 오류에 대비해 try문에 쓴다
                    val bytes = ByteArrayOutputStream() //새로운 바이트 배열 출력 스트림을 생성하는 이미지를 출력하는 것
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 90, bytes)// 사용하고 싶은 포맷인 quality와 OutputStream 전달

                    val f = File(externalCacheDir?.absoluteFile.toString() +
                        File.separator + "kidDrawingApp_" + System.currentTimeMillis() / 1000 + ".png" //저장 위치 지정
                    )

                    val fo = FileOutputStream(f)
                    fo.write(bytes.toByteArray())
                    fo.flush()
                    fo.close()

                    result = f.absolutePath

                    runOnUiThread {
                        cancelProgressDialog()
                        if(result.isNotEmpty()) {
                            Toast.makeText(this@MainActivity, "$result", Toast.LENGTH_SHORT).show()

                            shareImage(result)

                        } else {
                            Toast.makeText(this@MainActivity, "문제 생심", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e : Exception) {
                    result = ""
                    e.printStackTrace()
                }
            }
        }

        return result
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

    private fun showProgressDialog() {
        customProgressDialog = Dialog(this@MainActivity)

        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        customProgressDialog?.show()
    }

    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }
    }

    private fun shareImage(result:String) {
        //MediaScannerConnection
        //파일로부터 미터 데이터를 읽어내고 media content provider에 파일을 추가한다
        //그다음 미디어 스캐너 서비스 인터페이스를 제공하는 미디어 스캐너 연결 클라이언트를 사용하여
        //미디어 스캐너 연결 클래스의 클라이언트에 미디어 검색 파일 uri를 반환한다

        MediaScannerConnection.scanFile(this, arrayOf(result), null) {
            //패스와 uri 연결
            path, uri ->
            val sharIntent = Intent()
            sharIntent.action = Intent.ACTION_SEND //아이템을 보낼 수 있는 인텐트
            sharIntent.putExtra(Intent.EXTRA_STREAM, uri)
            sharIntent.type = "image/png"
            startActivity(Intent.createChooser(sharIntent, "Share"))

        }
    }

}