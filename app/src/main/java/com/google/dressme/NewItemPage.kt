package com.google.dressme

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.fragment.app.Fragment


class NewItemPage(
    private var label: String,
    private var color: Color
) : Fragment() {
    private lateinit var mActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity as MainActivity)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            mActivity.replaceFragment(CameraView())
        }


    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.d("ASDASD",label)
        val view = inflater.inflate(R.layout.fragment_new_item_page, container, false)
        view?.findViewById<TextView>(R.id.label)?.text = label
        val newPiece=view.findViewById<ImageView>(R.id.dressView)
        val dressColor=color.toArgb()
        newPiece.setColorFilter(dressColor,PorterDuff.Mode.MULTIPLY)

        val colorName=ColorUtils().getColorNameFromRgb(dressColor.red,dressColor.green,dressColor.blue)
        view?.findViewById<TextView>(R.id.colorName)?.text=colorName




        return view

    }

}