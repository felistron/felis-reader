package com.felisreader.user.presentation.signin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.felisreader.R
import com.felisreader.core.presentation.Loading
import kotlinx.coroutines.delay

@Composable
fun SignInDialog(
    viewModel: SignInViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    onCancel: () -> Unit,
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent

    when (state.currentStep) {
        is SignInStep.Notice -> {
            NoticeDialog(
                noticeChecked = state.noticeChecked,
                onCheckedChange = { onEvent(SignInEvent.OnCheckedChange(it)) },
                onNextDialog = { onEvent(SignInEvent.StepChange(SignInStep.Form(false))) },
                onDismissRequest = onCancel
            )
        }
        is SignInStep.Form -> {
            MangadexSignInDialog(
                username = state.username,
                onUsernameChange = { onEvent(SignInEvent.OnUsernameChange(it)) },
                password = state.password,
                onPasswordChange = { onEvent(SignInEvent.OnPasswordChange(it)) },
                clientId = state.clientId,
                onClientIdChange = { onEvent(SignInEvent.OnClientIdChange(it)) },
                clientSecret = state.clientSecret,
                onClientSecretChange = { onEvent(SignInEvent.OnClientSecretChange(it)) },
                onDismissRequest = onCancel,
                onSignIn = {
                    onEvent(SignInEvent.StepChange(SignInStep.Processing(false)))

                    onEvent(SignInEvent.SignIn(
                        onSuccess = {
                            onEvent(SignInEvent.StepChange(SignInStep.Processing(true)))
                            delay(1000)
                            onSuccess()
                        },
                        onFailure = {
                            onEvent(SignInEvent.StepChange(SignInStep.Form(true)))
                        }
                    ))
                },
                error = state.currentStep.error
            )
        }
        is SignInStep.Processing -> {
            ProcessingDialog(
                success = state.currentStep.success
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcessingDialog(
    success: Boolean,
) {
    AlertDialog(
        onDismissRequest = { }
    ) {
        if (success) {
            Icon(
                modifier = Modifier.size(64.dp),
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )
        } else {
            Loading(modifier = Modifier, size = 64)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangadexSignInDialog(
    username: String,
    onUsernameChange: (username: String) -> Unit,
    password: String,
    onPasswordChange: (password: String) -> Unit,
    clientId: String,
    onClientIdChange: (clientId: String) -> Unit,
    clientSecret: String,
    onClientSecretChange: (clientSecret: String) -> Unit,
    onDismissRequest: () -> Unit,
    onSignIn: () -> Unit,
    error: Boolean,
) {
    val fieldsFilled =
        username.isNotBlank() && password.isNotBlank() &&
        clientId.isNotBlank() && clientSecret.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.ui_signin_mangadex),
                    style = MaterialTheme.typography.titleLarge
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = username,
                    onValueChange = onUsernameChange,
                    placeholder = { Text(stringResource(id = R.string.username)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = password,
                    onValueChange = onPasswordChange,
                    placeholder = { Text(stringResource(id = R.string.password)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = clientId,
                    onValueChange = onClientIdChange,
                    placeholder = { Text(stringResource(id = R.string.client_id)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    value = clientSecret,
                    onValueChange = onClientSecretChange,
                    placeholder = { Text(stringResource(id = R.string.client_secret)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
                AnimatedVisibility(error) {
                    if (error) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = stringResource(id = R.string.wrong_credentials),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        enabled = fieldsFilled,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                        onClick = onSignIn
                    ) {
                        Text(stringResource(id = R.string.signin))
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        ),
                        onClick = onDismissRequest
                    ) {
                        Text(stringResource(id = R.string.ui_cancel))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoticeDialog(
    noticeChecked: Boolean,
    onCheckedChange: (checked: Boolean) -> Unit,
    onNextDialog: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val uriHandler = LocalUriHandler.current

    AlertDialog(
        onDismissRequest = onDismissRequest
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Text(
                    text = stringResource(id = R.string.notice),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(stringResource(id = R.string.signin_notice_body))
                AssistChip(
                    onClick = { uriHandler.openUri("https://api.mangadex.org/docs/02-authentication/personal-clients/") },
                    label = { Text(stringResource(id = R.string.get_personal_client)) },
                    trailingIcon = {
                        Icon(Icons.AutoMirrored.Filled.OpenInNew, contentDescription = null)
                    }
                )
                Row(
                    modifier = Modifier
                        .clickable(onClick = { onCheckedChange(!noticeChecked) })
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = noticeChecked, onCheckedChange = onCheckedChange)
                    Text(stringResource(id = R.string.signin_notice_agreement))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        enabled = noticeChecked,
                        onClick = onNextDialog
                    ) {
                        Text(stringResource(id = R.string.ui_continue))
                    }
                }
            }
        }
    }
}