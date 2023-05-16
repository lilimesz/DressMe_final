package com.google.dressme

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.media.Image
import android.os.Bundle
import android.os.ParcelFileDescriptor.open
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.dressme.databinding.FragmentCameraViewBinding
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


//typealias LumaListener = (luma: Double) -> Unit

class CameraView : Fragment() {
    private lateinit var viewBinding: FragmentCameraViewBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //BottomNav disable
        val bview = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bview.visibility=View.GONE

        viewBinding = FragmentCameraViewBinding.inflate(layoutInflater)

        //Permission check & request
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

    private fun allPermissionGranted(): Boolean {
        return ConstantsCamera.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(),it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
    private fun startCamera() {

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this.requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)
            } catch(exc:Exception) {
                Log.e(ConstantsCamera.TAG, "Use case binding failed",exc)
            }
        }, ContextCompat.getMainExecutor(this.requireContext()))

        imageCapture = ImageCapture.Builder().build()

    }
    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(requireActivity().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues()
            )
            .build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(ConstantsCamera.TAG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    Log.d(ConstantsCamera.TAG, msg)
                }
            }
        )



    }

    /*private fun uriToBitmap(selectedFileUri: Uri): Bitmap? {
        try {
            //val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(selectedFileUri, "r")
            //val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
            //val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val uri=intent.data
            val inputStream: InputStream =
                activity?.contentResolver?.openInputStream(uri!!)!!
            //parcelFileDescriptor.close()
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }*/

    fun Image.toBitmap(): Bitmap {
        val yBuffer = planes[0].buffer // Y
        val vuBuffer = planes[2].buffer // VU

        val ySize = yBuffer.remaining()
        val vuSize = vuBuffer.remaining()

        val nv21 = ByteArray(ySize + vuSize)

        yBuffer.get(nv21, 0, ySize)
        vuBuffer.get(nv21, ySize, vuSize)

        val yuvImage = YuvImage(nv21, ImageFormat.NV21, this.width, this.height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 50, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}