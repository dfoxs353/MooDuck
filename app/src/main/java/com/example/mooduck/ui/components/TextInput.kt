package com.example.mooduck.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    textInputLabel: String,
    secureText: Boolean = false,
    onValueChanged: (String) -> Unit,
    value: String
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        label = {
            Text(
                text = textInputLabel,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                )
            )
        },
        singleLine = true,
        onValueChange = onValueChanged,
        shape = RoundedCornerShape(size = 2.dp),
        visualTransformation = if (secureText) PasswordVisualTransformation() else
            VisualTransformation.None,
    )
}