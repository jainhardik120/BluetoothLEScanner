package com.jainhardik120.bluetoothlescanner.presentation.home

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import javax.inject.Inject

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: (deviceAddress: String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    Column(Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(1f), content = {
                itemsIndexed(state.scannedDevices) { index, device ->
                    BluetoothDeviceItem(item = device, onClick = {
                        onNavigate(device.address)
                    })
                    Divider()
                }
            })
        Row(Modifier.fillMaxWidth()) {
            Button(onClick = { viewModel.startScan() }) {
                Text(text = "Start Scan")
            }
            Button(onClick = { viewModel.stopScan() }) {
                Text(text = "Stop Scan")
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun BluetoothDeviceItem(item: BluetoothDevice, onClick: () -> Unit) {
    Surface(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Column {
            Text(text = "Address : ${item.address ?: ""}")
            Text(text = "Name : ${item.name ?: ""}")
            Text(
                text = "Bond State : ${
                    when (item.bondState) {
                        BluetoothDevice.BOND_BONDED -> "Paired"
                        BluetoothDevice.BOND_BONDING -> "Pairing"
                        BluetoothDevice.BOND_NONE -> "Not Paired"
                        else -> {""}
                    }
                }"
            )
        }
    }
}