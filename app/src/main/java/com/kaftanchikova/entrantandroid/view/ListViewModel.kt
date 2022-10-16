package com.kaftanchikova.entrantandroid.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaftanchikova.entrantandroid.model.Entrant
import com.kaftanchikova.entrantandroid.repository.EntrantsListener
import com.kaftanchikova.entrantandroid.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _entrants = MutableLiveData<List<Entrant>>()
    val entrants: LiveData<List<Entrant>> = _entrants

    private val listener: EntrantsListener = {
        _entrants.value = it
    }

    init {
        loadEntrant()
    }

    override fun onCleared() {
        super.onCleared()
        repository.removeListener(listener)
    }

    fun loadEntrant() {
        repository.addListener(listener)
    }

    fun getEntrants(){
        viewModelScope.launch {
            listener(repository.getEntrants())
        }
    }


    fun deleteEntrant(entrant: Entrant) {
        viewModelScope.launch {
            listener(repository.deleteEntrant(entrant))
        }
    }

    fun enrollEntrant(entrant: Entrant){
        viewModelScope.launch {
            listener(repository.enrollEntrant(entrant))
        }

    }
}