package com.daily.app.ui.activities.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.daily.app.R
import com.daily.app.common.AppPreferences
import com.daily.app.common.utils.AppUtils
import com.daily.app.common.utils.LocationUtils
import com.daily.app.domain.model.AppConfig
import com.daily.app.ui.activities.main.top.TopNewsFragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermissions()
    }

    private fun requestPermissions() {
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {

            initAppPreferences()
        }

        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
    }

    private fun initAppPreferences() {

        var code = Locale.getDefault().country
        var city = LocationUtils.getDefaultCity(code)!!
        var lang = Locale.getDefault().language

        LocationUtils.getCurrentLocation(this)?.let {
            LocationUtils.getAddress(this, it.latitude, it.longitude)?.let { address ->
                code = address.countryCode
                city = address.locality
            }
        }

        val prefJson = Gson().toJson(AppConfig("WORLD", code, city, lang, 50.toString()))
        AppPreferences.setDefaultPreference(this, prefJson)

        initFragments()
    }

    private fun initFragments() {
//        AppUtils.replaceFragment(this, WorldNewsFragment(), R.id.fragment_view)
            AppUtils.replaceFragment(this, TopNewsFragment(), R.id.fragment_view)
    }
}