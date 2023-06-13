package com.jainhardik120.bluetoothlescanner.presentation.device

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun DeviceScreen(viewModel: DeviceViewModel) {
    val state by viewModel.state.collectAsState()

    LazyColumn(content = {
        itemsIndexed(state.services){index, item ->
            Text(text = "${item.uuid?:""}")
        }
    })
}