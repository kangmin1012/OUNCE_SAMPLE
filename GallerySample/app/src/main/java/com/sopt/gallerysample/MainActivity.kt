package com.sopt.gallerysample

import android.annotation.SuppressLint
import android.app.Activity

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class MainActivity : AppCompatActivity() {
    private val galleryCode = 200
    private var running = true
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gallery_btn.setOnClickListener {

            settingPermission()
        }
        val h = DateRun()

        val thread = Thread(h)

        thread.start()





    }


    // 권한 설정을 쉽게 해주기 위한 TedPermission
    private fun settingPermission(){
        val permis = object : PermissionListener{
            override fun onPermissionGranted() {
                // start gallery
                callGallery()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@MainActivity, "권한 거부", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        TedPermission.with(this)
            .setPermissionListener(permis)
            .setRationaleMessage("갤러리 호출을 위해 권한을 설정해 주세요.")
            .setDeniedMessage("갤러리 권한은 앱 설정에서 변경 할 수 있습니다.")
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).check()
    }


    // 이미지 갤러리 호출
    private fun callGallery(){
//        var intent = Intent(Intent.ACTION_PICK)
//        intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
//        startActivityForResult(intent,GALLERY_CODE)
        CropImage.activity()
            .setGuidelines(CropImageView.Guidelines.ON)
            .setAspectRatio(4,3)
            .start(this)
    }


    // 갤러리에서 불러온 이미지 뷰에 뿌리기
    @SuppressLint("Recycle")
    @Suppress("DEPRECATION")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){

            //기본 갤러리 호출 시
            galleryCode ->{
                if(resultCode == Activity.RESULT_OK) {
                    val selectedImage: Uri = data?.data!!

                    val proj : Array<String> = arrayOf(MediaStore.Images.Media.DATA)
                    val cursor : Cursor? = contentResolver.query(selectedImage,proj,null,null,null)
                    val index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    cursor.moveToFirst()

                    val changeUri = cursor.getString(index)


                    Log.d("ViewUri", "$selectedImage")
                    Log.d("ViewUri", changeUri)
                    gallery_img.setImageURI(selectedImage)
                }
            }

            //크롭 라이브러리 이용 시
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                // result.uri = file:/// ....
                // result.originalUri = content:// ...
                val result = CropImage.getActivityResult(data)

                val proj : Array<String> = arrayOf(MediaStore.Images.Media.DATA)
                val cursor : Cursor? = contentResolver.query(result.originalUri,proj,null,null,null)
                val index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()

                // changeUri -> 절대 경로 : 서버에 이미지를 보내주기 위한 변경
                val changeUri = cursor.getString(index)



                Log.d("ViewUri", "${result.originalUri}, $changeUri")



                if(resultCode == Activity.RESULT_OK){
                    val selectedImage = result.uri
                    gallery_img.setImageURI(selectedImage)
                }
            }
        }

    }

    // 현재 시간이랑 특정 날짜랑의 차이를 반환하는 Runnable 객체
    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "CAST_NEVER_SUCCEEDS")
    inner class DateRun : Runnable{
        @SuppressLint("SimpleDateFormat")
        override fun run() {

            while (running){

                val now = System.currentTimeMillis()
                val date = Date(now)
                Log.d("NowDate","$date")

                val simpleDate = SimpleDateFormat("yyyy-MM-dd")
                val currentDate = simpleDate.format(date)

                val checkDate = "2020-06-21"

                count += 1

                try {
                    val date1 = simpleDate.parse(currentDate)
                    val date2 = simpleDate.parse(checkDate)

                    var calDate = date1.time - date2.time

                    calDate /= (24 * 60 * 60 * 1000)

                    calDate = abs(calDate)

                    Log.d("NowDate","{$currentDate} - {$checkDate} = $calDate")
                }catch (e : ParseException){
                    e.stackTrace
                }

                runOnUiThread {
                    time_txt.text = date.toString()
                }

                Thread.sleep(1000)

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        running = false
    }

}
