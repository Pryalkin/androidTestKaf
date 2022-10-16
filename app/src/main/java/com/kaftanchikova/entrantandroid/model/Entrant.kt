package com.kaftanchikova.entrantandroid.model

data class Entrant(
    val id: Long,
    val name: String,
    val surname: String,
    val patronymic: String,
    val age: Int,
    val email: String,
    val phone: String,
    val info: String,
    val status: Boolean
)
