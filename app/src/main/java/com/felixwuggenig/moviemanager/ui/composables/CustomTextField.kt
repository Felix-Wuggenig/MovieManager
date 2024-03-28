package com.felixwuggenig.moviemanager.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.felixwuggenig.moviemanager.ui.theme.ErrorColor
import com.felixwuggenig.moviemanager.ui.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    errorText: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    Surface(
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Column() {
            TextField(
                value = text,
                onValueChange = { newName ->
                    onValueChange(newName)
                },
                label = null,
                placeholder = {
                    Text(
                        placeholder,
                        color = TextColor
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                visualTransformation = if (!isPassword || passwordVisibility) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    if (isPassword && text.isNotEmpty()) {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            if (passwordVisibility) {
                                Icon(Icons.Filled.VisibilityOff, contentDescription = null)
                            } else {
                                Icon(Icons.Filled.Visibility, contentDescription = null)
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                )
            )
            if (errorText.isNotEmpty()) {
                Text(
                    text = errorText,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp),
                    color = ErrorColor,
                    fontSize = 12.sp
                )
            }
        }
    }


}