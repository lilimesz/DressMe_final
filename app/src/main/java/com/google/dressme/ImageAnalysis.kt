package com.google.dressme

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions

class imageAnalysis(bitmap: Bitmap?):Fragment() {
    val localModel = LocalModel.Builder()
        .setAssetFilePath("model.tflite")
        .build()

    val options = CustomImageLabelerOptions.Builder(localModel)
        .setConfidenceThreshold(0.5f)
        .setMaxResultCount(3)
        .build()
    val labeler = ImageLabeling.getClient(options)
    val image = InputImage.fromBitmap(bitmap!!, 0)
    var output = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bview = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bview.visibility = View.GONE

        val view = inflater.inflate(R.layout.item_pic_prev, container, false)

        labeler.process(image)
            .addOnSuccessListener { labels ->
                // Task completed successfully
                for (label in labels) {
                    val text = label.text
                    val confidence = label.confidence
                    output += "$text : $confidence aaaaaaaaaaaaaaaaa"
                }
                Log.e("ASDASDASD", output)
            }
            .addOnFailureListener {
                // Task failed with an exception
            }
        return view
    }

    }



