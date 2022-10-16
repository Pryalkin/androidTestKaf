package com.kaftanchikova.entrantandroid.repository

import com.kaftanchikova.entrantandroid.api.RetrofitInstance
import com.kaftanchikova.entrantandroid.exceptions.EntrantNotFoundException
import com.kaftanchikova.entrantandroid.model.Entrant

typealias EntrantsListener = (entrants: List<Entrant>) -> Unit

class Repository {

    private var entrants = mutableListOf<Entrant>()
    private val listeners = mutableSetOf<EntrantsListener>()

    suspend fun addEntrant(entrant: Entrant){
        RetrofitInstance.api.addEntrant(entrant)
    }

    suspend fun getEntrants(): MutableList<Entrant>{
        entrants = RetrofitInstance.api.getEntrants().body() as MutableList<Entrant>
        return entrants
    }


    fun getById(id: Long): Entrant{
        val entrant = entrants.firstOrNull { it.id == id } ?: throw EntrantNotFoundException()
        return entrant
    }

    suspend fun enrollEntrant(entrant: Entrant): MutableList<Entrant>{
        entrants = RetrofitInstance.api.enrollEntrant(entrant.id).body() as MutableList<Entrant>
        notifyChanges()
        return entrants
    }

    suspend fun deleteEntrant(entrant: Entrant): MutableList<Entrant>{
        entrants = RetrofitInstance.api.deleteEntrant(entrant.id).body() as MutableList<Entrant>
        notifyChanges()
        return entrants
    }

    fun addListener(listener: EntrantsListener){
        listeners.add(listener)
        listener.invoke(entrants)
    }

    fun removeListener(listener: EntrantsListener){
        listeners.remove(listener)
    }

    private fun notifyChanges(){
        listeners.forEach {it.invoke(entrants)}
    }
}