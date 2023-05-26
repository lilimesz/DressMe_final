package com.google.dressme.clothesPage.newItem

import android.Manifest
import android.os.Build

object ConstantsCamera {

    const val TAG = "CameraXApp"
    const val REQUEST_CODE_PERMISSIONS = 123
    val REQUIRED_PERMISSIONS =
        mutableListOf (
            Manifest.permission.CAMERA,
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()



}