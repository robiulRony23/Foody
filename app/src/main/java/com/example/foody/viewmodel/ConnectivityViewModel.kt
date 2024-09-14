package com.example.foody.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.foody.data.network.ConnectivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConnectivityViewModel @Inject constructor(private val connectivityRepository: ConnectivityRepository) : ViewModel() {

    val isOnline = connectivityRepository.isConnected.asLiveData()
}