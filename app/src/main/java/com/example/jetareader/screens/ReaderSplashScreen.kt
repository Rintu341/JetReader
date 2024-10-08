package com.example.jetareader.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetareader.R
import com.example.jetareader.navigation.ReaderAppScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Splash Screen",
    device = Devices.PIXEL_6_PRO
)
@Composable
fun ReaderSplashScreen(navController: NavController = rememberNavController()) {

    val scale = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(8f)
                        .getInterpolation(it)
                })
        )
        delay(2000L)
        //Check if user is already logged in
        //TODO: Check if user is already logged in
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.popBackStack()
            navController.navigate(ReaderAppScreen.LoginScreen.name)  // if not log in, go to login screen
        } else {
            navController.popBackStack()
            navController.navigate(ReaderAppScreen.ReaderHomeScreen.name) // go to home screen
        }
    }
    Surface(
        modifier = Modifier
            .size(330.dp)
            .scale(scale = scale.value),
        color = Color.White,
        shape = CircleShape,
        shadowElevation = 10.dp,
        border = BorderStroke(width = 2.dp, color = Color.Yellow)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(id = R.string.Reader), style = MaterialTheme.typography.displayLarge,
                color = Color.Cyan.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "\"Read. Change. Yourself\"", style = MaterialTheme.typography.titleMedium,
                color = Color.LightGray
            )
        }
    }
}