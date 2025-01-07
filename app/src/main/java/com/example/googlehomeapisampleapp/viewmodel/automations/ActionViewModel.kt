
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

package com.example.googlehomeapisampleapp.viewmodel.automations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.googlehomeapisampleapp.viewmodel.devices.DeviceViewModel
import com.google.home.DeviceType
import com.google.home.Trait
import com.google.home.TraitFactory
import com.google.home.matter.standard.LevelControl
import com.google.home.matter.standard.OnOff
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ActionViewModel (val candidate: CandidateViewModel? = null) : ViewModel() {

    // List of operations available when creating automation starters:
    enum class Action {
        ON,
        OFF,
        MOVE_TO_LEVEL,
    }

    open class Actions (val actions : List<Action>)

    val name: MutableStateFlow<String?>
    val description: MutableStateFlow<String?>

    val deviceVM: MutableStateFlow<DeviceViewModel?>
    val trait: MutableStateFlow<Trait?>
    val action: MutableStateFlow<Action?>

    val valueOnOff: MutableStateFlow<Boolean?>
    val valueLevel: MutableStateFlow<UByte?>

    init {
        // Initialize containers for name and description:
        name = MutableStateFlow(null)
        description = MutableStateFlow(null)
        // Initialize containers for action attributes:
        deviceVM = MutableStateFlow(null)
        trait = MutableStateFlow(null)
        action = MutableStateFlow(null)

        valueOnOff = MutableStateFlow(true)
        valueLevel = MutableStateFlow(50u)

        viewModelScope.launch {
            // Subscribe to changes on dynamic values:
            launch { subscribeToDevice() }
            launch { subscribeToTrait() }
        }
    }

    private suspend fun subscribeToDevice() {
        // Subscribe to device selection, to automatically determine the name of the action:
        deviceVM.collect { deviceVM ->
            val deviceType: DeviceType? = deviceVM?.type?.value
            name.emit(deviceType.toString())
        }
    }

    private suspend fun subscribeToTrait() {
        // Subscribe to trait selection, to automatically determine the description of the action:
        trait.collect { trait ->
            description.emit(trait?.factory.toString())
        }
    }

    companion object {

        // List of operations available when comparing booleans:
        object OnOffActions : Actions(listOf(
            Action.ON,
            Action.OFF,
        ))

        // List of operations available when comparing booleans:
        object LevelActions : Actions(listOf(
            Action.MOVE_TO_LEVEL
        ))

        // Map traits and the comparison operations they support:
        val actionActions: Map<TraitFactory<out Trait>, Actions> = mapOf(
            OnOff to OnOffActions,
            LevelControl to LevelActions,
            // BooleanState - No Actions
            // OccupancySensing - No Actions
        )
    }
}
