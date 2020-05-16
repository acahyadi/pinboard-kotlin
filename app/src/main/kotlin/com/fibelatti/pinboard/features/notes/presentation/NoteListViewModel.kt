package com.fibelatti.pinboard.features.notes.presentation

import com.fibelatti.core.archcomponents.BaseViewModel
import com.fibelatti.core.functional.mapCatching
import com.fibelatti.core.functional.onFailure
import com.fibelatti.pinboard.core.util.DateFormatter
import com.fibelatti.pinboard.features.appstate.AppStateRepository
import com.fibelatti.pinboard.features.appstate.SetNotes
import com.fibelatti.pinboard.features.notes.domain.NotesRepository
import com.fibelatti.pinboard.features.notes.domain.model.Note
import com.fibelatti.pinboard.features.notes.domain.model.NoteSorting
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    private val appStateRepository: AppStateRepository,
    private val dateFormatter: DateFormatter
) : BaseViewModel() {

    fun getAllNotes() {
        launch {
            notesRepository.getAllNotes()
                .mapCatching { appStateRepository.runAction(SetNotes(it)) }
                .onFailure(::handleError)
        }
    }

    fun sort(notes: List<Note>, sorting: NoteSorting): List<Note> {
        return when (sorting) {
            NoteSorting.ByDateUpdatedDesc -> {
                notes.sortedByDescending { dateFormatter.displayFormatToMillis(it.updatedAt) }
            }
            NoteSorting.ByDateUpdatedAsc -> {
                notes.sortedBy { dateFormatter.displayFormatToMillis(it.updatedAt) }
            }
            NoteSorting.AtoZ -> notes.sortedBy(Note::title)
        }
    }
}
