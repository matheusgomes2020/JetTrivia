package com.example.jettrivia

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Govern the entire app
@HiltAndroidApp
class TriviaApplication: Application() {
}