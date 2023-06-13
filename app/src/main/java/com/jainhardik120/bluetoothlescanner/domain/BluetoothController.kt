package com.jainhardik120.bluetoothlescanner.domain

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattService
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {

    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val gattServices :StateFlow<List<BluetoothGattService>>

    fun startDiscovery()
    fun stopDiscovery()
    fun release()

    fun connectGattDevice(address : String) : Boolean

}