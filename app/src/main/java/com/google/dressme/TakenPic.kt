package com.google.dressme

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions

class TakenPic(private var bitmap: Bitmap) : Fragment() {
    private lateinit var mActivity: MainActivity
    private lateinit var img: ImageView
    private lateinit var image: InputImage
    private lateinit var output: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.item_pic_prev, container, false)
        mActivity = (activity as MainActivity)
        img = view.findViewById(R.id.capturedImage)
        output = view.findViewById(R.id.label_textView)

        val retakeBtn = view.findViewById<Button>(R.id.retake_button)
        val continueBtn = view.findViewById<Button>(R.id.continue_button)

        bitmap.apply {
            img.setImageBitmap(this)
        }


        image = InputImage.fromBitmap(bitmap, 90)
        img.rotation = 90F
        retakeBtn.setOnClickListener { mActivity.replaceFragment(CameraView()) }
        continueBtn.setOnClickListener {
            imageAnalyser()
        }

        return view
    }

    private fun imageAnalyser() {

        val localModel = LocalModel.Builder().setAssetFilePath("model.tflite").build()
        val options = CustomImageLabelerOptions.Builder(localModel).setConfidenceThreshold(0.05f)
            .setMaxResultCount(5).build()
        val labeler = ImageLabeling.getClient(options)
        var outputText = ""


        labeler.process(image).addOnSuccessListener { labels ->
            for (label in labels) {
                val text = label.text
                val confidence = label.confidence*100
                outputText += "$text : $confidence %\n"
            }
            output.text = outputText
        }.addOnFailureListener {
            // Task failed with an exception
        }


    }

}