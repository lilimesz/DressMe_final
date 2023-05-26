package com.google.dressme.clothesPage.newItem

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.dressme.MainActivity
import com.google.dressme.R
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions

class TakenPic(private var bitmap: Bitmap) : Fragment() {
    private lateinit var mActivity: MainActivity
    private lateinit var img: ImageView
    private lateinit var image: InputImage
    private lateinit var dressColor: Color

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.item_pic_prev, container, false)
        mActivity = (activity as MainActivity)
        img = view.findViewById(R.id.capturedImage)

        val retakeBtn = view.findViewById<Button>(R.id.retake_button)
        val continueBtn = view.findViewById<Button>(R.id.continue_button)

        bitmap.apply {
            img.setImageBitmap(this)
        }

        //dressColor=bitmap.getColor(bitmap.width/2,bitmap.height/2)
        dressColor=getDominantColor(bitmap)



        image = InputImage.fromBitmap(bitmap, 90)
        img.rotation = 90F
        retakeBtn.setOnClickListener { mActivity.replaceFragment(CameraView()) }
        continueBtn.setOnClickListener {
            imageAnalyser()

        }

        return view
    }

    fun imageAnalyser() {
        var labelString = ""
        val localModel = LocalModel.Builder().setAssetFilePath("model.tflite").build()
        val options = CustomImageLabelerOptions.Builder(localModel).setConfidenceThreshold(0.3f)
            .setMaxResultCount(5).build()
        val labeler = ImageLabeling.getClient(options)
        var i = 0


        labeler.process(image).addOnSuccessListener { labels ->
            for (label in labels) {
                if (i == 0) {
                    labelString = label.text
                    mActivity.replaceFragment(NewItemPage(labelString,dressColor))
                }
                i++
                val text = label.text
                val confidence = label.confidence * 100
                Log.d("Image Classif. results", "$text : $confidence %\n")
                Log.d("LABEL", labelString)
            }
        }.addOnFailureListener {
            // Task failed with an exception
        }


    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun getDominantColor(bitmap: Bitmap?): Color {
        val newBitmap = Bitmap.createScaledBitmap(bitmap!!, 1, 1, true)
        val color = newBitmap.getColor(0, 0)
        newBitmap.recycle()
        return color
    }
    }