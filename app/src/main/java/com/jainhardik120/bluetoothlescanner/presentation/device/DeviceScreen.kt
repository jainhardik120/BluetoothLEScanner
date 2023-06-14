package com.jainhardik120.bluetoothlescanner.presentation.device

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.jainhardik120.bluetoothlescanner.domain.MBluetoothGattService

@Composable
fun DeviceScreen(viewModel: DeviceViewModel) {
    val state by viewModel.state.collectAsState()

    LazyColumn(content = {
        itemsIndexed(state.services) { index, item ->
            GattServiceView(item = item)
            Divider()
        }
    })
}

@Composable
fun GattServiceView(item: MBluetoothGattService) {
    var expanded by remember { mutableStateOf(false) }
    Surface(
        Modifier
            .fillMaxWidth()
            .clickable {
                expanded = !expanded
            }) {
        Column {
            Text(text = item.serviceType)
            Text(text = "UUID : ${item.uuid}")
        }
    }
    if (expanded) {
        Column(content = {
            item.characteristics.forEach { bluetoothGattCharacteristic ->
                Text(text = bluetoothGattCharacteristic.uuid)
                Text(text = bluetoothGattCharacteristic.properties)
                Text(text = bluetoothGattCharacteristic.permission)
            }
        })
    }
}