package com.example.noteapp.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AddNoteScreen(
    state: NoteState,
    navController: NavController,
    onEvent:(NotesEvent)-> Unit
){
    Scaffold(
        floatingActionButton = {
            FloatingActionButton( onClick = {
                onEvent(NotesEvent.SaveNote(
                    title=state.title.value,
                    note=state.note.value
                ))
                navController.popBackStack()
            }) {
                Icon(imageVector= Icons.Rounded.Check, contentDescription="Save Note")
            }
        }
    ) { paddingValues->
            Column(
                modifier= Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ){
                TextField(
                    value = state.title.value,
                    onValueChange = {
                        state.title.value=it
                    },
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder={
                        Text(text = "Title")
                    },
                    textStyle = TextStyle(
                        fontWeight= FontWeight.SemiBold,
                        fontSize = 17.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = state.note.value,
                    onValueChange = {
                        state.note.value=it
                    },
                    modifier=Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder={
                        Text(text = "Notes")
                    }
                )
            }
    }
}