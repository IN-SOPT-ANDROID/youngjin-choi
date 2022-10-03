package org.sopt.sample

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignViewModel : ViewModel() {
    val id = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val mbti = MutableLiveData<Mbti>()

    val isValidSignInput = MediatorLiveData<Boolean>().apply {
        addSourceList(id, password, name) { checkValidLoginInput() }
    }

    private fun checkValidLoginInput(): Boolean {
        if (id.value.isNullOrBlank() || id.value.isNullOrBlank() || password.value.isNullOrBlank()) return false
        return id.value!!.length in 6..10 && password.value!!.length in 8..12
    }
}