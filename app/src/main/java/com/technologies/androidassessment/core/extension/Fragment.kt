package com.technologies.androidassessment.core.extension

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.goToActivity(mClass: Class<*>, finishCurrentActivity: Boolean, extras: Bundle? = null,
                          withResult: Boolean = false, requestCode: Int = -1) {
    val intent = Intent(context, mClass)
    extras?.apply { intent.putExtras(this) }
    if (!withResult)
        this.startActivity(intent)
    else
        this.startActivityForResult(intent, requestCode)

    if (finishCurrentActivity)
        activity?.finish()

}