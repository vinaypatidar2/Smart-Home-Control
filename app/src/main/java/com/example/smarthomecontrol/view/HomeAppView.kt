package com.example.smarthomecontrol.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.smarthomecontrol.ui.theme.GoogleHomeAPISampleAppTheme
//import com.example.googlehomeapisampleapp.view.automations.ActionView
//import com.example.googlehomeapisampleapp.view.automations.AutomationView
//import com.example.googlehomeapisampleapp.view.automations.AutomationsView
//import com.example.googlehomeapisampleapp.view.automations.CandidatesView
//import com.example.googlehomeapisampleapp.view.automations.DraftView
//import com.example.googlehomeapisampleapp.view.automations.StarterView
import com.example.smarthomecontrol.view.devices.DeviceView
import com.example.smarthomecontrol.view.devices.DevicesView
import com.example.smarthomecontrol.viewmodel.HomeAppViewModel
//import com.example.googlehomeapisampleapp.viewmodel.automations.ActionViewModel
//import com.example.googlehomeapisampleapp.viewmodel.automations.AutomationViewModel
//import com.example.googlehomeapisampleapp.viewmodel.automations.CandidateViewModel
//import com.example.googlehomeapisampleapp.viewmodel.automations.DraftViewModel
//import com.example.googlehomeapisampleapp.viewmodel.automations.StarterViewModel
import com.example.smarthomecontrol.viewmodel.devices.DeviceViewModel


@Composable
fun HomeAppView (homeAppVM: HomeAppViewModel) {
    /** Value tracking whether a user is signed-in on the app **/
    val isSignedIn: Boolean = homeAppVM.homeApp.permissionsManager.isSignedIn.collectAsState().value

    /** Values tracking what is being selected on the app **/
    val selectedTab: HomeAppViewModel.NavigationTab by homeAppVM.selectedTab.collectAsState()
    val selectedDeviceVM: DeviceViewModel? by homeAppVM.selectedDeviceVM.collectAsState()

    // Apply theme on the top-level view:
    GoogleHomeAPISampleAppTheme {
        // Top-level external frame for the views:
        Column(modifier = Modifier.fillMaxSize()) {
            // Top spacer to allocate space for status bar / camera notch:
            Spacer(modifier = Modifier.height(48.dp).fillMaxWidth().background(Color.Transparent))

            // Primary frame to hold content:
            Column (modifier = Modifier.weight(1f).fillMaxWidth().background(Color.Transparent)) {

                /** Navigation Flow, displays a view depending on the viewmodel state **/

                // If not signed-in, show WelcomeView:
                if (!isSignedIn) {
                    WelcomeView(homeAppVM)
                }

                // If a device is selected, show the device controls:
                if (selectedDeviceVM != null) {
                    DeviceView(homeAppVM)
                }
                DevicesView(homeAppVM)

                }
            }
        }
    }
