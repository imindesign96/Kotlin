package com.example.myapp.admin.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val data = MutableLiveData<String>()
    val selected = MutableLiveData<String>()
}