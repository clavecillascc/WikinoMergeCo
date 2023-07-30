package com.clavecillascc.wikinomergeco.components

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class AutocompleteViewModel : ViewModel() {
    private val firestoreCollection = FirebaseFirestore.getInstance().collection("words")

    private val _suggestions = MutableStateFlow<List<String>>(emptyList())
    val suggestions: StateFlow<List<String>> = _suggestions

    fun fetchAutocompleteSuggestions(query: String) {
        if (query.isEmpty()) {
            _suggestions.value = emptyList()
        } else {
            val endLetter = query + "\uf8ff" // Include the unicode character to define the end range

            firestoreCollection
                .whereGreaterThanOrEqualTo("word", query)
                .whereLessThan("word", endLetter)
                .get()
                .addOnSuccessListener { documents ->
                    val suggestionsList = documents.mapNotNull { it.getString("word") }
                    _suggestions.value = suggestionsList.filter { it.startsWith(query) }
                }
                .addOnFailureListener { e ->
                    // Handle the failure here
                    Log.e("AutocompleteViewModel", "Failed to fetch suggestions: $e")
                }
        }
    }
}