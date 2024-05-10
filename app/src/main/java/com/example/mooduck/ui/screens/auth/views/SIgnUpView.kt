package com.example.mooduck.ui.screens.auth.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mooduck.R
import com.example.mooduck.ui.components.CircularDialog
import com.example.mooduck.ui.components.LogoWithText
import com.example.mooduck.ui.components.TextInput
import com.example.mooduck.ui.theme.Cian
import com.example.mooduck.ui.theme.TintBlack
import com.example.mooduck.ui.theme.White

@Composable
fun SignUpView(
    modifier: Modifier = Modifier,
    userNameValue: String,
    emailValue: String,
    passwordValue: String,
    repeatPasswordValue: String,
    onUserNameValueChanged: (String) -> Unit,
    onEmailValueChanged: (String) -> Unit,
    onPasswordValueChanged: (String) -> Unit,
    onRepeatPasswordValueChanged: (String) -> Unit,
    signInClick: () -> Unit,
    signUpCLick: () -> Unit,
    isShowModalMail: Boolean = false,
    isProgress: Boolean = false
) {
    Box(
        modifier = modifier,
    ) {
        AnimatedVisibility(visible = isProgress) {
            CircularDialog(titleText = stringResource(id = R.string.registration))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Cian),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoWithText(
                modifier = Modifier.padding(bottom = 30.dp, top = 20.dp),
                logo = painterResource(id = R.drawable.duck_picture),
                title = stringResource(id = R.string.app_name),
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 32.dp, vertical = 40.dp),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(id = R.string.registration),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )

                TextInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    textInputLabel = stringResource(id = R.string.user_name),
                    onValueChanged = onUserNameValueChanged,
                )

                TextInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    textInputLabel = stringResource(id = R.string.mail),
                    onValueChanged = onEmailValueChanged,
                )

                TextInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    textInputLabel = stringResource(id = R.string.password),
                    onValueChanged = onPasswordValueChanged,
                    secureText = true,
                )

                TextInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    textInputLabel = stringResource(id = R.string.repeat_password),
                    onValueChanged = onRepeatPasswordValueChanged,
                    secureText = true,
                )



                Button(
                    modifier = Modifier
                        .padding(top = 25.dp),
                    shape = RoundedCornerShape(size = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White
                    ),
                    border = BorderStroke(2.dp, TintBlack),
                    onClick = signUpCLick,
                    contentPadding = PaddingValues(vertical = 14.dp, horizontal = 15.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.signup),
                        style = TextStyle(
                            color = TintBlack,
                            fontSize = 17.sp,
                            fontWeight = FontWeight(600),
                        )
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.have_account),
                    style = TextStyle(
                        fontSize = 17.sp,
                        fontWeight = FontWeight(400),
                        color = White,
                        textAlign = TextAlign.Center,
                    )
                )

                Button(
                    modifier = Modifier
                        .padding(top = 20.dp),
                    shape = RoundedCornerShape(size = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White
                    ),
                    onClick = signInClick,
                    contentPadding = PaddingValues(vertical = 14.dp, horizontal = 45.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.login),
                        style = TextStyle(
                            color = Cian,
                            fontSize = 17.sp,
                            fontWeight = FontWeight(600),
                        )
                    )
                }

                Spacer(
                    modifier = Modifier
                        .padding(30.dp)
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(White)
                )
            }


        }
    }

    AnimatedVisibility(visible = isShowModalMail) {
        ModalMail(onConfirmClick = signInClick)
    }
}

@Preview
@Composable
fun SignUpView_Preview() {
    SignUpView(
        emailValue = "",
        passwordValue = "",
        userNameValue = "",
        repeatPasswordValue = "",
        onEmailValueChanged = {},
        onPasswordValueChanged = {},
        onRepeatPasswordValueChanged = {},
        onUserNameValueChanged = {},
        signInClick = {},
        signUpCLick = {},
        isShowModalMail = false,
        isProgress = false,
    )
}

@Composable
fun ModalMail(
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        modifier = modifier,
        shape = RoundedCornerShape(2.dp),
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.sign_up_almost_complete),
                textAlign = TextAlign.Center,
                style = TextStyle(),
                maxLines = 2,
                minLines = 2,
                fontSize = 18.sp,
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(id = R.string.check_mail),
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(200),
                )
                Icon(
                    modifier = Modifier.padding(top = 15.dp),
                    painter = painterResource(id = R.drawable.long_mail),
                    contentDescription = "",
                    tint = TintBlack,
                )
            }
        },
        onDismissRequest = onConfirmClick,
        confirmButton = {
            Button(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(size = 2.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TintBlack
                ),
                onClick = onConfirmClick,
                contentPadding = PaddingValues(vertical = 14.dp, horizontal = 45.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    style = TextStyle(
                        color = White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight(600),
                    )
                )
            }
        },
    )

}

@Preview
@Composable
fun ModalMail_Preview() {
    ModalMail(
        modifier = Modifier,
        onConfirmClick = {},
    )
}