
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

package com.example.smarthomecontrol

import android.content.Context
import androidx.activity.ComponentActivity
import com.google.home.DeviceType
import com.google.home.DeviceTypeFactory
import com.google.home.FactoryRegistry
import com.google.home.HomeClient
import com.google.home.HomeConfig
import com.google.home.Trait
import com.google.home.TraitFactory
import com.google.home.matter.standard.BooleanState
import com.google.home.matter.standard.ColorTemperatureLightDevice
import com.google.home.matter.standard.ContactSensorDevice
import com.google.home.matter.standard.DimmableLightDevice
import com.google.home.matter.standard.ExtendedColorLightDevice
import com.google.home.matter.standard.GenericSwitchDevice
import com.google.home.matter.standard.LevelControl
import com.google.home.matter.standard.OnOff
import com.google.home.matter.standard.OnOffLightDevice
import com.google.home.matter.standard.OnOffLightSwitchDevice
import com.google.home.matter.standard.OnOffPluginUnitDevice
import com.google.home.matter.standard.OnOffSensorDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class HomeApp(val context: Context, val scope: CoroutineScope, val activity : ComponentActivity) {

    var homeClient: HomeClient

    val permissionsManager : PermissionsManager
//    val commissioningManager : CommissioningManager

    init {
        // Registry to record device types and traits used in this app:
        val registry = FactoryRegistry(
            types = supportedTypes,
            traits = supportedTraits
        )

        // Configuration options for the HomeClient:
        val config = HomeConfig(
            coroutineContext = Dispatchers.IO,
            factoryRegistry = registry
        )

        // Initialize the HomeClient, which is the primary object to use all Home APIs:
        homeClient = HomeClientProvider.getClient(context = context, homeConfig = config)

        // Initialize supporting classes for Permissions and Commissioning APIs:
        permissionsManager = PermissionsManager(context, scope, activity, homeClient)
//        commissioningManager = CommissioningManager(context, scope, activity)
    }

    companion object {
        // List of supported device types by this app:
        val supportedTypes: List<DeviceTypeFactory<out DeviceType>> = listOf(
            OnOffLightDevice,
            DimmableLightDevice,
            ColorTemperatureLightDevice,
            ExtendedColorLightDevice,
            GenericSwitchDevice,
            OnOffLightSwitchDevice,
            OnOffPluginUnitDevice,
            OnOffSensorDevice,
            ContactSensorDevice,

        )

        // List of supported device traits by this app:
        val supportedTraits: List<TraitFactory<out Trait>> = listOf(
            OnOff,
            LevelControl,
            BooleanState
        )
    }
}