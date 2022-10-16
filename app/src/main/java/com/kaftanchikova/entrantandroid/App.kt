package com.kaftanchikova.entrantandroid

import android.app.Application
import com.kaftanchikova.entrantandroid.repository.Repository

class App: Application() {
    val repository = Repository()
}