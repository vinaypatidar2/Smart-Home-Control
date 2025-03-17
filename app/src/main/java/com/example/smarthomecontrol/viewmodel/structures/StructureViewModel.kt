package com.example.smarthomecontrol.viewmodel.structures

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
//import com.example.googlehomeapisampleapp.viewmodel.automations.AutomationViewModel
//import com.example.googlehomeapisampleapp.viewmodel.automations.CandidateViewModel
import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel
import com.google.home.Structure
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StructureViewModel (val structure: Structure) : ViewModel() {

    var id : String
    var name : String

    val roomVMs : MutableStateFlow<List<RoomViewModel>>
    val deviceVMs : MutableStateFlow<List<DeviceViewModel>>
    val deviceVMsWithoutRooms : MutableStateFlow<List<DeviceViewModel>>
//    val automationVMs : MutableStateFlow<List<AutomationViewModel>>

    init {
        // Initialize permanent values for a structure:
        id = structure.id.id
        name = structure.name

        // Initialize dynamic values for a structure:
        roomVMs = MutableStateFlow(mutableListOf())
        deviceVMs = MutableStateFlow(mutableListOf())
        deviceVMsWithoutRooms = MutableStateFlow(mutableListOf())
//        automationVMs = MutableStateFlow(mutableListOf())

        // Subscribe to changes on dynamic values:
        viewModelScope.launch { subscribeToRooms() }
        viewModelScope.launch { subscribeToDevices() }
//        viewModelScope.launch { subscribeToAutomations() }
    }

    private suspend fun subscribeToRooms() {
        // Subscribe to changes on rooms:
        structure.rooms().collect { roomSet ->
            val roomVMs = mutableListOf<RoomViewModel>()
            // Store rooms in container ViewModels:
            for (room in roomSet) {
                roomVMs.add(RoomViewModel(room))
            }
            // Store the ViewModels:
            this.roomVMs.emit(roomVMs)
        }
    }

    private suspend fun subscribeToDevices() {
        // Subscribe to changes on devices:
        structure.devices().collect { deviceSet ->
            val deviceVMs = mutableListOf<DeviceViewModel>()
            val deviceWithoutRoomVMs = mutableListOf<DeviceViewModel>()
            // Store devices in container ViewModels:
            for (device in deviceSet) {
                val deviceVM = DeviceViewModel(device)
                deviceVMs.add(deviceVM)
                // For any device that's not in a room, additionally keep track of a separate list:
                if (!device.isInRoom)
                    deviceWithoutRoomVMs.add(deviceVM)
            }
            // Store the ViewModels:
            this.deviceVMs.emit(deviceVMs)
            deviceVMsWithoutRooms.emit(deviceWithoutRoomVMs)
        }
    }

//    private suspend fun subscribeToAutomations() {
//        // Subscribe to changes on automations:
//        structure.automations().collect { automationSet ->
//            val automationVMs = mutableListOf<AutomationViewModel>()
//            // Store automations in container ViewModels:
//            for (automation in automationSet) {
//                automationVMs.add(AutomationViewModel(automation))
//            }
//            // Store the ViewModels:
//            this.automationVMs.emit(automationVMs)
//        }
//    }
}
