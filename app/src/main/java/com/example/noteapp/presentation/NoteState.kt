package com.example.noteapp.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.noteapp.data.Note

data class NoteState(
    val notes:List<Note> = emptyList(),
    val title: MutableState<String> = mutableStateOf(""),
    val note: MutableState<String> = mutableStateOf("")
)
