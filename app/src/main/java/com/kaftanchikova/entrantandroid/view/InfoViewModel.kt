package com.kaftanchikova.entrantandroid.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaftanchikova.entrantandroid.exceptions.EntrantNotFoundException
import com.kaftanchikova.entrantandroid.model.Entrant
import com.kaftanchikova.entrantandroid.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InfoViewModel(
//    private val entrantService: EntrantService
    private val repository: Repository
) : ViewModel() {

    private val _infoDetails = MutableLiveData<Entrant>()
    val infoDetails: LiveData<Entrant> = _infoDetails

    fun loadEntrant(entrantId: Long){
        if (_infoDetails.value != null) return
        try {
            _infoDetails.value = repository.getById(entrantId)
        } catch (e: EntrantNotFoundException){
            e.printStackTrace()
        }

    }

    fun deleteEntrant() {
        val entrantDetails = this.infoDetails.value ?: return
        viewModelScope.launch {
            repository.deleteEntrant(entrantDetails)
        }
    }

    fun enrollEntrant() {
        val entrantDetails = this.infoDetails.value ?: return
        viewModelScope.launch {
            repository.enrollEntrant(entrantDetails)
        }
    }

}