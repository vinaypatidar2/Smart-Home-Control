//package com.example.smarthomecontrol.view.devices
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
////import com.example.googlehomeapisampleapp.R
//import com.example.smarthomecontrol.R
////import com.example.googlehomeapisampleapp.view.shared.TabbedMenuView
//import com.example.smarthomecontrol.viewmodel.HomeAppViewModel
//import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel
//import com.example.smarthomecontrol.viewmodel.structures.RoomViewModel
//import com.example.smarthomecontrol.viewmodel.structures.StructureViewModel
//import com.google.home.ConnectivityState
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//import com.google.home.Trait
//import com.google.home.matter.standard.OnOff
//import androidx.compose.material3.Switch
//import kotlinx.coroutines.delay
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.mutableStateListOf
//import android.util.Log
//import android.widget.Toast
//import androidx.camera.view.PreviewView
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import com.example.smarthomecontrol.BlinkDetectionHelper
//
//
//
//
//
//@Composable
//fun DevicesAccountButton (homeAppVM: HomeAppViewModel) {
//    IconButton(onClick = { homeAppVM.homeApp.permissionsManager.requestPermissions() },
//            Modifier.size(48.dp).background(Color.Transparent)) {
//        Icon(Icons.Default.AccountCircle,"", Modifier.fillMaxSize(), tint = MaterialTheme.colorScheme.primary)
//    }
//}
//
//
//@Composable
//fun DevicesView(homeAppVM: HomeAppViewModel) {
//    val scope: CoroutineScope = rememberCoroutineScope()
//    var expandedStructure: Boolean by remember { mutableStateOf(false) }
//    var expandedRoom: Boolean by remember { mutableStateOf(false) }
//
//    val structureVMs: List<StructureViewModel> = homeAppVM.structureVMs.collectAsState().value
//    val selectedStructureVM: StructureViewModel? = homeAppVM.selectedStructureVM.collectAsState().value
//    val structureName: String = selectedStructureVM?.name ?: stringResource(R.string.devices_structure_loading)
//
//    val roomVMs: List<RoomViewModel> = selectedStructureVM?.roomVMs?.collectAsState()?.value ?: listOf()
//    var selectedRoom: RoomViewModel? by remember { mutableStateOf(roomVMs.firstOrNull()) }
//    if (roomVMs.isNotEmpty() && selectedRoom == null) {
//        selectedRoom = roomVMs.first()
//    }
//
//    val highlightStates = remember { mutableStateListOf<Boolean>().apply { repeat(roomVMs.size) { add(false) } } }
//
//
//
//    Column(modifier = Modifier.fillMaxHeight()) {
//        DevicesTopBar("", listOf { DevicesAccountButton(homeAppVM) })
//
//        Box(modifier = Modifier.weight(1f)) {
//            Column {
//                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
//                    var structureText: String = structureName
//                    if (structureVMs.size > 1) structureText += " ▾"
//                    TextButton(onClick = { expandedStructure = true }) {
//                        Text(text = structureText, fontSize = 32.sp)
//                    }
//                }
//                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
//                    var roomText: String = selectedRoom?.name ?: "Select Room"
//                    TextButton(onClick = { expandedRoom = true }) {
//                        Text(text = roomText, fontSize = 24.sp)
//                    }
//                }
//                DropdownMenu(expanded = expandedStructure, onDismissRequest = { expandedStructure = false }) {
//                    for (structure in structureVMs) {
//                        DropdownMenuItem(text = { Text(structure.name) }, onClick = { scope.launch { homeAppVM.selectedStructureVM.emit(structure) }; expandedStructure = false })
//                    }
//                }
//                DropdownMenu(expanded = expandedRoom, onDismissRequest = { expandedRoom = false }) {
//                    for (room in roomVMs) {
//                        DropdownMenuItem(text = { Text(room.name) }, onClick = { selectedRoom = room; expandedRoom = false })
//                    }
//                }
//                Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(weight = 1f, fill = false)) {
//                    if (selectedRoom != null) {
//                        DeviceListComponent(homeAppVM, selectedRoom!!, highlightStates)
//                    }
//                }
//                Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(weight = 1f, fill = false)) {
//                    if (selectedRoom != null) {
//                        BlinkDetectionComponent(selectedRoom!!, highlightStates)
//                    }
//                }
////                BlinkDetectionComponent(selectedRoom!!, highlightStates)
//            }
//        }
//    }
//}
//
//
//
///**
////@Composable
////fun DevicesView(homeAppVM: HomeAppViewModel) {
////    val scope: CoroutineScope = rememberCoroutineScope()
////    var expandedStructure: Boolean by remember { mutableStateOf(false) }
////    var expandedRoom: Boolean by remember { mutableStateOf(false) }
////
////    val structureVMs: List<StructureViewModel> = homeAppVM.structureVMs.collectAsState().value
////    val selectedStructureVM: StructureViewModel? = homeAppVM.selectedStructureVM.collectAsState().value
////    val structureName: String = selectedStructureVM?.name ?: stringResource(R.string.devices_structure_loading)
////
////    // Room selection (Always show the first room if none selected)
////    val roomVMs: List<RoomViewModel> = selectedStructureVM?.roomVMs?.collectAsState()?.value ?: listOf()
////
////    // Ensure that the first room is selected by default when the room data is loaded
////    var selectedRoom: RoomViewModel? by remember {
////        mutableStateOf(roomVMs.firstOrNull())
////    }
////
////    // Whenever the rooms are updated, set the first room as the default if no room is selected
////    if (roomVMs.isNotEmpty() && selectedRoom == null) {
////        selectedRoom = roomVMs.first()
////    }
////
////    Column(modifier = Modifier.fillMaxHeight()) {
////        DevicesTopBar("", listOf { DevicesAccountButton(homeAppVM) })
////
////        Box(modifier = Modifier.weight(1f)) {
////            Column {
////                // Structure selection
////                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
////                    var structureText: String = structureName
////                    if (structureVMs.size > 1) structureText += " ▾"
////
////                    TextButton(onClick = { expandedStructure = true }) {
////                        Text(text = structureText, fontSize = 32.sp)
////                    }
////                }
////
////                // Room selection
////                Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
////                    var roomText: String = selectedRoom?.name ?: "Select Room"
////
////                    TextButton(onClick = { expandedRoom = true }) {
////                        Text(text = roomText, fontSize = 24.sp)
////                    }
////                }
////
////                // Dropdown for structure
////                DropdownMenu(expanded = expandedStructure, onDismissRequest = { expandedStructure = false }) {
////                    for (structure in structureVMs) {
////                        DropdownMenuItem(
////                            text = { Text(structure.name) },
////                            onClick = {
////                                scope.launch { homeAppVM.selectedStructureVM.emit(structure) }
////                                expandedStructure = false
////                            }
////                        )
////                    }
////                }
////
////                // Dropdown for room
////                DropdownMenu(expanded = expandedRoom, onDismissRequest = { expandedRoom = false }) {
////                    for (room in roomVMs) {
////                        DropdownMenuItem(
////                            text = { Text(room.name) },
////                            onClick = {
////                                selectedRoom = room
////                                expandedRoom = false
////                            }
////                        )
////                    }
////                }
////
////                // Display devices for the selected room
////                Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(weight = 1f, fill = false)) {
////                    if (selectedRoom != null) {
////                        DeviceListComponent(homeAppVM, selectedRoom!!)
////                    }
////                }
////                BlinkDetectionComponent()
////            }
////        }
////    }
////}
//
////@Composable
////fun DeviceListItem (deviceVM: DeviceViewModel, homeAppVM: HomeAppViewModel) {
////    val scope: CoroutineScope = rememberCoroutineScope()
////    val deviceStatus: String = deviceVM.status.collectAsState().value
////    val traits: List<Trait> by deviceVM.traits.collectAsState()
////    val isConnected: Boolean = (deviceVM.connectivity == ConnectivityState.ONLINE)
////
////    Column (Modifier
////        .padding(horizontal = 24.dp, vertical = 8.dp)
////        .fillMaxWidth()
////
////    ) {
////        Text(deviceVM.name, fontSize = 20.sp)
////        Text(deviceStatus, fontSize = 16.sp)
////        val onOffTrait = traits.find { it is OnOff } as? OnOff
////        if (onOffTrait != null) {
////            Switch(
////                checked = onOffTrait.onOff==true,
////                onCheckedChange = { isChecked ->
////                    scope.launch {
////                        if (isConnected) {
////                            if (isChecked) {
////                                onOffTrait.on()
////                            } else {
////                                onOffTrait.off()
////                            }
////                        }
////                    }
////                },
////                enabled = isConnected
////            )
////        }
////    }
////}
//*/
//
//@Composable
//fun DeviceListItem(deviceVM: DeviceViewModel, homeAppVM: HomeAppViewModel, isHighlighted: Boolean) {
//    val scope: CoroutineScope = rememberCoroutineScope()
//    val deviceStatus: String = deviceVM.status.collectAsState().value
//    val traits: List<Trait> by deviceVM.traits.collectAsState()
//    val isConnected: Boolean = (deviceVM.connectivity == ConnectivityState.ONLINE)
//
//    val backgroundColor = if (isHighlighted) Color.Yellow else Color.Transparent
//
//    Column(
//        Modifier
//            .padding(horizontal = 24.dp, vertical = 8.dp)
//            .fillMaxWidth()
//            .background(backgroundColor) // Apply the background color for highlighting
//    ) {
//        Text(deviceVM.name, fontSize = 20.sp)
//        Text(deviceStatus, fontSize = 16.sp)
//        val onOffTrait = traits.find { it is OnOff } as? OnOff
//        if (onOffTrait != null) {
//            Switch(
//                checked = onOffTrait.onOff == true,
//                onCheckedChange = { isChecked ->
//                    if (isConnected) {
//                        // Use a coroutine to call suspend functions on and off
//                        scope.launch {
//                            if (isChecked) {
//                                onOffTrait.on() // Calling suspend function from coroutine
//                            } else {
//                                onOffTrait.off() // Calling suspend function from coroutine
//                            }
//                        }
//                    }
//                },
//                enabled = isConnected
//            )
//        }
//    }
//}
//
//
//
////@Composable
////fun RoomListItem (roomVM: RoomViewModel) {
////    Column (Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {
////        Text(roomVM.name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
////    }
////}
//
//@Composable
//fun DeviceListComponent(homeAppVM: HomeAppViewModel, selectedRoom: RoomViewModel, highlightStates: MutableList<Boolean>) {
//    val deviceVMsInRoom: List<DeviceViewModel> = selectedRoom?.deviceVMs?.collectAsState()?.value ?: listOf()
//    if (highlightStates.size != deviceVMsInRoom.size) {
//        highlightStates.clear()
//        highlightStates.addAll(List(deviceVMsInRoom.size) { false })
//    }
//
//    for (index in deviceVMsInRoom.indices) {
//        DeviceListItem(deviceVMsInRoom[index], homeAppVM, highlightStates[index])
//    }
//}
//
//
//
//
//
//
//
//
////@Composable
////fun DeviceListComponent (homeAppVM: HomeAppViewModel,  selectedRoom: RoomViewModel) {
////    val deviceVMsInRoom: List<DeviceViewModel> = selectedRoom.deviceVMs.collectAsState().value
////
//////    val selectedStructureVM: StructureViewModel =
//////        homeAppVM.selectedStructureVM.collectAsState().value ?: return
//////
//////    val selectedRoomVMs: List<RoomViewModel> =
//////        selectedStructureVM.roomVMs.collectAsState().value
////
//////    val selectedDeviceVMsWithoutRooms: List<DeviceViewModel> =
//////        selectedStructureVM.deviceVMsWithoutRooms.collectAsState().value
////
////    for (deviceVM in deviceVMsInRoom) {
////        DeviceListItem(deviceVM, homeAppVM)
////    }
////
//////    for (roomVM in selectedRoomVMs) {
//////        RoomListItem(roomVM)
//////
//////        val deviceVMsInRoom: List<DeviceViewModel> = roomVM.deviceVMs.collectAsState().value
//////
//////        for (deviceVM in deviceVMsInRoom) {
//////            DeviceListItem(deviceVM, homeAppVM)
//////        }
//////    }
////
//////    if (selectedDeviceVMsWithoutRooms.isNotEmpty()) {
//////
//////        Column (Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {
//////            Text("Not in a room", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
//////        }
//////
//////        for (deviceVM in selectedDeviceVMsWithoutRooms) {
//////            DeviceListItem(deviceVM, homeAppVM)
//////        }
//////
//////    }
////
////}
//
//@Composable
//fun DevicesTopBar (title: String, buttons: List<@Composable () -> Unit>) {
//    Box (Modifier.height(64.dp).fillMaxWidth().padding(horizontal = 16.dp)) {
//        Row (Modifier.height(64.dp).fillMaxWidth().background(Color.Transparent),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center) {
//            Text(title, fontSize = 24.sp)
//        }
//
//        Row (Modifier.height(64.dp).fillMaxWidth().background(Color.Transparent),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.End) {
//            for (button in buttons) {
//                button()
//            }
//        }
//    }
//}
//
//
//@Composable
//fun BlinkDetectionComponent(selectedRoom: RoomViewModel?, highlightStates: MutableList<Boolean>) {
//
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val previewView = remember { PreviewView(context) }
//    val deviceVMsInRoom: List<DeviceViewModel> = selectedRoom?.deviceVMs?.collectAsState()?.value ?: listOf()
////    val highlightStates = remember(deviceVMsInRoom.size) { mutableStateListOf<Boolean>().apply { repeat(deviceVMsInRoom.size) { add(false) } } }
//    var highlightedIndex by remember { mutableStateOf(-1) }
//
//    LaunchedEffect(deviceVMsInRoom) {
//        for (index in deviceVMsInRoom.indices) {
//            delay(3000L * index) // Delay for each device to highlight one by one
//            highlightStates[index] = true
//            highlightedIndex = index
//        }
//    }
//    val blinkDetectionHelper = remember {
//        BlinkDetectionHelper(context, previewView, object : BlinkDetectionHelper.BlinkListener {
//            override fun onBlinkDetected() {
//                if (highlightedIndex in deviceVMsInRoom.indices && deviceVMsInRoom.isNotEmpty()) { // Check for empty list
//                    Toast.makeText(context, "Blink Detected: ${deviceVMsInRoom[highlightedIndex].name}", Toast.LENGTH_SHORT).show()
//                    Log.d("BlinkDetection", "Blink Detected for Device: ${deviceVMsInRoom[highlightedIndex].name}")
//                } else {
//                    Log.d("BlinkDetection", "Blink Detected, but device list is empty or index invalid.")
//                }
//            }
//        })
//    }
//
//    AndroidView(factory = { previewView }, modifier = Modifier.size(150.dp).background(Color.Black))
//    LaunchedEffect(lifecycleOwner) {
//        blinkDetectionHelper.startCamera(lifecycleOwner)
//    }
//    DisposableEffect(Unit) {
//        onDispose {
//            blinkDetectionHelper.shutdownCameraExecutor()
//        }
//    }
//
//
//}




















































/*
 * WORKING CODE
 *
 * */






//
//
//
//
//package com.example.smarthomecontrol.view.devices
//
//import android.content.Context
//import android.util.Log
//import android.widget.Toast
//import androidx.camera.view.PreviewView
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable // Keep if needed elsewhere, unused in provided snippet
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.AccountCircle
//import androidx.compose.material3.DropdownMenu
//import androidx.compose.material3.DropdownMenuItem
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Switch
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.DisposableEffect
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight // Keep if needed elsewhere
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.lifecycle.compose.LocalLifecycleOwner
//import com.example.smarthomecontrol.BlinkDetectionHelper
//import com.example.smarthomecontrol.R
//import com.example.smarthomecontrol.viewmodel.HomeAppViewModel
//import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel
//import com.example.smarthomecontrol.viewmodel.structures.RoomViewModel
//import com.example.smarthomecontrol.viewmodel.structures.StructureViewModel
//import com.google.home.ConnectivityState
//import com.google.home.Trait
//import com.google.home.matter.standard.OnOff
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//// --- Helper Composables (Mostly Unchanged) ---
//
//@Composable
//fun DevicesAccountButton(homeAppVM: HomeAppViewModel) {
//    IconButton(
//        onClick = { homeAppVM.homeApp.permissionsManager.requestPermissions() },
//        Modifier
//            .size(48.dp)
//            .background(Color.Transparent)
//    ) {
//        Icon(
//            Icons.Default.AccountCircle,
//            "",
//            Modifier.fillMaxSize(),
//            tint = MaterialTheme.colorScheme.primary
//        )
//    }
//}
//
//@Composable
//fun DevicesTopBar(title: String, buttons: List<@Composable () -> Unit>) {
//    Box(
//        Modifier
//            .height(64.dp)
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//    ) {
//        Row(
//            Modifier
//                .height(64.dp)
//                .fillMaxWidth()
//                .background(Color.Transparent),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Text(title, fontSize = 24.sp)
//        }
//
//        Row(
//            Modifier
//                .height(64.dp)
//                .fillMaxWidth()
//                .background(Color.Transparent),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.End
//        ) {
//            for (button in buttons) {
//                button()
//            }
//        }
//    }
//}
//
//// --- Main View (DevicesView) ---
//
//@Composable
//fun DevicesView(homeAppVM: HomeAppViewModel) {
//    val scope: CoroutineScope = rememberCoroutineScope()
//    val context = LocalContext.current // Get context here for Toast/Log
//
//    var expandedStructure: Boolean by remember { mutableStateOf(false) }
//    var expandedRoom: Boolean by remember { mutableStateOf(false) }
//
//    val structureVMs: List<StructureViewModel> by homeAppVM.structureVMs.collectAsState()
//    val selectedStructureVM: StructureViewModel? by homeAppVM.selectedStructureVM.collectAsState()
//    val structureName: String =
//        selectedStructureVM?.name ?: stringResource(R.string.devices_structure_loading)
//
//    val roomVMs: List<RoomViewModel> = selectedStructureVM?.roomVMs?.collectAsState()?.value ?: emptyList()
//
//    // Use remember with selectedStructureVM as a key to reset room selection when structure changes
//    var selectedRoom: RoomViewModel? by remember(selectedStructureVM) {
//        mutableStateOf(roomVMs.firstOrNull())
//    }
//
//    // Update selectedRoom if roomVMs load/change after initial composition
//    LaunchedEffect(roomVMs) {
//        if (selectedRoom == null || !roomVMs.contains(selectedRoom)) {
//            selectedRoom = roomVMs.firstOrNull()
//        }
//    }
//
//    // State to track the currently highlighted device index
//    var highlightedIndex by remember { mutableStateOf(-1) }
//
//    // Reset highlighted index when the selected room changes
//    LaunchedEffect(selectedRoom) {
//        highlightedIndex = -1
//    }
//
//    // Get device list based on selected room (provide empty list if null)
//    val deviceVMsInRoom: List<DeviceViewModel> by selectedRoom?.deviceVMs?.collectAsState() ?: remember { mutableStateOf(emptyList()) }
//
//    Column(modifier = Modifier.fillMaxHeight()) {
//        DevicesTopBar("", listOf { DevicesAccountButton(homeAppVM) })
//
//        Box(modifier = Modifier.weight(1f)) {
//            Column {
//                // Structure Selection
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    var structureText: String = structureName
//                    if (structureVMs.size > 1) structureText += " ▾"
//                    TextButton(onClick = { expandedStructure = true }) {
//                        Text(text = structureText, fontSize = 32.sp)
//                    }
//                }
//
//                // Room Selection
//                Row(
//                    horizontalArrangement = Arrangement.Center,
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    val roomText: String = selectedRoom?.name ?: stringResource(R.string.devices_select_room) // Use resource
//                    TextButton(onClick = { if (roomVMs.isNotEmpty()) expandedRoom = true }) { // Only allow opening if rooms exist
//                        Text(text = roomText, fontSize = 24.sp)
//                    }
//                }
//
//                // Structure Dropdown
//                DropdownMenu(
//                    expanded = expandedStructure,
//                    onDismissRequest = { expandedStructure = false }) {
//                    structureVMs.forEach { structure ->
//                        DropdownMenuItem(
//                            text = { Text(structure.name) },
//                            onClick = {
//                                scope.launch { homeAppVM.selectedStructureVM.emit(structure) }
//                                selectedRoom = null // Reset room selection
//                                highlightedIndex = -1 // Reset highlight
//                                expandedStructure = false
//                            })
//                    }
//                }
//
//                // Room Dropdown
//                DropdownMenu(
//                    expanded = expandedRoom,
//                    onDismissRequest = { expandedRoom = false }) {
//                    roomVMs.forEach { room ->
//                        DropdownMenuItem(
//                            text = { Text(room.name) },
//                            onClick = {
//                                selectedRoom = room
//                                highlightedIndex = -1 // Reset highlight
//                                expandedRoom = false
//                            })
//                    }
//                }
//
//                // --- Device List and Blink Detection Area ---
//                Row(Modifier.fillMaxWidth().weight(1f)) { // Use Row for side-by-side layout
//                    // Device List Column (takes most space)
//                    Column(
//                        modifier = Modifier
//                            .weight(0.7f) // Adjust weight as needed
//                            .verticalScroll(rememberScrollState())
//                            .padding(end = 8.dp) // Add padding between list and camera
//                    ) {
//                        if (selectedRoom != null) {
//                            DeviceListComponent(
//                                homeAppVM = homeAppVM,
//                                deviceVMs = deviceVMsInRoom,
//                                highlightedIndex = highlightedIndex
//                            )
//                        } else {
//                            Text(
//                                stringResource(R.string.devices_select_room_prompt), // Prompt user
//                                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
//                            )
//                        }
//                    }
//
//                    // Blink Detection Column (takes remaining space)
//                    Column(
//                        modifier = Modifier
//                            .weight(0.3f) // Adjust weight as needed
//                            .padding(start = 8.dp), // Add padding
//                        horizontalAlignment = Alignment.CenterHorizontally // Center camera preview
//                    ) {
//                        if (selectedRoom != null && deviceVMsInRoom.isNotEmpty()) {
//                            BlinkDetectionComponent(
//                                deviceVMsInRoom = deviceVMsInRoom,
//                                onHighlightIndexChange = { newIndex ->
//                                    // Update the highlighted index state in the parent
//                                    highlightedIndex = newIndex
//                                },
//                                onDeviceSelectedByBlink = { selectedDevice ->
//                                    // Action to perform when blink is detected for the highlighted device
//                                    val message = "Blink Detected for: ${selectedDevice.name}"
//                                    Log.d("BlinkSelection", message)
//                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                                    // Optional: Trigger device action here, e.g., toggle light
////                                     scope.launch { toggleDevice(selectedDevice) }
//                                    handleDeviceToggleOnBlink(
//                                        device = selectedDevice,
//                                        scope = scope,
//                                        context = context
//                                    )
//
//                                }
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//// --- Device List Component ---
//
//@Composable
//fun DeviceListComponent(
//    homeAppVM: HomeAppViewModel,
//    deviceVMs: List<DeviceViewModel>,
//    highlightedIndex: Int // Receive the single highlighted index
//) {
//    if (deviceVMs.isEmpty()) {
//        Text(
//            stringResource(R.string.devices_no_devices), // Use resource
//            modifier = Modifier.padding(16.dp).fillMaxWidth()
//            // Consider adding alignment or specific styling
//        )
//    } else {
//        deviceVMs.forEachIndexed { index, deviceVM ->
//            DeviceListItem(
//                deviceVM = deviceVM,
//                homeAppVM = homeAppVM,
//                isHighlighted = (index == highlightedIndex) // Check if this item is the highlighted one
//            )
//        }
//    }
//}
//
//// --- Device List Item (Unchanged logic, receives isHighlighted) ---
//
//@Composable
//fun DeviceListItem(
//    deviceVM: DeviceViewModel,
//    homeAppVM: HomeAppViewModel,
//    isHighlighted: Boolean // Receive highlight state
//) {
//    val scope: CoroutineScope = rememberCoroutineScope()
//    val deviceStatus: String by deviceVM.status.collectAsState()
//    val traits: List<Trait> by deviceVM.traits.collectAsState()
//    val isConnected: Boolean = (deviceVM.connectivity == ConnectivityState.ONLINE)
//
//    // Determine background color based on highlight state
//    val backgroundColor = if (isHighlighted) MaterialTheme.colorScheme.primaryContainer else Color.Transparent // Use theme color
//
//    Column(
//        Modifier
//            .padding(horizontal = 24.dp, vertical = 8.dp)
//            .fillMaxWidth()
//            .background(backgroundColor) // Apply the background color
//    ) {
//        Text(deviceVM.name, fontSize = 20.sp)
//        Text(deviceStatus, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) // Use theme color
//
//        val onOffTrait = traits.find { it is OnOff } as? OnOff
//        if (onOffTrait != null) {
//            Switch(
//                checked = onOffTrait.onOff == true,
//                onCheckedChange = { isChecked ->
//                    if (isConnected) {
//                        scope.launch {
//                            try { // Add basic error handling for API calls
//                                if (isChecked) {
//                                    onOffTrait.on()
//                                } else {
//                                    onOffTrait.off()
//                                }
//                            } catch (e: Exception) {
//                                Log.e("DeviceControl", "Failed to toggle ${deviceVM.name}", e)
//                                // Optionally show a toast or message to the user
//                            }
//                        }
//                    }
//                },
//                enabled = isConnected
//            )
//        }
//        // Add more trait controls here if needed (e.g., brightness sliders)
//    }
//}
//
//
//// --- Blink Detection Component (Handles camera and highlighting sequence) ---
//
//@Composable
//fun BlinkDetectionComponent(
//    deviceVMsInRoom: List<DeviceViewModel>,
//    onHighlightIndexChange: (Int) -> Unit, // Callback to notify parent of highlight changes
//    onDeviceSelectedByBlink: (DeviceViewModel) -> Unit // Callback when blink selects a device
//) {
//    val context = LocalContext.current
//    val lifecycleOwner = LocalLifecycleOwner.current
//    val previewView = remember { PreviewView(context) }
//
//    // Internal state to track the index currently being highlighted by the timer
//    var sequenceHighlightIndex by remember { mutableStateOf(-1) }
//
//    // --- Highlighting Sequence Logic ---
//    LaunchedEffect(deviceVMsInRoom) { // Re-run when the list of devices changes
//        if (deviceVMsInRoom.isEmpty()) {
//            sequenceHighlightIndex = -1
//            onHighlightIndexChange(-1) // Notify parent no highlight
//            return@LaunchedEffect // Stop if no devices
//        }
//
//        // Start sequence from the beginning
//        sequenceHighlightIndex = -1 // Ensure starting clean
//        onHighlightIndexChange(-1)
//        delay(1000L) // Initial short delay before first highlight
//
//        var currentIndex = 0
//        while (true) { // Loop indefinitely (or until effect is cancelled)
//            sequenceHighlightIndex = currentIndex
//            onHighlightIndexChange(currentIndex) // Notify parent
//            Log.d("HighlightSeq", "Highlighting index $currentIndex: ${deviceVMsInRoom[currentIndex].name}")
//
//            delay(3000L) // Wait 3 seconds
//
//            // Move to the next device, loop back to 0 if at the end
//            currentIndex = (currentIndex + 1) % deviceVMsInRoom.size
//
//            // Check for potential infinite loops or state issues during recomposition/cancellation - kotlinx.coroutines handles cancellation implicitly
//        }
//    }
//
//    // --- Blink Detection Setup ---
//    val blinkDetectionHelper = remember {
//        BlinkDetectionHelper(context, previewView, object : BlinkDetectionHelper.BlinkListener {
//            override fun onBlinkDetected() {
//                // Check if a device is currently highlighted by the sequence
//                val currentlyHighlightedDeviceIndex = sequenceHighlightIndex
//                if (currentlyHighlightedDeviceIndex in deviceVMsInRoom.indices) {
//                    // A device is highlighted, trigger the selection callback
//                    val selectedDevice = deviceVMsInRoom[currentlyHighlightedDeviceIndex]
//                    onDeviceSelectedByBlink(selectedDevice)
//                } else {
//                    // Blink detected but no device was highlighted at that moment
//                    Log.d("BlinkDetection", "Blink detected, but no device was highlighted in sequence.")
//                }
//            }
//        })
//    }
//
//    // Camera Preview View
//    AndroidView(
//        factory = { previewView },
//        modifier = Modifier
//            .size(120.dp) // Adjust size as needed
//            .background(Color.DarkGray) // Background for the preview area
//    )
//
//    // Start camera when the composable enters the composition and lifecycle is appropriate
//    LaunchedEffect(lifecycleOwner) {
//        blinkDetectionHelper.startCamera(lifecycleOwner)
//        Log.d("BlinkDetectionComponent", "Camera Started")
//    }
//
//    // Clean up camera resources when the composable leaves the composition
//    DisposableEffect(Unit) {
//        onDispose {
//            Log.d("BlinkDetectionComponent", "Disposing BlinkDetectionComponent, shutting down camera.")
//            blinkDetectionHelper.shutdownCameraExecutor()
//        }
//    }
//}
//
//
//
//
//private fun handleDeviceToggleOnBlink(
//    device: DeviceViewModel,
//    scope: CoroutineScope,
//    context: Context
//) {
//    // 1. Find the OnOff trait from the device's current state
//    val traits = device.traits.value // Access the current value of the StateFlow
//    val onOffTrait = traits.find { it is OnOff } as? OnOff
//
//    if (onOffTrait != null) {
//        // 2. Launch a coroutine because .on() and .off() are suspend functions
//        scope.launch {
//            try {
//                // 3. Check the current state and toggle
//                val currentState = onOffTrait.onOff
//                Log.d("BlinkToggle", "Device ${device.name} current state: $currentState. Attempting toggle...")
//                if (currentState == true) {
//                    // If ON, turn OFF
//                    onOffTrait.off()
//                    Log.d("BlinkToggle", "Executed OFF for ${device.name}")
//                } else {
//                    // If OFF or null, turn ON
//                    onOffTrait.on()
//                    Log.d("BlinkToggle", "Executed ON for ${device.name}")
//                }
//            } catch (e: Exception) {
//                // 4. Handle potential errors during the API call
//                Log.e("BlinkToggle", "Failed to toggle device ${device.name}", e)
//                // Show error message to the user
//                Toast.makeText(context, "Failed to toggle ${device.name}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    } else {
//        // 5. Handle case where the device doesn't support OnOff
//        Log.w("BlinkSelection", "Device ${device.name} has no OnOff trait to toggle.")
//        Toast.makeText(context, "${device.name} cannot be toggled.", Toast.LENGTH_SHORT).show()
//    }
//}
//
//





// Add missing String Resources to res/values/strings.xml
/*
<resources>
    // ... other strings
    <string name="devices_structure_loading">Loading Structure…</string>
    <string name="devices_select_room">Select Room</string>
    <string name="devices_select_room_prompt">Please select a room to view devices.</string>
    <string name="devices_no_devices">No devices found in this room.</string>
</resources>
*/





package com.example.smarthomecontrol.view.devices

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
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
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.smarthomecontrol.BlinkDetectionHelper
import com.example.smarthomecontrol.R
import com.example.smarthomecontrol.viewmodel.HomeAppViewModel
import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel
import com.example.smarthomecontrol.viewmodel.structures.RoomViewModel
import com.example.smarthomecontrol.viewmodel.structures.StructureViewModel
import com.google.home.ConnectivityState
import com.google.home.Trait
import com.google.home.matter.standard.OnOff
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// --- Helper Composables (Mostly Unchanged) ---

@Composable
fun DevicesAccountButton(homeAppVM: HomeAppViewModel) {
    IconButton(
        onClick = { homeAppVM.homeApp.permissionsManager.requestPermissions() },
        Modifier
            .size(48.dp)
            .background(Color.Transparent)
    ) {
        Icon(
            Icons.Default.AccountCircle,
            "",
            Modifier.fillMaxSize(),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun DevicesTopBar(title: String, buttons: List<@Composable () -> Unit>) {
    Box(
        Modifier
            .height(64.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            Modifier
                .height(64.dp)
                .fillMaxWidth()
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(title, fontSize = 24.sp)
        }

        Row(
            Modifier
                .height(64.dp)
                .fillMaxWidth()
                .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            for (button in buttons) {
                button()
            }
        }
    }
}

// --- Main View (DevicesView) ---

@Composable
fun DevicesView(homeAppVM: HomeAppViewModel) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    var expandedStructure: Boolean by remember { mutableStateOf(false) }
    var expandedRoom: Boolean by remember { mutableStateOf(false) }

    val cachedStructureVMs = remember { mutableStateOf<List<StructureViewModel>?>(null) }
    val structureVMs: List<StructureViewModel> by homeAppVM.structureVMs.collectAsState(initial = cachedStructureVMs.value ?: emptyList())
    LaunchedEffect(structureVMs) {
        cachedStructureVMs.value = structureVMs
    }

    val selectedStructureVM: StructureViewModel? by homeAppVM.selectedStructureVM.collectAsState()
    val structureName: String =
        selectedStructureVM?.name ?: stringResource(R.string.devices_structure_loading)

    val cachedRoomVMs = remember(selectedStructureVM) { mutableStateOf<List<RoomViewModel>?>(null) }
    val roomVMs: List<RoomViewModel> by selectedStructureVM?.roomVMs?.collectAsState(initial = cachedRoomVMs.value ?: emptyList()) ?: remember { mutableStateOf(emptyList()) }
    LaunchedEffect(roomVMs) {
        cachedRoomVMs.value = roomVMs
    }

    var selectedRoom: RoomViewModel? by remember(selectedStructureVM) {
        mutableStateOf(roomVMs.firstOrNull())
    }

    LaunchedEffect(roomVMs) {
        if (selectedRoom == null || !roomVMs.contains(selectedRoom)) {
            selectedRoom = roomVMs.firstOrNull()
        }
    }

    var highlightedIndex by remember { mutableStateOf(-1) }

    LaunchedEffect(selectedRoom) {
        highlightedIndex = -1
    }

    val deviceVMsInRoom: List<DeviceViewModel> by selectedRoom?.deviceVMs?.collectAsState() ?: remember { mutableStateOf(emptyList()) }

    Column(modifier = Modifier.fillMaxHeight()) {
        DevicesTopBar("", listOf { DevicesAccountButton(homeAppVM) })

        Box(modifier = Modifier.weight(1f)) {
            Column {
                // Structure Selection
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    var structureText: String = structureName
                    if (structureVMs.size > 1) structureText += " ▾"
                    TextButton(onClick = { expandedStructure = true }) {
                        Text(text = structureText, fontSize = 32.sp)
                    }
                }

                // Room Selection
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val roomText: String = selectedRoom?.name ?: stringResource(R.string.devices_select_room)
                    TextButton(onClick = { if (roomVMs.isNotEmpty()) expandedRoom = true }) {
                        Text(text = roomText, fontSize = 24.sp)
                    }
                }

                // Structure Dropdown
                DropdownMenu(
                    expanded = expandedStructure,
                    onDismissRequest = { expandedStructure = false }) {
                    structureVMs.forEach { structure ->
                        DropdownMenuItem(
                            text = { Text(structure.name) },
                            onClick = {
                                scope.launch { homeAppVM.selectedStructureVM.emit(structure) }
                                selectedRoom = null
                                highlightedIndex = -1
                                expandedStructure = false
                            })
                    }
                }

                // Room Dropdown
                DropdownMenu(
                    expanded = expandedRoom,
                    onDismissRequest = { expandedRoom = false }) {
                    roomVMs.forEach { room ->
                        DropdownMenuItem(
                            text = { Text(room.name) },
                            onClick = {
                                selectedRoom = room
                                highlightedIndex = -1
                                expandedRoom = false
                            })
                    }
                }

                // --- Device List and Blink Detection Area ---
                Row(Modifier.fillMaxWidth().weight(1f)) {
                    // Device List Column
                    Column(
                        modifier = Modifier
                            .weight(0.7f)
                            .verticalScroll(rememberScrollState())
                            .padding(end = 8.dp)
                    ) {
                        if (selectedRoom != null) {
                            DeviceListComponent(
                                homeAppVM = homeAppVM,
                                deviceVMs = deviceVMsInRoom,
                                highlightedIndex = highlightedIndex
                            )
                        }
                    }



                }
                // Blink Detection Column
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .padding(start = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (selectedRoom != null && deviceVMsInRoom.isNotEmpty()) {
                        BlinkDetectionComponent(
                            deviceVMsInRoom = deviceVMsInRoom,
                            onHighlightIndexChange = { newIndex ->
                                highlightedIndex = newIndex
                            },
                            onDeviceSelectedByBlink = { selectedDevice ->
                                // The actual toggle logic will be handled with debouncing
                                handleBlinkToggle(selectedDevice, scope, context)
                            }
                        )
                    }
                }
            }
        }
    }
}

// --- Device List Component ---

@Composable
fun DeviceListComponent(
    homeAppVM: HomeAppViewModel,
    deviceVMs: List<DeviceViewModel>,
    highlightedIndex: Int
) {
    if (deviceVMs.isEmpty()) {
        Text(
            stringResource(R.string.devices_no_devices),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        )
    } else {
        deviceVMs.forEachIndexed { index, deviceVM ->
            DeviceListItem(
                deviceVM = deviceVM,
                homeAppVM = homeAppVM,
                isHighlighted = (index == highlightedIndex)
            )
        }
    }
}

// --- Device List Item (Unchanged logic, receives isHighlighted) ---

@Composable
fun DeviceListItem(
    deviceVM: DeviceViewModel,
    homeAppVM: HomeAppViewModel,
    isHighlighted: Boolean
) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val deviceStatus: String by deviceVM.status.collectAsState()
    val traits: List<Trait> by deviceVM.traits.collectAsState()
    val isConnected: Boolean = (deviceVM.connectivity == ConnectivityState.ONLINE)

    val backgroundColor = if (isHighlighted) MaterialTheme.colorScheme.primaryContainer else Color.Transparent

    Column(
        Modifier
            .padding(horizontal = 24.dp, vertical = 8.dp)
            .fillMaxWidth()
            .background(backgroundColor)
    ) {
        Text(deviceVM.name, fontSize = 20.sp)
        Text(deviceStatus, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)

        val onOffTrait = traits.find { it is OnOff } as? OnOff
        if (onOffTrait != null) {
            Switch(
                checked = onOffTrait.onOff == true,
                onCheckedChange = { isChecked ->
                    if (isConnected) {
                        scope.launch {
                            try {
                                if (isChecked) {
                                    onOffTrait.on()
                                } else {
                                    onOffTrait.off()
                                }
                            } catch (e: Exception) {
                                Log.e("DeviceControl", "Failed to toggle ${deviceVM.name}", e)
                                // Consider showing a user-friendly error message here
                            }
                        }
                    }
                },
                enabled = isConnected
            )
        }
        // Add more trait controls here if needed (e.g., brightness sliders)
    }
}


// --- Blink Detection Component (Handles camera and highlighting sequence) ---

@Composable
fun BlinkDetectionComponent(
    deviceVMsInRoom: List<DeviceViewModel>,
    onHighlightIndexChange: (Int) -> Unit,
    onDeviceSelectedByBlink: (DeviceViewModel) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val previewView = remember { PreviewView(context) }

    var sequenceHighlightIndex by remember { mutableStateOf(-1) }

    val highlightDuration = 3000L
    val initialDelay = 500L

    LaunchedEffect(deviceVMsInRoom) {
        if (deviceVMsInRoom.isEmpty()) {
            sequenceHighlightIndex = -1
            onHighlightIndexChange(-1)
            return@LaunchedEffect
        }

        sequenceHighlightIndex = -1
        onHighlightIndexChange(-1)
        delay(initialDelay)

        var currentIndex = 0
        while (true) {
            sequenceHighlightIndex = currentIndex
            onHighlightIndexChange(currentIndex)
            Log.d("HighlightSeq", "Highlighting index $currentIndex: ${deviceVMsInRoom[currentIndex].name}")

            delay(highlightDuration)

            currentIndex = (currentIndex + 1) % deviceVMsInRoom.size
        }
    }

    val blinkDetectionHelper = remember {
        BlinkDetectionHelper(context, previewView, object : BlinkDetectionHelper.BlinkListener {
            override fun onBlinkDetected() {
                val currentlyHighlightedDeviceIndex = sequenceHighlightIndex
                if (currentlyHighlightedDeviceIndex in deviceVMsInRoom.indices) {
                    val selectedDevice = deviceVMsInRoom[currentlyHighlightedDeviceIndex]
                    onDeviceSelectedByBlink(selectedDevice)
                } else {
                    Log.d("BlinkDetection", "Blink detected, but no device was highlighted in sequence.")
                }
            }
        })
    }

    AndroidView(
        factory = { previewView },
        modifier = Modifier
            .size(120.dp)
            .background(Color.DarkGray)
    )

    LaunchedEffect(lifecycleOwner) {
        blinkDetectionHelper.startCamera(lifecycleOwner)
        Log.d("BlinkDetectionComponent", "Camera Started")
    }

    DisposableEffect(Unit) {
        onDispose {
            Log.d("BlinkDetectionComponent", "Disposing BlinkDetectionComponent, shutting down camera.")
            blinkDetectionHelper.shutdownCameraExecutor()
        }
    }
}

// --- Debouncing Logic for Blink Toggle ---
private var lastToggleTime = 0L
private const val TOGGLE_DEBOUNCE_DELAY = 3000L // Adjust this value as needed

private fun handleBlinkToggle(
    device: DeviceViewModel,
    scope: CoroutineScope,
    context: Context
) {
    val currentTime = System.currentTimeMillis()
    if (currentTime - lastToggleTime >= TOGGLE_DEBOUNCE_DELAY) {
        lastToggleTime = currentTime
        handleDeviceToggleOnBlink(device, scope, context)
    } else {
        Log.d("BlinkToggle", "Blink ignored due to debounce.")
//        Toast.makeText(context, "Ignoring rapid blink.", Toast.LENGTH_SHORT).show() // Optional feedback
    }
}

private fun handleDeviceToggleOnBlink(
    device: DeviceViewModel,
    scope: CoroutineScope,
    context: Context
) {
    val traits = device.traits.value
    val onOffTrait = traits.find { it is OnOff } as? OnOff

    if (onOffTrait != null) {
        scope.launch {
            try {
                val currentState = onOffTrait.onOff
                Log.d("BlinkToggle", "Device ${device.name} current state: $currentState. Attempting toggle...")
                Toast.makeText(context, "Blink detected", Toast.LENGTH_SHORT).show()
                if (currentState == true) {
                    onOffTrait.off()
                    Log.d("BlinkToggle", "Executed OFF for ${device.name}")
                } else {
                    onOffTrait.on()
                    Log.d("BlinkToggle", "Executed ON for ${device.name}")
                }
            } catch (e: Exception) {
                Log.e("BlinkToggle", "Failed to toggle device ${device.name}", e)
//                Toast.makeText(context, "Failed to toggle ${device.name}", Toast.LENGTH_SHORT).show()
            }
        }
    } else {
        Log.w("BlinkSelection", "Device ${device.name} has no OnOff trait to toggle.")
//        Toast.makeText(context, "${device.name} cannot be toggled.", Toast.LENGTH_SHORT).show()
    }
}