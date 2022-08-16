package com.daily.app.ui.activities.main

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import butterknife.BindView
import butterknife.ButterKnife
import com.daily.app.R
import com.daily.app.common.AppPreferences
import com.daily.app.common.utils.LocationUtils
import com.daily.app.domain.model.AppConfig
import com.daily.app.ui.activities.main.local.LocalNewsFragment
import com.daily.app.ui.activities.main.search.SearchNewsFragment
import com.daily.app.ui.activities.main.top.TopNewsFragment
import com.daily.app.ui.activities.main.world.WorldNewsFragment
import com.daily.app.ui.adapters.PageAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    @BindView(R.id.viewPager)
    lateinit var fragmentViewPager: ViewPager2

    @BindView(R.id.navigation_view)
    lateinit var navigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

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

        val fragments: ArrayList<Fragment> = arrayListOf(
            TopNewsFragment(),
            WorldNewsFragment(),
            LocalNewsFragment(),
            SearchNewsFragment()
        )

        var navigationId = R.id.top
        val pageAdapter = PageAdapter(this, fragments)

        fragmentViewPager.adapter = pageAdapter
        fragmentViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                Log.d(TAG, "onPageSelected: $position")
                when(position) {
                    0 -> {
                        navigationId = R.id.top
                        navigationView.menu.getItem(0).isChecked = true
                    }
                    1 -> {
                        navigationId = R.id.world
                        navigationView.menu.getItem(1).isChecked = true
                    }
                    2 -> {
                        navigationId = R.id.local
                        navigationView.menu.getItem(2).isChecked = true
                    }
                    3 -> {
                        navigationId = R.id.search
                        navigationView.menu.getItem(3).isChecked = true
                    }
                }
            }
        })

        navigationView.setOnItemSelectedListener {
            if (it.itemId != navigationId) {
                navigationId = it.itemId
                when(navigationId) {
                    R.id.top -> fragmentViewPager.currentItem = 0
                    R.id.world -> fragmentViewPager.currentItem = 1
                    R.id.local -> fragmentViewPager.currentItem = 2
                    R.id.search -> fragmentViewPager.currentItem = 3
                }
            }

            Log.d(TAG, "initFragments: navigationId = $navigationId")

            return@setOnItemSelectedListener true
        }
    }
}