package com.example.jetareader.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jetareader.R
import com.example.jetareader.components.EmailInput
import com.example.jetareader.components.PasswordInput
import com.example.jetareader.navigation.ReaderAppScreen

@Composable
fun ReaderLoginScreen(navController: NavController) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(id = R.string.Reader), style = MaterialTheme.typography.displayLarge,
                color = Color.Cyan.copy(alpha = 0.5f)
            )
            if (showLoginForm.value) UserForm(loading = false, isCreateAccount = false) { email, pwd ->
                navController.navigate(ReaderAppScreen.ReaderHomeScreen.name) // TODO Login user
            }
            else{
                UserForm(loading = false, isCreateAccount = true){ email, pwd -> //TODO Create FB Account
                    navController.navigate(ReaderAppScreen.ReaderHomeScreen.name)
                }

            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row (
            modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            val text = if(showLoginForm.value) "Sign Up" else "Log in"
            Text(text = "New User?")
            Text(text,
                modifier = Modifier.clickable {
                    showLoginForm.value = !showLoginForm.value
                }.padding(start = 5.dp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                color = Color.Cyan)

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true, device = Devices.PIXEL_6_PRO, showSystemUi = true)
@Composable
fun UserForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> {} }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Cancel
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    val modifier = Modifier
        .padding(start = 10.dp, end = 10.dp)
        .height(300.dp)
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())

    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(isCreateAccount) Text(text = stringResource(id = R.string.create_acct),
            modifier = Modifier.padding(4.dp)) else Text(text = stringResource(id = R.string.login))

        EmailInput(
            emailState = email,
            enabled = !loading,
            onAction = KeyboardActions {
                passwordFocusRequest.requestFocus()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        PasswordInput(
            modifier = Modifier
                .focusRequester(passwordFocusRequest),
            passwordState = password,
            labelId = "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }

    }
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick:() -> Unit = {}
) {
    Button(onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        enabled = !loading && validInputs,
        shape = CircleShape) {
        if(loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }
}

/*
       TextField(
           value = email.value,
           onValueChange = { email.value = it },
           label = { Text("Username") },
           modifier = Modifier
               .fillMaxWidth()
               .padding(bottom = 8.dp)
       )

       TextField(
           value = password.value,
           onValueChange = { password.value = it },
           label = { Text("Password") },
           visualTransformation = PasswordVisualTransformation(),
           keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
           modifier = Modifier
               .fillMaxWidth()
               .padding(bottom = 16.dp)
       )

       Button(
           onClick = { /* Handle login logic here */ },
           modifier = Modifier
               .fillMaxWidth()
               .height(50.dp)
       ) {
           Text(text = "Login")
       }

       Text(
           text = "or sign in with:",
           fontSize = MaterialTheme.typography.bodyLarge.fontSize,
           color = Color.Gray,
           modifier = Modifier.padding(top = 16.dp)
       )

        */