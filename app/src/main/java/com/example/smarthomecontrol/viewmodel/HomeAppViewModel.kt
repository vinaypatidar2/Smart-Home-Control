package com.example.smarthomecontrol.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthomecontrol.HomeApp
import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel
import com.example.smarthomecontrol.viewmodel.structures.StructureViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class HomeAppViewModel(val homeApp: HomeApp) : ViewModel() {

    enum class NavigationTab { DEVICES }

    var selectedTab: MutableStateFlow<NavigationTab> = MutableStateFlow(NavigationTab.DEVICES)
    var selectedStructureVM: MutableStateFlow<StructureViewModel?> = MutableStateFlow(null)
    var selectedDeviceVM: MutableStateFlow<DeviceViewModel?> = MutableStateFlow(null)
    var structureVMs: MutableStateFlow<List<StructureViewModel>> = MutableStateFlow(mutableListOf())

    init {
        viewModelScope.launch {
            if (homeApp.permissionsManager.isSignedIn.value) {
                subscribeToStructures()
            }
            homeApp.permissionsManager.isSignedIn.collect { isSignedIn ->
                if (isSignedIn) {
                    subscribeToStructures()
                }
            }
        }
    }

    private fun subscribeToStructures() {
        viewModelScope.launch {
            homeApp.homeClient.structures().collect { structureSet ->
                val structureVMList = structureSet.map { StructureViewModel(it) }
                structureVMs.emit(structureVMList)

                if (selectedStructureVM.value == null && structureVMList.isNotEmpty()) {
                    selectedStructureVM.emit(structureVMList.first())
                }
            }
        }
    }
}