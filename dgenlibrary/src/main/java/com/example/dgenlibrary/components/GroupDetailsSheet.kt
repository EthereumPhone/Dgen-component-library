package com.example.dgenlibrary.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.model.GroupMember
import com.example.dgenlibrary.ui.backgrounds.DgenNavigationBackground
import com.example.dgenlibrary.ui.backgrounds.FadeDirection
import com.example.dgenlibrary.ui.backgrounds.FadeEdge
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenTurqoise
import com.example.dgenlibrary.ui.theme.label_fontSize

@Composable
fun GroupDetailsSheet(
    groupName: String,
    groupDescription: String?,
    members: List<GroupMember>,
    primaryColor: Color,
    secondaryColor: Color,
    onBackClick: () -> Unit,
    onUpdateGroupName: (String) -> Unit,
    onUpdateGroupDescription: (String) -> Unit,
    onAddMembers: () -> Unit,
    onRemoveMember: (String) -> Unit,
    onLeaveGroup: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditMode by remember { mutableStateOf(false) }
    var showLeaveConfirmDialog by remember { mutableStateOf(false) }
    var showRemoveMemberDialog by remember { mutableStateOf<GroupMember?>(null) }

    var editNameState by remember(isEditMode) {
        mutableStateOf(TextFieldValue(groupName))
    }
    var editDescriptionState by remember(isEditMode) {
        mutableStateOf(TextFieldValue(groupDescription ?: ""))
    }

    val scrollState = rememberScrollState()

    DgenNavigationBackground(
        modifier = modifier,
        primaryColor = primaryColor,
        onBackClick = onBackClick,
        headerContent = {
            Text(
                text = "GROUP DETAILS",
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 24.sp,
                    letterSpacing = 0.sp,
                    textDecoration = TextDecoration.None
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(onClick = {
                if (isEditMode) {
                    onUpdateGroupName(editNameState.text.trim())
                    onUpdateGroupDescription(editDescriptionState.text.trim())
                }
                isEditMode = !isEditMode
            }) {
                Icon(
                    imageVector = if (isEditMode) Icons.Default.Check else Icons.Default.Edit,
                    contentDescription = if (isEditMode) "Save" else "Edit",
                    tint = if (isEditMode) primaryColor else primaryColor.copy(alpha = 0.6f)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(top = 24.dp, bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isEditMode) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "GROUP NAME",
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = primaryColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = label_fontSize
                            )
                        )
                        OutlinedTextField(
                            value = editNameState.text,
                            onValueChange = {
                                editNameState = TextFieldValue(it)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontFamily = PitagonsSans,
                                color = primaryColor,
                                fontSize = 16.sp
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = primaryColor.copy(alpha = 0.3f),
                                cursorColor = primaryColor
                            ),
                            singleLine = true
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "DESCRIPTION",
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = primaryColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = label_fontSize
                            )
                        )
                        OutlinedTextField(
                            value = editDescriptionState.text,
                            onValueChange = {
                                editDescriptionState = TextFieldValue(it)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            textStyle = TextStyle(
                                fontFamily = PitagonsSans,
                                color = primaryColor,
                                fontSize = 14.sp
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryColor,
                                unfocusedBorderColor = primaryColor.copy(alpha = 0.3f),
                                cursorColor = primaryColor
                            ),
                            maxLines = 4
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 12.dp)) {
                        Text(
                            text = "GROUP NAME",
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = primaryColor.copy(alpha = 0.6f),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = label_fontSize
                            )
                        )
                        Text(
                            text = groupName.ifBlank { "Unnamed Group" },
                            style = TextStyle(
                                fontFamily = PitagonsSans,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(modifier = Modifier.padding(vertical = 12.dp)) {
                        Text(
                            text = "DESCRIPTION",
                            style = TextStyle(
                                fontFamily = SpaceMono,
                                color = primaryColor.copy(alpha = 0.6f),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = label_fontSize
                            )
                        )
                        Text(
                            text = groupDescription?.takeIf { it.isNotBlank() }
                                ?: "No description",
                            style = TextStyle(
                                fontFamily = PitagonsSans,
                                color = if (groupDescription.isNullOrBlank())
                                    primaryColor.copy(alpha = 0.4f) else primaryColor,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "MEMBERS ${members.size}",
                        style = TextStyle(
                            fontFamily = SpaceMono,
                            color = primaryColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            letterSpacing = 0.sp,
                            textDecoration = TextDecoration.None
                        )
                    )

                    IconButton(onClick = onAddMembers) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = "Add members",
                            tint = primaryColor
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 300.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(members) { member ->
                            MemberItem(
                                header = member.displayName,
                                subheader = member.subtitle ?: "",
                                onDelete = { showRemoveMemberDialog = member },
                                primaryColor = primaryColor,
                                actionButton = if (isEditMode) {
                                    {
                                        IconButton(
                                            onClick = { showRemoveMemberDialog = member },
                                            modifier = Modifier.size(32.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PersonRemove,
                                                contentDescription = "Remove member",
                                                tint = dgenRed.copy(alpha = 0.7f),
                                                modifier = Modifier.size(18.dp)
                                            )
                                        }
                                    }
                                } else null
                            )
                        }
                    }

                    FadeEdge(
                        FadeDirection.Top,
                        Modifier.align(Alignment.TopCenter),
                        size = 8.dp
                    )
                    FadeEdge(
                        FadeDirection.Bottom,
                        Modifier.align(Alignment.BottomCenter),
                        size = 8.dp
                    )
                }
            }

            Button(
                onClick = { showLeaveConfirmDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = dgenRed.copy(alpha = 0.2f),
                    contentColor = dgenRed
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ExitToApp,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Leave Group",
                    style = TextStyle(
                        fontFamily = SpaceMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
            }
        }
    }

    // Leave Group Confirmation Dialog
    if (showLeaveConfirmDialog) {
        AlertDialog(
            onDismissRequest = { showLeaveConfirmDialog = false },
            containerColor = dgenBlack,
            title = {
                Text(
                    text = "Leave Group?",
                    style = TextStyle(
                        fontFamily = PitagonsSans,
                        color = primaryColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
            },
            text = {
                Text(
                    text = "You will no longer receive messages from this group.",
                    style = TextStyle(
                        fontFamily = SpaceMono,
                        color = primaryColor.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onLeaveGroup()
                        showLeaveConfirmDialog = false
                    }
                ) {
                    Text(
                        text = "Leave",
                        color = dgenRed,
                        fontFamily = SpaceMono,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showLeaveConfirmDialog = false }) {
                    Text(
                        text = "Cancel",
                        color = primaryColor,
                        fontFamily = SpaceMono
                    )
                }
            }
        )
    }

    // Remove Member Confirmation Dialog
    showRemoveMemberDialog?.let { member ->
        AlertDialog(
            onDismissRequest = { showRemoveMemberDialog = null },
            containerColor = dgenBlack,
            title = {
                Text(
                    text = "Remove Member?",
                    style = TextStyle(
                        fontFamily = PitagonsSans,
                        color = primaryColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                )
            },
            text = {
                Text(
                    text = "Remove ${member.displayName} from the group?",
                    style = TextStyle(
                        fontFamily = SpaceMono,
                        color = primaryColor.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRemoveMember(member.id)
                        showRemoveMemberDialog = null
                    }
                ) {
                    Text(
                        text = "Remove",
                        color = dgenRed,
                        fontFamily = SpaceMono,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showRemoveMemberDialog = null }) {
                    Text(
                        text = "Cancel",
                        color = primaryColor,
                        fontFamily = SpaceMono
                    )
                }
            }
        )
    }
}

@Composable
fun EditTextDialog(
    title: String,
    initialValue: String,
    primaryColor: Color,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    var text by remember { mutableStateOf(initialValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = dgenBlack,
        title = {
            Text(
                text = title,
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
            )
        },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor,
                    fontSize = 14.sp
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryColor,
                    unfocusedBorderColor = primaryColor.copy(alpha = 0.3f),
                    cursorColor = primaryColor
                ),
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm(text) },
                enabled = text.isNotBlank()
            ) {
                Text(
                    text = "Save",
                    color = if (text.isNotBlank()) primaryColor else primaryColor.copy(alpha = 0.3f),
                    fontFamily = SpaceMono,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = primaryColor.copy(alpha = 0.7f),
                    fontFamily = SpaceMono
                )
            }
        }
    )
}

@Preview
@Composable
fun PreviewGroupDetailsSheet() {
    GroupDetailsSheet(
        groupName = "XMTP Developers",
        groupDescription = "A group for XMTP developers to discuss the protocol.",
        members = listOf(
            GroupMember(id = "user1", displayName = "Alice", subtitle = "0x1234...5678"),
            GroupMember(id = "user2", displayName = "Bob", subtitle = "0xabcd...ef12"),
            GroupMember(id = "user3", displayName = "charlie.base.eth", subtitle = "0x9876...5432")
        ),
        primaryColor = dgenTurqoise,
        secondaryColor = dgenBlack,
        onBackClick = {},
        onUpdateGroupName = {},
        onUpdateGroupDescription = {},
        onAddMembers = {},
        onRemoveMember = {},
        onLeaveGroup = {}
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF050505)
@Composable
fun PreviewEditTextDialog() {
    EditTextDialog(
        title = "Edit Group Name",
        initialValue = "XMTP Developers",
        primaryColor = dgenTurqoise,
        onDismiss = {},
        onConfirm = {}
    )
}
