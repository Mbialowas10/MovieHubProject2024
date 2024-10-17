package com.mbialowas.moviehubproject2024.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

@Composable
fun SignInScreen(context: Context, modifier: Modifier = Modifier){
    // state-level variables
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf(false) }
    val keyController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxWidth(),
            label = { Text("Email") },
            keyboardController = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = {Text("Password")},
            keyboardController = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
        )
//        if (error != null){
//            Text(
//                text =error!!,
//                color = Color.Red,
//                modifier = Modifier.padding(8.dp)
//            )
//        }
        Button(
            onClick = {
//                keyController?.hide()
//                loading = true
//                error = null
                performSignIn(email, password, context, keyboardController = keyController)
            }
        ){
            Text(text="Sign In")
        }
    }
}

private fun performSignIn(
    email: String,
    password: String,
    context: Context,
    keyboardController: SoftwareKeyboardController
) {
    val auth = FirebaseAuth.getInstance()
}
