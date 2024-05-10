package com.example.mooduck.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mooduck.ui.theme.Cian
import com.example.mooduck.ui.theme.TintBlack
import com.example.mooduck.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    textInputLabel: String,
    secureText: Boolean = false,
    maxLines: Int = 1,
    onValueChanged: (String) -> Unit,
) {
    var text by remember {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier,
        value = text,
        label = {
            Text(
                text = textInputLabel,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                )
            )
        },
        singleLine = maxLines == 1,
        onValueChange = {
            text = it
            onValueChanged(text)
        },
        maxLines = maxLines,
        shape = RoundedCornerShape(size = 2.dp),
        visualTransformation = if (secureText) PasswordVisualTransformation() else
            VisualTransformation.None,
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Cian,
            focusedIndicatorColor = Cian,
            focusedContainerColor = White,
            unfocusedContainerColor = White,
            focusedLabelColor = Cian,
            cursorColor = Cian
        )
    )
}