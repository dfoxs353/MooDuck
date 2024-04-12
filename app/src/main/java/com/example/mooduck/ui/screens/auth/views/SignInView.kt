package com.example.mooduck.ui.screens.auth.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mooduck.MooDuckApp
import com.example.mooduck.R
import com.example.mooduck.ui.components.LogoWithText
import com.example.mooduck.ui.components.TextInput
import com.example.mooduck.ui.theme.Cian
import com.example.mooduck.ui.theme.MooDuckTheme
import com.example.mooduck.ui.theme.TintBlack
import com.example.mooduck.ui.theme.White

@Composable
fun SignInView(
    modifier: Modifier = Modifier,
    emailValue: String,
    passwordValue: String,
    onEmailValueChanged: (String) -> Unit,
    onPasswordValueChanged: (String) -> Unit,
    forgotPasswordClick: () -> Unit,
    signInClick: () -> Unit,
    signUpCLick: () -> Unit,
){
    Box(
        modifier = modifier,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Cian),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LogoWithText(
                logo = painterResource(id = R.drawable.duck_picture),
                title = stringResource(id = R.string.app_name),
                modifier = Modifier.padding(bottom = 30.dp)
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
                    text = stringResource(id = R.string.enter),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )

                TextInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 25.dp),
                    textInputLabel = stringResource(id = R.string.mail_example),
                    onValueChanged =  onEmailValueChanged ,
                    value = emailValue,
                )

                TextInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    textInputLabel = stringResource(id = R.string.password),
                    onValueChanged = onPasswordValueChanged,
                    secureText = true,
                    value = passwordValue,
                )

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = forgotPasswordClick,
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = stringResource(id = R.string.forgot_password),
                        style = TextStyle(
                            fontSize = 17.sp,
                            fontWeight = FontWeight(200),
                            color = TintBlack,
                            textAlign = TextAlign.Center,
                        )
                    )
                }

                Button(
                    modifier = Modifier
                        .padding(top = 25.dp),
                    shape = RoundedCornerShape(size = 2.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = White
                    ),
                    border = BorderStroke(2.dp, TintBlack),
                    onClick =  signInClick ,
                    contentPadding = PaddingValues(vertical = 14.dp, horizontal = 64.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.login ),
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
                    .padding(vertical = 25.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.dont_account),
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
                    onClick =  signUpCLick ,
                    contentPadding = PaddingValues(vertical = 14.dp, horizontal = 33.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.signup ),
                        style = TextStyle(
                            color = Cian,
                            fontSize = 17.sp,
                            fontWeight = FontWeight(600),
                        )
                    )
                }

                Spacer(modifier = Modifier
                    .padding(30.dp)
                    .height(1.dp)
                    .fillMaxWidth()
                    .background(White)
                )
            }


        }
    }
}

@Preview
@Composable
fun SignInView_Preview(){
    SignInView(
        emailValue = "",
        passwordValue = "",
        onEmailValueChanged = {},
        onPasswordValueChanged = {},
        signInClick = {},
        signUpCLick = {},
        forgotPasswordClick = {},
    )
}