package com.example.smarthomecontrol

import android.content.Context
import androidx.activity.ComponentActivity
import com.google.home.HomeClient
import com.google.home.HomeException
import com.google.home.PermissionsResult
import com.google.home.PermissionsResultStatus
import com.google.home.PermissionsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PermissionsManager(val context: Context, val scope: CoroutineScope, val activity: ComponentActivity, val client: HomeClient) {

    var isSignedIn: MutableStateFlow<Boolean>

    init {
        // StateFlow to carry the result for successful sign-in:
        isSignedIn = MutableStateFlow(false)
        // Register permission caller callback on HomeClient:
        client.registerActivityResultCallerForPermissions(activity)
        // Check the current permission state:
        checkPermissions()
    }

    private fun checkPermissions() {
        scope.launch {
            // Check and wait until getting the first permission state after initialization:
            val permissionsState: PermissionsState = client.hasPermissions().first { state ->
                state != PermissionsState.PERMISSIONS_STATE_UNINITIALIZED
            }
            // Adjust the sign-in status according to permission state:
            isSignedIn.emit(permissionsState == PermissionsState.GRANTED)
            // Report the permission state:
            reportPermissionState(permissionsState)
        }
    }

    fun requestPermissions() {
        scope.launch {
            try {
                // Request permissions from the Permissions API and record the result:
                val result: PermissionsResult = client.requestPermissions(forceLaunch = true)
                // Adjust the sign-in status according to permission result:
                if (result.status == PermissionsResultStatus.SUCCESS)
                    isSignedIn.emit(true)
                // Report the permission result:
                reportPermissionResult(result)
            }
            catch (e: HomeException) { MainActivity.showError(this, e.message.toString()) }
        }
    }

    private fun reportPermissionState(permissionState : PermissionsState) {
        val message: String = "Permissions State: " + permissionState.name
        // Report the permission state:
        when (permissionState) {
            PermissionsState.GRANTED ->
                MainActivity.showDebug(this, message)
            PermissionsState.NOT_GRANTED ->
                MainActivity.showWarning(this, message)
            PermissionsState.PERMISSIONS_STATE_UNAVAILABLE ->
                MainActivity.showWarning(this, message)
            PermissionsState.PERMISSIONS_STATE_UNINITIALIZED ->
                MainActivity.showError(this, message)
            else -> MainActivity.showError(this, message)
        }
    }

    private fun reportPermissionResult(permissionResult: PermissionsResult) {
        var message: String = "Permissions Result: " + permissionResult.status.name
        // Include any error messages in the permission result:
        if (permissionResult.errorMessage != null)
            message += " | " + permissionResult.errorMessage
        // Report the permission result:
        when (permissionResult.status) {
            PermissionsResultStatus.SUCCESS ->
                MainActivity.showDebug(this, message)
            PermissionsResultStatus.CANCELLED ->
                MainActivity.showWarning(this, message)
            PermissionsResultStatus.ERROR ->
                MainActivity.showError(this, message)
            else -> MainActivity.showError(this, message)
        }
    }

}

