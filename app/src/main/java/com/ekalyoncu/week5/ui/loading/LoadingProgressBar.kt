package com.ekalyoncu.week5.ui.loading

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import com.ekalyoncu.week5.R

class LoadingProgressBar(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_loading)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setCancelable(false)
    }
}