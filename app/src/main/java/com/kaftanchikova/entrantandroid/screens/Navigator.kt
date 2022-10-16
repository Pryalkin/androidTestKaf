package com.kaftanchikova.entrantandroid.screens

import com.kaftanchikova.entrantandroid.model.Entrant

interface Navigator {
    fun showInfo(entrant: Entrant)
    fun goBack()
    fun toast(messageRes: Int)
}