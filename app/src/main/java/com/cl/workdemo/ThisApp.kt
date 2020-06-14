package com.cl.workdemo

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class ThisApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
    }
}