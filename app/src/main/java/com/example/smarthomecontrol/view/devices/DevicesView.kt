package com.example.smarthomecontrol.view.devices

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.googlehomeapisampleapp.R
import com.example.smarthomecontrol.R
//import com.example.googlehomeapisampleapp.view.shared.TabbedMenuView
import com.example.smarthomecontrol.viewmodel.HomeAppViewModel
import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel
import com.example.smarthomecontrol.viewmodel.structures.RoomViewModel
import com.example.smarthomecontrol.viewmodel.structures.StructureViewModel
import com.google.home.ConnectivityState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.google.home.Trait
import com.google.home.matter.standard.OnOff
import androidx.compose.material3.Switch

@Composable
fun DevicesAccountButton (homeAppVM: HomeAppViewModel) {
    IconButton(onClick = { homeAppVM.homeApp.permissionsManager.requestPermissions() },
            Modifier.size(48.dp).background(Color.Transparent)) {
        Icon(Icons.Default.AccountCircle,"", Modifier.fillMaxSize(), tint = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun DevicesView (homeAppVM: HomeAppViewModel) {
    val scope: CoroutineScope = rememberCoroutineScope()
    var expanded: Boolean by remember { mutableStateOf(false) }

    val structureVMs: List<StructureViewModel> = homeAppVM.structureVMs.collectAsState().value
    val selectedStructureVM: StructureViewModel? = homeAppVM.selectedStructureVM.collectAsState().value
    val structureName: String = selectedStructureVM?.name ?: stringResource(R.string.devices_structure_loading)

    Column (modifier = Modifier.fillMaxHeight()) {
        DevicesTopBar("", listOf { DevicesAccountButton(homeAppVM) })

        Box (modifier = Modifier.weight(1f)) {
            Column {
                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    var structureText: String = structureName

                    if (structureVMs.size > 1)
                        structureText += " ▾"

                    TextButton(onClick = { expanded = true }) {
                        Text(text = structureText, fontSize = 32.sp)
                    }
                }

                Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                    Box {
                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            for (structure in structureVMs) {
                                DropdownMenuItem(
                                    text = { Text(structure.name) },
                                    onClick = {
                                        scope.launch { homeAppVM.selectedStructureVM.emit(structure) }
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(weight = 1f, fill = false)) {
                    DeviceListComponent(homeAppVM)
                }
            }
        }


    }
}

@Composable
fun DeviceListItem (deviceVM: DeviceViewModel, homeAppVM: HomeAppViewModel) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val deviceStatus: String = deviceVM.status.collectAsState().value
    val traits: List<Trait> by deviceVM.traits.collectAsState()
    val isConnected: Boolean = (deviceVM.connectivity == ConnectivityState.ONLINE)

    Column (Modifier
        .padding(horizontal = 24.dp, vertical = 8.dp)
        .fillMaxWidth()

    ) {
        Text(deviceVM.name, fontSize = 20.sp)
        Text(deviceStatus, fontSize = 16.sp)
        val onOffTrait = traits.find { it is OnOff } as? OnOff
        if (onOffTrait != null) {
            Switch(
                checked = onOffTrait.onOff==true,
                onCheckedChange = { isChecked ->
                    scope.launch {
                        if (isConnected) {
                            if (isChecked) {
                                onOffTrait.on()
                            } else {
                                onOffTrait.off()
                            }
                        }
                    }
                },
                enabled = isConnected
            )
        }
    }
}

@Composable
fun RoomListItem (roomVM: RoomViewModel) {
    Column (Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {
        Text(roomVM.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun DeviceListComponent (homeAppVM: HomeAppViewModel) {

    val selectedStructureVM: StructureViewModel =
        homeAppVM.selectedStructureVM.collectAsState().value ?: return

    val selectedRoomVMs: List<RoomViewModel> =
        selectedStructureVM.roomVMs.collectAsState().value

//    val selectedDeviceVMsWithoutRooms: List<DeviceViewModel> =
//        selectedStructureVM.deviceVMsWithoutRooms.collectAsState().value


    for (roomVM in selectedRoomVMs) {
        RoomListItem(roomVM)

        val deviceVMsInRoom: List<DeviceViewModel> = roomVM.deviceVMs.collectAsState().value

        for (deviceVM in deviceVMsInRoom) {
            DeviceListItem(deviceVM, homeAppVM)
        }
    }

//    if (selectedDeviceVMsWithoutRooms.isNotEmpty()) {
//
//        Column (Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {
//            Text("Not in a room", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
//        }
//
//        for (deviceVM in selectedDeviceVMsWithoutRooms) {
//            DeviceListItem(deviceVM, homeAppVM)
//        }
//
//    }

}

@Composable
fun DevicesTopBar (title: String, buttons: List<@Composable () -> Unit>) {
    Box (Modifier.height(64.dp).fillMaxWidth().padding(horizontal = 16.dp)) {
        Row (Modifier.height(64.dp).fillMaxWidth().background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
            Text(title, fontSize = 24.sp)
        }

        Row (Modifier.height(64.dp).fillMaxWidth().background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End) {
            for (button in buttons) {
                button()
            }
        }
    }
}
