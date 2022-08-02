package com.daily.app.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.daily.app.R
import com.daily.app.common.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getArticles().observe(this) {
            Log.d(TAG, "onCreate: $it")
            when(it) {
                is Resource.Success -> {
                }
                is Resource.Error -> {
                }
                is Resource.Loading -> {
                }
            }

        }
    }
}