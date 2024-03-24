package com.felixwuggenig.moviemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel() : ViewModel() {

    private val mutableNameData = MutableLiveData<String>()
    val nameData: LiveData<String> = mutableNameData

    private val mutableEmailData = MutableLiveData<String>()
    val emailData: LiveData<String> = mutableEmailData

    private val mutablePasswordData = MutableLiveData<String>()
    val passwordData: LiveData<String> = mutablePasswordData

    private val mutablePasswordConfirmData = MutableLiveData<String>()
    val passwordConfirmData: LiveData<String> = mutablePasswordConfirmData

    private val mutableNameError = MutableLiveData<String>()
    val nameError: LiveData<String> = mutableNameError

    private val mutableEmailError = MutableLiveData<String>()
    val emailError: LiveData<String> = mutableEmailError

    private val mutablePasswordError = MutableLiveData<String>()
    val passwordError: LiveData<String> = mutablePasswordError

    private val mutablePasswordConfirmError = MutableLiveData<String>()
    val passwordConfirmError: LiveData<String> = mutablePasswordConfirmError


    fun setNameData(newName: String) {
        mutableNameData.value = newName
    }

    fun setEmailData(newEmail: String) {
        mutableEmailData.value = newEmail
    }

    fun setPasswordData(newPassword: String) {
        mutablePasswordData.value = newPassword
    }

    fun setPasswordConfirmData(newPasswordConfirm: String) {
        mutablePasswordConfirmData.value = newPasswordConfirm
    }

    fun checkData(): Boolean {
        var isValid = true
        if (nameData.value.isNullOrEmpty()) {
            mutableNameError.value = "Name cannot be empty"
            isValid = false
        } else {
            mutableNameError.value = ""
        }

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
            mutablePasswordConfirmError.value = "Passwords do not match!"
            isValid = false
        } else {
            mutablePasswordConfirmError.value = ""
        }
        return isValid
    }


}