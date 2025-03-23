package com.example.smarthomecontrol.view.devices

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarthomecontrol.viewmodel.HomeAppViewModel
import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel
import com.google.home.ConnectivityState
import com.google.home.DeviceType
import com.google.home.Trait
import com.google.home.matter.standard.LevelControl
import com.google.home.matter.standard.LevelControlTrait
import com.google.home.matter.standard.OnOff
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DeviceView (homeAppVM: HomeAppViewModel) {
    val scope: CoroutineScope = rememberCoroutineScope()
    val deviceVM: DeviceViewModel? by homeAppVM.selectedDeviceVM.collectAsState()

    BackHandler {
        scope.launch { homeAppVM.selectedDeviceVM.emit(null) }
    }

    Column {
        Spacer(Modifier.height(64.dp))

        Box (modifier = Modifier.weight(1f)) {

            Column {
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth()) {
                    Text(text = "" + deviceVM?.name, fontSize = 32.sp)
                }

                Column(modifier = Modifier.verticalScroll(rememberScrollState()).weight(weight = 1f, fill = false)) {
                    ControlListComponent(homeAppVM)
                }
            }
        }
    }
}

@Composable
fun ControlListComponent (homeAppVM: HomeAppViewModel) {

    val deviceVM: DeviceViewModel = homeAppVM.selectedDeviceVM.collectAsState().value ?: return
    val deviceType: DeviceType = deviceVM.type.collectAsState().value
    val deviceTypeName: String = deviceVM.typeName.collectAsState().value
    val deviceTraits: List<Trait> = deviceVM.traits.collectAsState().value

    val isConnected: Boolean = (deviceVM.connectivity == ConnectivityState.ONLINE)

    Column (Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()) {
        Text(deviceTypeName, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }

    for (trait in deviceTraits) {
        ControlListItem(trait, isConnected, deviceType)
    }
}

@Composable
fun ControlListItem (trait: Trait, isConnected: Boolean, type: DeviceType) {
    val scope: CoroutineScope = rememberCoroutineScope()

    Box (Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
        when (trait) {
            is OnOff -> {
                Column (Modifier.fillMaxWidth()) {
                    Text(trait.factory.toString(), fontSize = 20.sp)
                    Text(DeviceViewModel.getTraitStatus(trait, type), fontSize = 16.sp)
                }

                Switch (checked = (trait.onOff == true), modifier = Modifier.align(Alignment.CenterEnd),
                    onCheckedChange = { state ->
                        scope.launch { if (state) trait.on() else trait.off() }
                    },
                    enabled = isConnected
                )
            }




            else -> return
        }
    }
}

@Composable
fun LevelSlider(value: Float, low: Float, high: Float, steps: Int, onValueChange: (Float) -> Unit, modifier: Modifier, isEnabled: Boolean) {
    var level: Float by remember { mutableStateOf(value) }
    var oldValue: Float by remember { mutableStateOf(value) }

    Slider(
        value = level,
        valueRange = low..high,
        steps = steps,
        modifier = modifier,
        onValueChange = { level = it },
        onValueChangeFinished = { onValueChange(level) },
        enabled = isEnabled
    )

    // Register external value change:
    if(value != oldValue) {
        oldValue = value
        level = value
    }
}
