package com.felixwuggenig.moviemanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.felixwuggenig.moviemanager.ui.theme.ErrorColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }

    val passwordFocusRequester = remember { FocusRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            isError = nameError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions {
                if (name.isBlank()) {
                    nameError = "Name cannot be empty"
                } else {
                    nameError = ""
                }
            }
        )
        if (nameError.isNotEmpty()) {
            Text(text = nameError, color = ErrorColor)
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                if (email.isBlank()) {
                    emailError = "Email cannot be empty"
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError = "Invalid email format"
                } else {
                    emailError = ""
                }
            },
            label = { Text("Email") },
            singleLine = true,
            isError = emailError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions {
                if (email.isBlank()) {
                    emailError = "Email cannot be empty"
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError = "Invalid email format"
                } else {
                    emailError = ""
                }
            }
        )
        if (emailError.isNotEmpty()) {
            Text(text = emailError, color = ErrorColor)
        }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            isError = passwordError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions {
                if (password.isBlank()) {
                    passwordError = "Password cannot be empty"
                } else if (password.length < 6) {
                    passwordError = "Password must be at least 6 characters long"
                } else {
                    passwordError = ""
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            trailingIcon = {
                if (password.isNotEmpty()) {
                    IconButton(onClick = { password = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            }
        )
        if (passwordError.isNotEmpty()) {
            Text(text = passwordError, color = ErrorColor)
        }

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            isError = confirmPasswordError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions {
                if (confirmPassword != password) {
                    confirmPasswordError = "Passwords do not match"
                } else {
                    confirmPasswordError = ""
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            trailingIcon = {
                if (confirmPassword.isNotEmpty()) {
                    IconButton(onClick = { confirmPassword = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = null)
                    }
                }
            }
        )
        if (confirmPasswordError.isNotEmpty()) {
            Text(text = confirmPasswordError, color = ErrorColor)
        }

        Button(
            onClick = {
                // Perform sign-up logic here
                if (name.isBlank()) nameError = "Name cannot be empty"
                if (email.isBlank()) emailError = "Email cannot be empty"
                if (password.isBlank()) passwordError = "Password cannot be empty"
                if (confirmPassword != password) confirmPasswordError = "Passwords do not match"

                if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword == password) {
                    navController.navigate("home")
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = "Sign Up")
        }
    }
}