package com.kaftanchikova.entrantandroid.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaftanchikova.entrantandroid.model.Entrant
import com.kaftanchikova.entrantandroid.repository.Repository
import kotlinx.coroutines.launch

class TheFormModel (
    private val repository: Repository
) : ViewModel() {

    val myEntrant: MutableLiveData<Entrant> = MutableLiveData()

    fun addEntrant(entrant: Entrant){
        viewModelScope.launch {
            repository.addEntrant(entrant)
        }
    }
}