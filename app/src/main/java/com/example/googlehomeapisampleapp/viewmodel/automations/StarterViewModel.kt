
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
import com.google.home.matter.standard.BooleanState
import com.google.home.matter.standard.LevelControl
import com.google.home.matter.standard.OccupancySensing
import com.google.home.matter.standard.OccupancySensingTrait
import com.google.home.matter.standard.OnOff
import com.google.home.matter.standard.Thermostat
import com.google.home.matter.standard.ThermostatTrait
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StarterViewModel (val candidateVM: CandidateViewModel? = null) : ViewModel() {

    // List of operations available when creating automation starters:
    enum class Operation {
        EQUALS,
        NOT_EQUALS,
        GREATER_THAN,
        GREATER_THAN_OR_EQUALS,
        LESS_THAN,
        LESS_THAN_OR_EQUALS
    }

    open class Operations (val operations : List<Operation>)

    val name: MutableStateFlow<String?>
    val description: MutableStateFlow<String?>

    val deviceVM : MutableStateFlow<DeviceViewModel?>
    val trait : MutableStateFlow<TraitFactory<out Trait>?>
    val operation : MutableStateFlow<Operation?>

    val valueOnOff : MutableStateFlow<Boolean>
    val valueLevel : MutableStateFlow<UByte>
    val valueBooleanState : MutableStateFlow<Boolean>
    val valueOccupancy : MutableStateFlow<OccupancySensingTrait.OccupancyBitmap>
    val valueThermostat : MutableStateFlow<ThermostatTrait.SystemModeEnum>

    init {
        // Initialize containers for name and description:
        name = MutableStateFlow(null)
        description = MutableStateFlow(null)
        // Initialize containers for starter attributes:
        deviceVM = MutableStateFlow(null)
        trait = MutableStateFlow(null)
        operation = MutableStateFlow(null)
        // Initialize containers for potential starter value:
        valueOnOff = MutableStateFlow(true)
        valueLevel = MutableStateFlow(50u)
        valueBooleanState = MutableStateFlow(true)
        valueOccupancy = MutableStateFlow(OccupancySensingTrait.OccupancyBitmap())
        valueThermostat = MutableStateFlow(ThermostatTrait.SystemModeEnum.Off)

        // Subscribe to changes on dynamic values:
        viewModelScope.launch { subscribeToDevice() }
        viewModelScope.launch { subscribeToTrait() }
    }

    private suspend fun subscribeToDevice() {
        // Subscribe to device selection, to automatically determine the name of the starter:
        deviceVM.collect { deviceVM ->
            val deviceType: DeviceType? = deviceVM?.type?.value
            name.emit(deviceType.toString())
        }
    }

    private suspend fun subscribeToTrait() {
        // Subscribe to trait selection, to automatically determine the description of the starter:
        trait.collect { trait ->
            description.emit(trait?.factory.toString())
        }
    }

    companion object {

        // List of operations available when comparing booleans:
        object BooleanOperations : Operations(listOf(
            Operation.EQUALS,
            Operation.NOT_EQUALS
        ))

        // List of operations available when comparing booleans:
        object OccupancyOperations : Operations(listOf(
            Operation.EQUALS,
            Operation.NOT_EQUALS
        ))

        // List of operations available when comparing values:
        object LevelOperations : Operations(listOf(
            Operation.GREATER_THAN,
            Operation.GREATER_THAN_OR_EQUALS,
            Operation.LESS_THAN,
            Operation.LESS_THAN_OR_EQUALS
        ))

        // Map traits and the comparison operations they support:
        val starterOperations: Map<TraitFactory<out Trait>, Operations> = mapOf(
            OnOff to BooleanOperations,
            LevelControl to LevelOperations,
            BooleanState to BooleanOperations,
            OccupancySensing to OccupancyOperations,
            Thermostat to BooleanOperations,
        )

        enum class OnOffValue {
            On,
            Off,
        }

        val valuesOnOff: Map<OnOffValue, Boolean> = mapOf(
            OnOffValue.On to true,
            OnOffValue.Off to false,
        )

        enum class ContactValue {
            Open,
            Closed,
        }

        val valuesContact: Map<ContactValue, Boolean> = mapOf(
            ContactValue.Closed to true,
            ContactValue.Open to false,
        )

        enum class OccupancyValue {
            Occupied,
            NotOccupied,
        }

        val valuesOccupancy: Map<OccupancyValue, OccupancySensingTrait.OccupancyBitmap?> = mapOf(
            OccupancyValue.Occupied to OccupancySensingTrait.OccupancyBitmap(true),
            OccupancyValue.NotOccupied to OccupancySensingTrait.OccupancyBitmap(false),
        )

        enum class ThermostatValue {
            Heat,
            Cool,
            Off,
        }

        val valuesThermostat: Map<ThermostatValue, ThermostatTrait.SystemModeEnum> = mapOf(
            ThermostatValue.Heat to ThermostatTrait.SystemModeEnum.Heat,
            ThermostatValue.Cool to ThermostatTrait.SystemModeEnum.Cool,
            ThermostatValue.Off to ThermostatTrait.SystemModeEnum.Off,
        )
    }
}
