package com.example.dgenlibrary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.dgenlibrary.model.GroupMember
import com.example.dgenlibrary.ui.theme.PitagonsSans
import com.example.dgenlibrary.ui.theme.SpaceMono
import com.example.dgenlibrary.ui.theme.dgenBlack
import com.example.dgenlibrary.ui.theme.dgenRed
import com.example.dgenlibrary.ui.theme.dgenTurqoise

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
    var showEditNameDialog by remember { mutableStateOf(false) }
    var showEditDescriptionDialog by remember { mutableStateOf(false) }
    var showLeaveConfirmDialog by remember { mutableStateOf(false) }
    var showRemoveMemberDialog by remember { mutableStateOf<GroupMember?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(dgenBlack)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = primaryColor
                )
            }

            Text(
                text = "Group Details",
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Group Info Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            // Group Name
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showEditNameDialog = true }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Group Name",
                        style = TextStyle(
                            fontFamily = SpaceMono,
                            color = primaryColor.copy(alpha = 0.6f),
                            fontSize = 12.sp
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
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit name",
                    tint = primaryColor.copy(alpha = 0.6f),
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Group Description
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showEditDescriptionDialog = true }
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Description",
                        style = TextStyle(
                            fontFamily = SpaceMono,
                            color = primaryColor.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                    )
                    Text(
                        text = groupDescription?.takeIf { it.isNotBlank() } ?: "No description",
                        style = TextStyle(
                            fontFamily = PitagonsSans,
                            color = if (groupDescription.isNullOrBlank()) primaryColor.copy(alpha = 0.4f) else primaryColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit description",
                    tint = primaryColor.copy(alpha = 0.6f),
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Members Section Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "MEMBERS (${members.size})",
                style = TextStyle(
                    fontFamily = SpaceMono,
                    color = primaryColor.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    letterSpacing = 1.sp
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

        // Members List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            items(members) { member ->
                GroupMemberItem(
                    member = member,
                    primaryColor = primaryColor,
                    onRemoveClick = { showRemoveMemberDialog = member }
                )
            }
        }

        // Leave Group Button
        Button(
            onClick = { showLeaveConfirmDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = dgenRed.copy(alpha = 0.2f),
                contentColor = dgenRed
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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

    // Edit Name Dialog
    if (showEditNameDialog) {
        EditTextDialog(
            title = "Edit Group Name",
            initialValue = groupName,
            primaryColor = primaryColor,
            onDismiss = { showEditNameDialog = false },
            onConfirm = { newName ->
                onUpdateGroupName(newName)
                showEditNameDialog = false
            }
        )
    }

    // Edit Description Dialog
    if (showEditDescriptionDialog) {
        EditTextDialog(
            title = "Edit Description",
            initialValue = groupDescription ?: "",
            primaryColor = primaryColor,
            onDismiss = { showEditDescriptionDialog = false },
            onConfirm = { newDescription ->
                onUpdateGroupDescription(newDescription)
                showEditDescriptionDialog = false
            }
        )
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
fun GroupMemberItem(
    member: GroupMember,
    primaryColor: Color,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(primaryColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = (member.displayName.firstOrNull() ?: '?').uppercase(),
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = member.displayName,
                style = TextStyle(
                    fontFamily = PitagonsSans,
                    color = primaryColor,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (member.subtitle != null) {
                Text(
                    text = member.subtitle,
                    style = TextStyle(
                        fontFamily = SpaceMono,
                        color = primaryColor.copy(alpha = 0.5f),
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        IconButton(
            onClick = onRemoveClick,
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
fun PreviewGroupMemberItem() {
    GroupMemberItem(
        member = GroupMember(id = "user1", displayName = "Alice", subtitle = "0x1234...5678"),
        primaryColor = dgenTurqoise,
        onRemoveClick = {},
        modifier = Modifier.padding(horizontal = 16.dp)
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
