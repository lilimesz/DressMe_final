package com.google.dressme

import android.Manifest
import android.os.Build

object ConstantsCamera {

    const val TAG = "CameraXApp"
    const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    const val REQUEST_CODE_PERMISSIONS = 123
    val IMAGE_CAPTURE_CODE = 654
    val REQUIRED_PERMISSIONS =
        mutableListOf (
            Manifest.permission.CAMERA,
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()



}