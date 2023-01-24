package com.example.notas.presentation.screen.home

import com.example.notas.data.models.NoteEntity
import com.example.notas.util.Request

data class HomeStateFlow(
    /** Notes Screen **/
    val allNotes: Request<List<NoteEntity>> = Request.Static,
    val searchNotes: Request<List<NoteEntity>> = Request.Static,
    val sort: Request<Priority> = Request.Static,
    val selectNote: NoteEntity? = null
)