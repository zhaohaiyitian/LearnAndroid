package com.jack.learn.framework

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.jack.learn.R
import com.jack.learn.click
import com.jack.learn.databinding.ActivityScopeStorageBinding
import java.io.File
import kotlin.concurrent.thread

class ScopeStorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding = ActivityScopeStorageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        checkPermission(this)
        viewBinding.apply {
            create.click {
                create()
            }
            createSAF.click {
                createSAF()
            }
            insertImage.click {
                insertImage()
            }
            query.click {
                query()
            }
            updateImage.click {
                updateImage()
            }
            deleteImage.click {
                deleteImage()
            }
            downFile.click {

            }
        }
    }

    private fun checkPermission(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            activity.requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), 1
            )
        }
        return false
    }

    private fun create() {
        // 此种方式30以上版本报错   java.io.IOException: Operation not permitted
        var file = File(Environment.getExternalStorageDirectory(), "demo.txt")
        Log.d("wangjie", "create: " + file.exists())
        if (!file.exists()) {
            try {
                file.createNewFile()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    private fun createSAF() {
        // 操作数据库的方式操作文件
        // 类似于操作数据库external.db
        val contentUri = MediaStore.Files.getContentUri("external")
        val path = Environment.DIRECTORY_DOCUMENTS + "wj"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Downloads.RELATIVE_PATH, path)
        contentValues.put(MediaStore.Downloads.DISPLAY_NAME, path)
        contentValues.put(MediaStore.Downloads.TITLE, path)
        val insert = contentResolver.insert(contentUri, contentValues)
        if (insert != null) {
            Toast.makeText(this, "添加文件成功", Toast.LENGTH_SHORT).show();
        }
    }

    var imageUri: Uri? = null
    private fun insertImage() {
        val bitmap = BitmapFactory.decodeResource(resources, R.mipmap.image)
        val displayName = "${System.currentTimeMillis()}.jpg"
        val mimeType = "image/jpeg"
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.ImageColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Wj/")
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, displayName)
        contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, mimeType)
        imageUri =
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        val openOutputStream = contentResolver.openOutputStream(imageUri!!)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, openOutputStream)
        openOutputStream!!.close()
        if (imageUri != null) {
            Toast.makeText(this, "添加文件成功", Toast.LENGTH_SHORT).show()
        }
    }

    private fun query() {
        // /sdcard/Pictures/Wj/1731306311009.jpg
        val external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Images.ImageColumns.DISPLAY_NAME+"=?"
        val arg = arrayOf("1731306311009.jpg")
        val cursor = contentResolver.query(external, null, selection, arg, null)
        if (cursor != null && cursor.moveToFirst()) {
            val queryUri = ContentUris.withAppendedId(external,cursor.getLong(0))
            Toast.makeText(this, "查询成功$queryUri", Toast.LENGTH_SHORT).show()
            cursor.close()
        }
    }

    private fun updateImage() {
        val contentValues = ContentValues()
        contentValues.put(MediaStore.Images.ImageColumns.DISPLAY_NAME,"jack.jpg")
        contentResolver.update(imageUri!!,contentValues,null,null)
    }
    private fun deleteImage() {
        val i = contentResolver.delete(imageUri!!, null, null)
        if (i > 0) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
        }
    }

    private fun downloadFile() {
        thread {
            val values = ContentValues()
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, "test_qq.apk")
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + "/QQ")
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
                val uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values)
            }
            runOnUiThread {

            }
        }.start()
    }
}