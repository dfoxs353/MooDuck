package com.example.mooduck.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.mooduck.R
import com.example.mooduck.ui.models.SearchWidgetState
import com.example.mooduck.ui.theme.Cian
import com.example.mooduck.ui.theme.TintBlack
import com.example.mooduck.ui.theme.White

@Composable
fun AppBar(
    searchWidgetState: SearchWidgetState,
    searchText: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    onSearchTriggered: () -> Unit,
) {
    when (searchWidgetState) {
        SearchWidgetState.OPENED -> {
            OpenedAppBar(
                text = searchText,
                onTextChange = onTextChange,
                onCloseClick = onCloseClick,
                onSearchClick = onSearchClick,
            )
        }
        SearchWidgetState.CLOSED -> {
            ClosedAppBar(
                onSearchClick = onSearchTriggered
            )
        }
    }
}

@Composable
fun ClosedAppBar(
    onSearchClick: () -> Unit
) {
    TopAppBar(
        title = {
            LogoWithText(
                logo = painterResource(id = R.drawable.duck_picture),
                title = stringResource(id = R.string.app_name)
            )
        },
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "SearchIcon",
                    tint = White
                )
            }
        },
        backgroundColor = Cian,
    )
}

@Composable
fun OpenedAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClick: (String) -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = White,
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.search),
                    color = TintBlack,
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    onClick = { onSearchClick(text) }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "SearchIcon",
                        tint = TintBlack
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    } else {
                        onCloseClick()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = TintBlack,
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = TintBlack.copy(alpha = ContentAlpha.medium),
            ),
        )
    }
}