package com.jainhardik120.bluetoothlescanner.domain

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService

data class MBluetoothGattService(
    val uuid: String,
    val serviceType : String,
    val characteristics : List<MBluetoothGattCharacteristic>
)

data class MBluetoothGattCharacteristic(
    val uuid: String,
    val properties : String,
    val permission: String
)

fun BluetoothGattService.toMBluetoothGattService(): MBluetoothGattService{
    return MBluetoothGattService(
        uuid = this.uuid.toString(),
        serviceType = when (this.type) {
            BluetoothGattService.SERVICE_TYPE_PRIMARY -> "Primary Service"
            BluetoothGattService.SERVICE_TYPE_SECONDARY -> "Secondary Service"
            else -> ""
        },
        characteristics = this.characteristics.map { 
            it.toMBluetoothGattCharacteristic()
        }
    )
}

fun BluetoothGattCharacteristic.toMBluetoothGattCharacteristic() : MBluetoothGattCharacteristic{
    return MBluetoothGattCharacteristic(
        uuid = this.uuid.toString(),
        properties = StringBuilder().apply {
            val properties = this@toMBluetoothGattCharacteristic.properties
            if (properties and BluetoothGattCharacteristic.PROPERTY_BROADCAST != 0) {
                this.append("Broadcast")
            }
            if (properties and BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS != 0) {
                if (this.isNotEmpty()) {
                    this.append(", ")
                }
                this.append("Extended Properties")
            }
            if (properties and BluetoothGattCharacteristic.PROPERTY_INDICATE != 0) {
                if (this.isNotEmpty()) {
                    this.append(", ")
                }
                this.append("Indicate")
            }
            if (properties and BluetoothGattCharacteristic.PROPERTY_NOTIFY != 0) {
                if (this.isNotEmpty()) {
                    this.append(", ")
                }
                this.append("Notify")
            }
            if (properties and BluetoothGattCharacteristic.PROPERTY_READ != 0) {
                if (this.isNotEmpty()) {
                    this.append(", ")
                }
                this.append("Read")
            }
            if (properties and BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE != 0) {
                if (this.isNotEmpty()) {
                    this.append(", ")
                }
                this.append("Signed Write")
            }
            if (properties and BluetoothGattCharacteristic.PROPERTY_WRITE != 0) {
                if (this.isNotEmpty()) {
                    this.append(", ")
                }
                this.append("Write")
            }
            if (properties and BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE != 0) {
                if (this.isNotEmpty()) {
                    this.append(", ")
                }
                this.append("Write No Response")
            }
        }.toString(),
        permission = StringBuilder().apply {
            val permissions = this@toMBluetoothGattCharacteristic.permissions
            if (permissions and BluetoothGattCharacteristic.PERMISSION_READ != 0) {
                this.append("Read ")
            }
            if (permissions and BluetoothGattCharacteristic.PERMISSION_WRITE != 0) {
                this.append("Write ")
            }
            if (permissions and BluetoothGattCharacteristic.PERMISSION_WRITE_SIGNED != 0) {
                this.append("Write Signed ")
            }
            if (permissions and BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED != 0) {
                this.append("Write Encrypted ")
            }
            if (permissions and BluetoothGattCharacteristic.PERMISSION_WRITE_ENCRYPTED_MITM != 0) {
                this.append("Write Encrypted with Man-in-the-Middle Protection ")
            }
            if (permissions and BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED != 0) {
                this.append("Read Encrypted ")
            }
            if (permissions and BluetoothGattCharacteristic.PERMISSION_READ_ENCRYPTED_MITM != 0) {
                this.append("Read Encrypted with Man-in-the-Middle Protection ")
            }
        }.toString()
    )
}
