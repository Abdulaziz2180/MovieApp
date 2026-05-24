package com.example.movieapp2.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp2.data.datastore.ProfileData
import com.example.movieapp2.data.datastore.ProfileDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileDataStore: ProfileDataStore
) : ViewModel() {

    private val _profileData = MutableStateFlow(ProfileData())
    val profileData: StateFlow<ProfileData> = _profileData.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            profileDataStore.profileFlow.collect { data ->
                _profileData.value = data
            }
        }
    }

    fun updateProfile(fullName: String, resumeUrl: String, avatarUri: String) {
        viewModelScope.launch {
            profileDataStore.saveProfile(fullName, resumeUrl, avatarUri)
        }
    }

    fun updateAvatar(uri: String) {
        android.util.Log.d("PROFILE", "Сохраняем аватар: $uri")
        viewModelScope.launch {
            profileDataStore.saveAvatar(uri)
        }
    }
}