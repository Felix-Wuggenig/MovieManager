package com.felixwuggenig.moviemanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.felixwuggenig.moviemanager.ui.theme.ErrorColor
import com.felixwuggenig.moviemanager.viewmodels.LoginViewModel
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    val viewModel: LoginViewModel = getViewModel()
    var name = viewModel.nameData.observeAsState("")
    var email = viewModel.emailData.observeAsState("")
    var password = viewModel.passwordData.observeAsState("")
    var confirmPassword = viewModel.passwordConfirmData.observeAsState("")

    var nameError = viewModel.nameError.observeAsState("")
    var emailError = viewModel.emailError.observeAsState("")
    var passwordError = viewModel.passwordError.observeAsState("")
    var confirmPasswordError = viewModel.passwordConfirmError.observeAsState("")

    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordConfirmVisibility by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name.value,
            onValueChange = { newName ->
                viewModel.setNameData(newName)
            },
            label = { Text("Name") },
            singleLine = true,
            isError = nameError.value.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
        )
        if (nameError.value.isNotEmpty()) {
            Text(text = nameError.value, color = ErrorColor)
        }

        OutlinedTextField(
            value = email.value,
            onValueChange = { newEmail ->
                viewModel.setEmailData(newEmail)
            },
            label = { Text("Email") },
            singleLine = true,
            isError = emailError.value.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
        if (emailError.value.isNotEmpty()) {
            Text(text = emailError.value, color = ErrorColor)
        }

        OutlinedTextField(
            value = password.value,
            onValueChange = { newPassword ->
                viewModel.setPasswordData(newPassword)
            },
            label = { Text("Password") },
            visualTransformation = if (passwordVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            singleLine = true,
            isError = passwordError.value.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            trailingIcon = {
                if (password.value.isNotEmpty()) {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(Icons.Default.Home, contentDescription = null)
                    }
                }
            }
        )
        if (passwordError.value.isNotEmpty()) {
            Text(text = passwordError.value, color = ErrorColor)
        }

        OutlinedTextField(
            value = confirmPassword.value,
            onValueChange = { newPasswordConfirm ->
                viewModel.setPasswordConfirmData(newPasswordConfirm)
            },
            label = { Text("Confirm Password") },
            visualTransformation = if (passwordConfirmVisibility) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            singleLine = true,
            isError = confirmPasswordError.value.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            trailingIcon = {
                if (confirmPassword.value.isNotEmpty()) {
                    IconButton(onClick = {
                        passwordConfirmVisibility = !passwordConfirmVisibility
                    }) {
                        Icon(Icons.Default.Home, contentDescription = null)
                    }
                }
            }
        )
        if (confirmPasswordError.value.isNotEmpty()) {
            Text(text = confirmPasswordError.value, color = ErrorColor)
        }

        Button(
            onClick = {
                navController.navigate("home")

                if (viewModel.checkData()) {
                    navController.navigate("home")
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign Up")
        }
    }
}