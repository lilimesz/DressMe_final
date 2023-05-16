package com.google.dressme

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.dressme.databinding.FragmentCameraViewBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


//typealias LumaListener = (luma: Double) -> Unit

class CameraView : Fragment() {
    private lateinit var viewBinding: FragmentCameraViewBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private lateinit var mActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //BottomNav disable
        val bview = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bview.visibility = View.GONE

        viewBinding = FragmentCameraViewBinding.inflate(layoutInflater)

        //Permission check & request
        if (allPermissionGranted()) {
            Toast.makeText(context, "Permissions OK", Toast.LENGTH_SHORT).show()
            startCamera()
        } else {
            Toast.makeText(context, "Permissions needed", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(
                requireActivity(),
                ConstantsCamera.REQUIRED_PERMISSIONS,
                ConstantsCamera.REQUEST_CODE_PERMISSIONS
            )
        }
        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }
        cameraExecutor = Executors.newSingleThreadExecutor()

        return viewBinding.root
    }

    private fun allPermissionGranted(): Boolean {
        return ConstantsCamera.REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(), it
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
                    this, cameraSelector, preview, imageCapture
                )
            } catch (exc: Exception) {
                Log.e(ConstantsCamera.TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this.requireContext()))

        imageCapture = ImageCapture.Builder().build()

    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        mActivity = (activity as MainActivity)

        imageCapture.takePicture(cameraExecutor, object : ImageCapture.OnImageCapturedCallback() {
            @SuppressLint("UnsafeOptInUsageError")
            override fun onCaptureSuccess(image: ImageProxy) {
                mActivity.replaceFragment(TakenPic())
                view?.findViewById<ImageView>(R.id.capturedImage)?.setImageBitmap(image.image?.toBitmap())
                //mActivity.replaceFragment(imageAnalysis(imageP.toBitmap()))
                image.close()
            }
        })

    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    fun Image.toBitmap(): Bitmap {
        val buffer = planes[0].buffer
        buffer.rewind()
        val bytes = ByteArray(buffer.capacity())
        buffer.get(bytes)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

}