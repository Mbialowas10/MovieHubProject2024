package com.mbialowas.moviehubproject2024

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.mbialowas.moviehubproject2024.screens.SignInScreen
import com.mbialowas.moviehubproject2024.ui.theme.MovieHubProject2024Theme

class SignInActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            MovieHubProject2024Theme {
                Scaffold { innerPadding ->
                    val context: Context = applicationContext
                    SignInScreen(context, Modifier.padding(innerPadding))
                }
            }
        }
    }
}