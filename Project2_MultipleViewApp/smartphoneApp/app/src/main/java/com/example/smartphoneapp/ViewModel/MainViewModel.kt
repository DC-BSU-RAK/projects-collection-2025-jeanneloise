package com.example.smartphoneapp.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.smartphoneapp.Model.ItemsModel
import com.example.smartphoneapp.Repository.MainRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository(application.applicationContext)

    // Update this method to accept categoryId as String
    fun loadFiltered(categoryId: String): LiveData<List<ItemsModel>> {
        val liveData = MutableLiveData<List<ItemsModel>>()
        liveData.value = repository.getItemsByCategory(categoryId)
        return liveData
    }
}

