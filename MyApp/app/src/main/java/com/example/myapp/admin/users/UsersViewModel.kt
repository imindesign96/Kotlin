package com.example.myapp.admin.users

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.admin.total.Data
import com.example.myapp.admin.total.DataService
import com.example.myapp.admin.total.SimData
import com.example.myapp.admin.total.SimService

class UsersViewModel : ViewModel() {
    private val currentUser = MutableLiveData<UsersData>()
    private val selectedSim = MutableLiveData<SimData>()
    private val selectedSimService = MutableLiveData<SimService>()
    private val selectedData = MutableLiveData<Data>()
    private val selectedService = MutableLiveData<DataService>()
    private val price = MutableLiveData<Double>()
    val result = MutableLiveData<String>()
    val userInfo: LiveData<UsersData> get() = currentUser
    val simInfo: LiveData<SimData> get() = selectedSim
    val chosenSimService: LiveData<SimService> get() = selectedSimService
    val dataInfo: LiveData<Data> get() = selectedData
    val serviceInfo: LiveData<DataService> get() = selectedService
    val totalPrice : LiveData<Double> get() = price
    fun setUser(user: UsersData) {
        currentUser.value = user
        Log.d("BUYSIM", "SET USER ${currentUser.value}")
    }

    fun setSimData(data: SimData) {
        selectedSim.value = data
        Log.d("BUYSIM", "SET SIM ${selectedSim.value}")
    }

    fun setSim() {
        price.value = ((selectedSim.value?.simPrice?.plus(selectedData.value!!.price + selectedService.value!!.price) ?: 0.0)).toDouble()
        Log.d("BUYSIM", "SET SIM AT PRICE${price.value}")
        selectedSimService.value = SimService(selectedSim.value, selectedData.value, selectedService.value,price.value, currentUser.value?.email)
        Log.d("BUYSIM", "SET SIMSERVICE AT ${selectedSimService.value}")
        result.value = "Success"
    }

    fun setData(data : Data) {
        selectedData.value = data
        Log.d("BUYSIM", "SET DATA ${selectedData.value}")
    }

    fun setService(service: DataService) {
        selectedService.value = service
        Log.d("BUYSIM", "SET SERVICE ${selectedService.value}")
    }

    fun cancel() {
        selectedSimService.value = SimService(null, null, null, null, null)
        selectedData.value = Data.THREE
        selectedService.value = DataService.DATA
        selectedSim.value = SimData(null, null, null, null, null)
        price.value = 0.0
    }

}