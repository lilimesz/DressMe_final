package com.google.dressme

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.common.model.LocalModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.custom.CustomImageLabelerOptions

fun imageAnalysis(bitmap: Bitmap?) {





    val localModel = LocalModel.Builder()
        .setAssetFilePath("model.tflite")
        .build()

    val options = CustomImageLabelerOptions.Builder(localModel)
        .setConfidenceThreshold(0.5f)
        .setMaxResultCount(3)
        .build()
    val labeler = ImageLabeling.getClient(options)
    val image = InputImage.fromBitmap(bitmap!!, 0)
    var output=""

     labeler.process(image)
                .addOnSuccessListener { labels ->
                    // Task completed successfully
                    for (label in labels) {
                        val text = label.text
                        val confidence = label.confidence
                        output += "$text : $confidence aaaaaaaaaaaaaaaaa"
                    }
                    Log.e("ASDASDASD",output)
                }
                .addOnFailureListener {
                    // Task failed with an exception
                }

        }


  /*  // extension function to get bitmap from assets
    fun Context.assetsToBitmap(fileName: String): Bitmap? {
        return try {
            with(assets.open(fileName)) {
                BitmapFactory.decodeStream(this)
            }
        } catch (e: IOException) {
            null
        }
    }*/


