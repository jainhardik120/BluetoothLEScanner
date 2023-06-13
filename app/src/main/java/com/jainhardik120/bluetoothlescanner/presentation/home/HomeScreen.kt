package com.jainhardik120.bluetoothlescanner.presentation.home

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import javax.inject.Inject

@Composable
fun HomeScreen (viewModel: HomeViewModel = hiltViewModel(), onNavigate: (deviceAddress: String)->Unit) {
    val state by viewModel.state.collectAsState()
    Column(Modifier.fillMaxSize()) {

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(1f), content = {
            itemsIndexed(state.scannedDevices) { index, device ->
                Text(text = device.address ?: "null",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onNavigate(device.address)
                        }
                        .padding(16.dp))
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