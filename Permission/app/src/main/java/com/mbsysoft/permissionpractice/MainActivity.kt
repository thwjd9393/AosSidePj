package com.mbsysoft.permissionpractice

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    // 1. activity result launcher 필요
    private val cameraResultLauncher : ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){ //권한 요청
                // 요청이 승인 됐는지 확인
                // 액티비티 결과를 register로 입력하면 activity result launcher가 돌아옴
                isGranted ->
                if(isGranted) {
                    Toast.makeText(this, "퍼미션 완료", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "퍼미션 거부", Toast.LENGTH_SHORT).show()
                }
            }

    // 2. 퍼미션 여러개 받기
    private val cameraAndLocationResultLauncher : ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){ //권한 요청
            permission ->
            permission.entries.forEach {
                // 리턴 => it:Map.Entry<String, Boolean>
                // 키 : 키워드 벨류 : 승인여부
                val permissionName = it.key
                val isGranted = it.value

                if(isGranted) { //승인 확인
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(this, "GPS 위치 퍼미션", Toast.LENGTH_SHORT).show()
                    } else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(this, "대략적 위치 퍼미션", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "카메라 퍼미션", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    if (permissionName == Manifest.permission.ACCESS_FINE_LOCATION) {
                        Toast.makeText(this, "위치 퍼미션 거부", Toast.LENGTH_SHORT).show()
                    } else if (permissionName == Manifest.permission.ACCESS_COARSE_LOCATION) {
                        Toast.makeText(this, "대략적 위치 퍼미션 거부", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "카메라 퍼미션 거부", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPermission : Button = findViewById(R.id.btnPermission)
//        btnPermission.setOnClickListener {
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
//                Toast.makeText(this, "액세스 거부되어 실행 불가", Toast.LENGTH_SHORT).show()
//            } else {
//                cameraResultLauncher.launch(Manifest.permission.CAMERA)
//            }
//        }

        btnPermission.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                Toast.makeText(this, "액세스 거부되어 실행 불가", Toast.LENGTH_SHORT).show()
            } else {
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,)
                    )
            }
        }

    }
}