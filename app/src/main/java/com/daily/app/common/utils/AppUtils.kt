package com.daily.app.common.utils

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

class AppUtils {
    companion object {
        val TAG = "AppUtils"

        fun replaceFragment(activity: FragmentActivity, fragment: Fragment, fragmentView: Int) {
            Log.d(TAG, "replaceFragment: " + fragment.javaClass.name)
            val transaction = activity.supportFragmentManager
                .beginTransaction()
            transaction.replace(fragmentView, fragment)
            transaction.commit()
        }

        fun urlGrooming(url: String) : String {
            var groomUrl: String = if (url.contains("https://www.")) {
                url.substringAfter("https://www.")
            } else if (url.contains("http://www.")) {
                url.substringAfter("http://www.")
            } else {
                url.substringAfter("http://")
            }
            return groomUrl
        }
    }
}