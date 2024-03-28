package com.felixwuggenig.moviemanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.ui.composables.CustomTextField
import com.felixwuggenig.moviemanager.viewmodels.LoginViewModel
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {
    val viewModel: LoginViewModel = getViewModel()

    val onSubmitClicked = {
        if (viewModel.checkData()) {
            navController.popBackStack()
            navController.navigate("home")
        }
        navController.navigate("home")

    }
    val name = viewModel.nameData.observeAsState("")
    val email = viewModel.emailData.observeAsState("")
    val password = viewModel.passwordData.observeAsState("")
    val confirmPassword = viewModel.passwordConfirmData.observeAsState("")

    val emailError = viewModel.emailError.observeAsState("")
    val passwordError = viewModel.passwordError.observeAsState("")
    val confirmPasswordError = viewModel.confirmPasswordError.observeAsState("")

    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordConfirmVisibility by remember { mutableStateOf(false) }


    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_header),
                contentDescription = null, // Provide appropriate content description
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth // Or another scaling option you prefer
            )
            Image(
                painter = painterResource(id = R.drawable.login_profile_picture),
                contentDescription = null, // Provide appropriate content description
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .scale(3f),
                contentScale = ContentScale.Fit
            )
        }
        Column(modifier = Modifier.padding(48.dp)) {
            CustomTextField(
                text = name.value,
                errorText = "",
                placeholder = "Name",
                onValueChange = { newName -> viewModel.setNameData(newName = newName) })

            CustomTextField(
                text = email.value,
                errorText = emailError.value,
                placeholder = "E-Mail-Address*",
                onValueChange = { newEmail -> viewModel.setEmailData(newEmail = newEmail) })

            CustomTextField(
                text = password.value,
                errorText = passwordError.value,
                placeholder = "Password*",
                isPassword = true,
                onValueChange = { newPassword -> viewModel.setPasswordData(newPassword = newPassword) }
            )

            CustomTextField(
                text = confirmPassword.value,
                errorText = confirmPasswordError.value,
                placeholder = "Confirm Password*",
                isPassword = true,
                onValueChange = { newPassword -> viewModel.setConfirmPasswordData(newConfirmPassword = newPassword) }
            )

            Button(
                onClick = { onSubmitClicked() },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(50.dp)
            ) {
                Text(text = "Sign Up", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}