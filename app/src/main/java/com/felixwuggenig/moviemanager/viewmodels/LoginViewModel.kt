package com.felixwuggenig.moviemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixwuggenig.moviemanager.data.SharedPreferences

class LoginViewModel(val sharedPreferences: SharedPreferences) : ViewModel() {

    private val mutableNameData = MutableLiveData<String>()
    val nameData: LiveData<String> = mutableNameData

    private val mutableEmailData = MutableLiveData<String>()
    val emailData: LiveData<String> = mutableEmailData

    private val mutablePasswordData = MutableLiveData<String>()
    val passwordData: LiveData<String> = mutablePasswordData

    private val mutablePasswordConfirmData = MutableLiveData<String>()
    val passwordConfirmData: LiveData<String> = mutablePasswordConfirmData

    private val mutableEmailError = MutableLiveData<String>()
    val emailError: LiveData<String> = mutableEmailError

    private val mutablePasswordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = mutablePasswordError

    private val mutableConfirmPasswordError = MutableLiveData<String>()
    val confirmPasswordError: LiveData<String> = mutableConfirmPasswordError


    fun setNameData(newName: String) {
        mutableNameData.value = newName
    }

    fun setEmailData(newEmail: String) {
        mutableEmailData.value = newEmail
    }

    fun setPasswordData(newPassword: String) {
        mutablePasswordData.value = newPassword
    }

    fun setConfirmPasswordData(newConfirmPassword: String) {
        mutablePasswordConfirmData.value = newConfirmPassword
    }

    fun checkData(): Boolean {
        var isValid = true

        if (emailData.value.isNullOrEmpty()) {
            mutableEmailError.value = "Email cannot be empty"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailData.value).matches()) {
            mutableEmailError.value = "Invalid email format"
            isValid = false
        } else {
            mutableEmailError.value = ""
        }

        if (passwordData.value.isNullOrEmpty()) {
            mutablePasswordError.value = "Password cannot be empty"
            isValid = false
        } else if ((passwordData.value?.length ?: 0) < 6) {
            mutablePasswordError.value = "Password must be at least 6 characters long"
            isValid = false
        } else {
            mutablePasswordError.value = ""
        }

        if (passwordConfirmData.value != passwordData.value) {
            mutableConfirmPasswordError.value = "Passwords do not match!"
            isValid = false
        } else {
            mutableConfirmPasswordError.value = ""
        }
        if (isValid) {
            sharedPreferences.saveName(mutableNameData.value.orEmpty())
        }
        return isValid
    }


}