package com.example.smarthomecontrol.viewmodel.structures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel
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

        // Subscribe to changes on dynamic values:
        viewModelScope.launch { subscribeToDevices() }
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