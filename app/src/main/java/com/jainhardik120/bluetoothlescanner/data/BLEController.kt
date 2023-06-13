package com.jainhardik120.bluetoothlescanner.data

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.jainhardik120.bluetoothlescanner.domain.BluetoothController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


@SuppressLint("MissingPermission")
class BLEController(private val context: Context) : BluetoothController {

    private val TAG = "BLEController"

    private var bluetoothGatt: BluetoothGatt? = null

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val bluetoothLeScanner by lazy {
        bluetoothAdapter?.bluetoothLeScanner
    }

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    private val _scannedDevices = MutableStateFlow<List<BluetoothDevice>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDevice>>
        get() = _scannedDevices.asStateFlow()

    private val _gattServices = MutableStateFlow<List<BluetoothGattService>>(emptyList())
    override val gattServices: StateFlow<List<BluetoothGattService>>
        get() = _gattServices.asStateFlow()

    private fun handleResult(result : ScanResult?){
        if(result==null){
            return;
        }
        val device = result.device
        _scannedDevices.update {devices->
            if(device in devices) devices else{
                devices + device
            }
        }
    }

    private val scanCallback = object : ScanCallback(){
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            super.onScanResult(callbackType, result)
            handleResult(result)
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>?) {
            super.onBatchScanResults(results)
            results?.forEach {
                result->
                handleResult(result)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.d(TAG, "onScanFailed: Scan Failed with code ${errorCode}")
        }
    }

    private val bluetoothGattCallback = object : BluetoothGattCallback(){
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            super.onConnectionStateChange(gatt, status, newState)
            bluetoothGatt = gatt
            if(newState== BluetoothProfile.STATE_CONNECTED){

                bluetoothGatt?.discoverServices()
            }else if(newState== BluetoothProfile.STATE_DISCONNECTED){

            }
        }

        override fun onServicesDiscovered(gatt: BluetoothGatt?, status: Int) {
            super.onServicesDiscovered(gatt, status)
            if(status== BluetoothGatt.GATT_SUCCESS){
                _gattServices.update {it
                    bluetoothGatt?.services ?: it
                }
            }
        }
    }


    override fun startDiscovery() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }
        bluetoothLeScanner?.startScan(scanCallback)
    }

    override fun stopDiscovery() {
        if(!hasPermission(Manifest.permission.BLUETOOTH_SCAN)){
            return
        }
        bluetoothLeScanner?.stopScan(scanCallback)
    }

    override fun release() {

    }

    override fun connectGattDevice(address: String) : Boolean{
        bluetoothAdapter?.let { adapter ->
            try {
                val device = adapter.getRemoteDevice(address)
                device.connectGatt(context, false, bluetoothGattCallback)
                return true;
            } catch (exception: Exception) {
                Log.w(TAG, "Device not found with provided address.")
                return false
            }
        }?:return false
    }
}