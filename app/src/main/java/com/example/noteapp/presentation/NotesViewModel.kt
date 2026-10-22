package com.example.noteapp.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.Note
import com.example.noteapp.data.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotesViewModel(
    private val dao: NoteDao
): ViewModel() {

    private val isSortedByDateCreated= MutableStateFlow(true)

    private val notes=
        isSortedByDateCreated.flatMapLatest { sort->
            if(sort){
                dao.getNoteOrderedByDateCreated()
            }else{
                dao.getNoteOrderedByTitle()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(),emptyList())

    val _state= MutableStateFlow(NoteState())
    val state=
        combine(_state,isSortedByDateCreated,notes){state,isSortedByDateCreated,notes ->
             state.copy(
                notes=notes
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())


    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.DeleteNote->{
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }

            is NotesEvent.SaveNote->{
                val single_note= Note(
                    title=state.value.title.value,
                    note=state.value.note.value,
                    dateCreated = System.currentTimeMillis()
                )

                viewModelScope.launch {
                    dao.upsertNote(single_note)
                }

                _state.update {
                    it.copy(
                        title= mutableStateOf(""),
                        note=mutableStateOf("")
                    )
                }
            }

            NotesEvent.SortNote->{
                isSortedByDateCreated.value=!isSortedByDateCreated.value
            }
        }
    }
}