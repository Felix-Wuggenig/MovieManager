package com.felixwuggenig.moviemanager.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.felixwuggenig.moviemanager.R
import com.felixwuggenig.moviemanager.ui.composables.CustomTextField
import com.felixwuggenig.moviemanager.viewmodels.SignUpViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun SignUpScreen(navController: NavController) {
    val viewModel: SignUpViewModel = getViewModel()

    val name = viewModel.nameData.observeAsState("")
    val email = viewModel.emailData.observeAsState("")
    val password = viewModel.passwordData.observeAsState("")
    val confirmPassword = viewModel.passwordConfirmData.observeAsState("")

    val emailError = viewModel.emailError.observeAsState("")
    val passwordError = viewModel.passwordError.observeAsState("")
    val confirmPasswordError = viewModel.confirmPasswordError.observeAsState("")

    SignUpView(
        name = name.value,
        email = email.value,
        password = password.value,
        confirmPassword = confirmPassword.value,
        emailError = emailError.value,
        passwordError = passwordError.value,
        confirmPasswordError = confirmPasswordError.value,
        onUpdateName = { newName -> viewModel.setNameData(newName = newName) },
        onUpdateEmail = { newEmail -> viewModel.setEmailData(newEmail = newEmail) },
        onUpdatePassword = { newPassword -> viewModel.setPasswordData(newPassword = newPassword) },
        onUpdateConfirmPassword = { newConfirmPassword ->
            viewModel.setConfirmPasswordData(
                newConfirmPassword = newConfirmPassword
            )
        },
        onSubmitClicked = {
            if (viewModel.checkData()) {
                navController.popBackStack()
                navController.navigate("home")
            }
        }
    )

}

@Composable
fun SignUpView(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    emailError: String,
    passwordError: String,
    confirmPasswordError: String,
    onUpdateName: (String) -> Unit,
    onUpdateEmail: (String) -> Unit,
    onUpdatePassword: (String) -> Unit,
    onUpdateConfirmPassword: (String) -> Unit,
    onSubmitClicked: () -> Unit
) {
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
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.FillWidth
            )
            Image(
                painter = painterResource(id = R.drawable.login_profile_picture),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .scale(3f),
                contentScale = ContentScale.Fit
            )
        }
        Column(modifier = Modifier.padding(48.dp)) {
            CustomTextField(
                text = name,
                errorText = "",
                placeholder = "Name",
                onValueChange = { newName -> onUpdateName(newName) })

            CustomTextField(
                text = email,
                errorText = emailError,
                placeholder = "E-Mail-Address*",
                onValueChange = { newEmail -> onUpdateEmail(newEmail) })

            CustomTextField(
                text = password,
                errorText = passwordError,
                placeholder = "Password*",
                isPassword = true,
                onValueChange = { newPassword -> onUpdatePassword(newPassword) }
            )

            CustomTextField(
                text = confirmPassword,
                errorText = confirmPasswordError,
                placeholder = "Confirm Password*",
                isPassword = true,
                onValueChange = { newPassword -> onUpdateConfirmPassword(newPassword) }
            )

            Button(
                onClick = { onSubmitClicked() },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .requiredHeight(50.dp)
            ) {
                Text(text = "Sign Up", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Preview
@Composable
private fun SignUpViewPreview() {
    SignUpView(
        name = "Testname",
        email = "Email",
        password = "",
        confirmPassword = "",
        emailError = "",
        passwordError = "Password cant be empty",
        confirmPasswordError = "",
        onUpdateName = {},
        onUpdateEmail = {},
        onUpdatePassword = {},
        onUpdateConfirmPassword = {},
        onSubmitClicked = {}
    )
}