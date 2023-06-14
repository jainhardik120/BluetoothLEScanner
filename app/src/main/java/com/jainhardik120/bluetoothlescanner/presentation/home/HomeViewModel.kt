package com.jainhardik120.bluetoothlescanner.presentation.home

import android.bluetooth.BluetoothDevice
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jainhardik120.bluetoothlescanner.domain.BluetoothController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bluetoothController: BluetoothController
)  : ViewModel() {

    private val _state = MutableStateFlow(DevicesState())
    val state = combine(
        bluetoothController.scannedDevices,
        _state
    ) { scannedDevices, state ->
        state.copy(
            scannedDevices = scannedDevices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init{
        startScan()
    }

    fun startScan() {
        bluetoothController.startDiscovery()
    }
    fun stopScan() {
        bluetoothController.stopDiscovery()
    }

}

data class DevicesState(
    val scannedDevices: List<BluetoothDevice> = emptyList(),
)