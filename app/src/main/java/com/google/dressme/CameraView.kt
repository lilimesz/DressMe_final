package com.google.dressme

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.print.PrintAttributes.Resolution
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.Button
import androidx.camera.camera2.internal.compat.workaround.TargetAspectRatio.RATIO_4_3
import androidx.camera.core.*
import androidx.camera.video.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Locale

import com.google.dressme.databinding.ActivityMainBinding
import com.google.dressme.databinding.FragmentCameraViewBinding


typealias LumaListener = (luma: Double) -> Unit

class CameraView : Fragment() {
    private lateinit var viewBinding: FragmentCameraViewBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewBinding = FragmentCameraViewBinding.inflate(layoutInflater)
        val bview = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bview.visibility=View.GONE

        if (allPermissionGranted()) {
            Toast.makeText(context, "Permissions OK",Toast.LENGTH_SHORT).show()
            startCamera()
        }else{
            Toast.makeText(context, "Permissions needed",Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(requireActivity(),ConstantsCamera.REQUIRED_PERMISSIONS,ConstantsCamera.REQUEST_CODE_PERMISSIONS)

        }
        viewBinding.imageCaptureButton.setOnClickListener{takePhoto()}

        cameraExecutor = Executors.newSingleThreadExecutor()

        return viewBinding.root

    }

    private fun takePhoto() {}

    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)

                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this,cameraSelector,preview)

            } catch(exc:Exception) {
                Log.e(ConstantsCamera.TAG, "Use case binding failed",exc)
            }
        }, ContextCompat.getMainExecutor(this.requireContext()))


    }



    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == ConstantsCamera.REQUEST_CODE_PERMISSIONS) {

            if (allPermissionGranted()) {
                startCamera()

            }else{
                Toast.makeText(context, "No permission for camera",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun allPermissionGranted(): Boolean {
        return ConstantsCamera.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(),it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }



}