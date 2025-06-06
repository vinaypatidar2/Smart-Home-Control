package com.example.smarthomecontrol.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.example.googlehomeapisampleapp.R
import com.example.smarthomecontrol.R
import com.example.smarthomecontrol.viewmodel.HomeAppViewModel

@Composable
fun WelcomeView (homeAppVM: HomeAppViewModel) {
    Column (modifier = Modifier.fillMaxSize().padding(32.dp), verticalArrangement = Arrangement.Center) {

//        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//            Text(stringResource(R.string.welcome_text_1), fontSize = 32.sp)
//        }

//        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//            Text(stringResource(R.string.welcome_text_2), fontSize = 32.sp)
//        }

//        Spacer(Modifier.height(32.dp))

        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image (
                painter = painterResource(R.drawable.icon_app),
                contentDescription = stringResource(R.string.app_name)
            )
        }

        Spacer(Modifier.height(32.dp))

        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(stringResource(R.string.app_name), fontSize = 24.sp, textAlign = TextAlign.Center)
        }

//        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//            Text(stringResource(R.string.welcome_text_4), fontSize = 24.sp, textAlign = TextAlign.Center)
//        }

//        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//            Text(stringResource(R.string.welcome_text_5), fontSize = 24.sp, textAlign = TextAlign.Center)
//        }

        Spacer(Modifier.height(32.dp))

        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            // Sign-in button to trigger Permissions API and start the sign-in flow:
            Button(onClick = { homeAppVM.homeApp.permissionsManager.requestPermissions() }) {
                Text(stringResource(R.string.welcome_text_3))
            }
        }
    }
}