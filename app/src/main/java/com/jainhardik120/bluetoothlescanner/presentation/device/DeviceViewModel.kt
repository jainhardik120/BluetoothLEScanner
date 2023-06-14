package com.jainhardik120.bluetoothlescanner.presentation.device

import android.bluetooth.BluetoothGattService
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jainhardik120.bluetoothlescanner.domain.BluetoothController
import com.jainhardik120.bluetoothlescanner.domain.MBluetoothGattCharacteristic
import com.jainhardik120.bluetoothlescanner.domain.MBluetoothGattService
import com.jainhardik120.bluetoothlescanner.presentation.home.DevicesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val bluetoothController: BluetoothController
) : ViewModel() {

    private val _state = MutableStateFlow(DeviceScreenState())
    val state = combine(
        bluetoothController.gattServices,
        _state
    ) { gattServices, state ->
        state.copy(
            services = gattServices
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _state.value)

    init {
        val address = savedStateHandle.get<String>("deviceAddress")?:""
        _state.update {
            it.copy(address = address)
        }
        bluetoothController.connectGattDevice(address)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("TAG", "onCleared: Cleared")
    }
}

data class DeviceScreenState(
    val address : String = "",
    val services : List<MBluetoothGattService> = emptyList()
)