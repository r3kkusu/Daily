package com.daily.app.ui.activities

import androidx.lifecycle.ViewModel
import com.daily.app.domain.repositories.AppRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepo: AppRepo
) : ViewModel() {

    var job : Job? = null

}