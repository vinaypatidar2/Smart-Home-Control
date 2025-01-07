
/* Copyright 2025 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.example.googlehomeapisampleapp.viewmodel.structures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlehomeapisampleapp.viewmodel.devices.DeviceViewModel
import com.google.home.Room
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RoomViewModel (val room: Room) : ViewModel() {

    var id : String
    var name : String

    val deviceVMs : MutableStateFlow<List<DeviceViewModel>>

    init {
        // Initialize permanent values for a room:
        id = room.id.id
        name = room.name

        // Initialize dynamic values for a structure:
        deviceVMs = MutableStateFlow(mutableListOf())

        viewModelScope.launch {
            // Subscribe to changes on dynamic values:
            launch { subscribeToDevices() }
        }
    }

    private suspend fun subscribeToDevices() {
        // Subscribe to changes on devices:
        room.devices().collect { deviceSet ->
            val deviceVMs = mutableListOf<DeviceViewModel>()
            // Store devices in container ViewModels:
            for (device in deviceSet) {
                deviceVMs.add(DeviceViewModel(device))
            }
            // Store the ViewModels:
            this.deviceVMs.emit(deviceVMs)
        }
    }
}