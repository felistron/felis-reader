package com.felisreader.library.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.felisreader.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangadexSignIn(
    setVisible: (visible: Boolean) -> Unit,
    signIn: (
        username: String, password: String,
        clientId: String, clientSecret: String,
        remember: Boolean) -> Unit
) {
    val uriHandler = LocalUriHandler.current

    var usernameText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var clientIdText by remember { mutableStateOf("") }
    var clientSecretText by remember { mutableStateOf("") }

    var nextDialog by remember { mutableStateOf(false) }
    var noticeChecked by remember { mutableStateOf(false) }
    var rememberChecked by remember { mutableStateOf(false) }

    if (!nextDialog) {
        AlertDialog(
            onDismissRequest = { setVisible(false) }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                tonalElevation = 4.dp
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
                            Icon(Icons.Default.OpenInNew, contentDescription = null)
                        }
                    )
                    Row(
                        modifier = Modifier
                            .clickable(onClick = { noticeChecked = !noticeChecked })
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = noticeChecked, onCheckedChange = { noticeChecked = it})
                        Text(stringResource(id = R.string.signin_notice_agreement))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Button(
                            enabled = noticeChecked,
                            onClick = { nextDialog = true }
                        ) {
                            Text(stringResource(id = R.string.ui_continue))
                        }
                    }
                }
            }
        }
    } else {
        AlertDialog(
            onDismissRequest = { setVisible(false) }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground,
                tonalElevation = 4.dp
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
                        value = usernameText,
                        onValueChange = { usernameText = it },
                        placeholder = { Text(stringResource(id = R.string.username)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        value = passwordText,
                        onValueChange = { passwordText = it },
                        placeholder = { Text(stringResource(id = R.string.password)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Next,
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        value = clientIdText,
                        onValueChange = { clientIdText = it },
                        placeholder = { Text(stringResource(id = R.string.client_id)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next,
                        ),
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        value = clientSecretText,
                        onValueChange = { clientSecretText = it },
                        placeholder = { Text(stringResource(id = R.string.client_id)) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = false,
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Row(
                        modifier = Modifier
                            .clickable(onClick = { rememberChecked = !rememberChecked })
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(checked = rememberChecked, onCheckedChange = { rememberChecked = it})
                        Text(stringResource(id = R.string.singin_remember))
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                            ),
                            onClick = {
                                signIn(usernameText, passwordText, clientIdText, clientSecretText, rememberChecked)
                            }
                        ) {
                            Text(stringResource(id = R.string.signin))
                        }
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            ),
                            onClick = { setVisible(false) }
                        ) {
                            Text(stringResource(id = R.string.ui_cancel))
                        }
                    }
                }
            }
        }
    }
}