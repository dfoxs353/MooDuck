package com.example.mooduck.ui.screens.detailbook.views

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.mooduck.R
import com.example.mooduck.ui.components.TextInput
import com.example.mooduck.ui.screens.detailbook.models.AddCommentState
import com.example.mooduck.ui.screens.detailbook.models.AddCommentSubState
import com.example.mooduck.ui.theme.MooDuckTheme
import com.example.mooduck.ui.theme.TintBlack
import com.example.mooduck.ui.theme.White

@Composable
fun AddCommentModal(
    onTitleChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
    onPublishClick: () -> Unit,
    onDismissRequest: () -> Unit,
    state: AddCommentSubState = AddCommentSubState.Nothing,
){
    AlertDialog(
        text = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                when(state){
                    AddCommentSubState.Nothing -> {
                        AddCommentView(onTitleChange = onTitleChange,onCommentChange = onCommentChange)
                    }
                    AddCommentSubState.Successful -> {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.adding_comment_successful)
                        )
                    }
                    AddCommentSubState.Error -> {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(id = R.string.adding_comment_error)
                        )
                    }
                    AddCommentSubState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            when(state){
                AddCommentSubState.Nothing -> {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .padding(bottom = 15.dp),
                        shape = RoundedCornerShape(size = 2.dp),
                        border = BorderStroke(1.dp, TintBlack),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TintBlack
                        ),
                        contentPadding = PaddingValues(10.dp),
                        onClick = onPublishClick,
                    ) {
                        Text(
                            text = stringResource(
                                id = R.string.publish
                            ).uppercase(),
                            style = TextStyle(
                                color = White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        )
                    }
                }
                else -> {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 15.dp)
                            .padding(bottom = 15.dp),
                        shape = RoundedCornerShape(size = 2.dp),
                        border = BorderStroke(1.dp, TintBlack),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = TintBlack
                        ),
                        contentPadding = PaddingValues(10.dp),
                        onClick = onDismissRequest,
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = stringResource(
                                id = R.string.back
                            ).uppercase(),
                            style = TextStyle(
                                color = White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun AddCommentView(
    onTitleChange: (String) -> Unit,
    onCommentChange: (String) -> Unit,
){
    Column(
        modifier = Modifier
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.add_comment),
            textAlign = TextAlign.Center
        )
        TextInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            textInputLabel = stringResource(id = R.string.header),
            onValueChanged = onTitleChange,
        )
        TextInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            textInputLabel = stringResource(id = R.string.comment),
            onValueChanged = onCommentChange,
            maxLines = 5
        )
    }
}

@Preview
@Composable
fun AddCommentModal_Preview(){
    MooDuckTheme {
        AddCommentModal(
            onCommentChange = {},
            onTitleChange = {},
            onPublishClick = {},
            onDismissRequest = {},
            state = AddCommentSubState.Loading
        )
    }
}