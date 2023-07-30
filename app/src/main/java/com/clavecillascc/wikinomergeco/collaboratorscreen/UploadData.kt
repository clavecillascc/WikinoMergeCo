package com.clavecillascc.wikinomergeco.collaboratorscreen

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import java.util.Locale

object UploadData {
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    val recentlyAddedContributions = mutableListOf<String>()

    fun uploadToFirebase(
        term: String,
        language: String,
        translationterm: String,
        terminsentence: String,
        translationsentence: String
    ) {

        val capitalizedTerm = term.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

        val fileName = "$capitalizedTerm.txt"
        val folderPath = "$language/"

        val formattedContent = buildString {
            appendLine("")
            appendLine("     $term")
            appendLine("     $language")
            if (translationterm.isNotBlank()) {
                appendLine("     • $translationterm")
                if (terminsentence.isNotBlank()) {
                    appendLine("       $terminsentence")
                }
                if (translationsentence.isNotBlank()) {
                    appendLine("         $translationsentence")
                }
            }
        }
        recentlyAddedContributions.add(formattedContent)

        val data = formattedContent.toByteArray(Charsets.UTF_8)

        val metadata = StorageMetadata.Builder()
            .setContentType("text/plain")
            .build()

        val fileReference = storageReference.child(folderPath + fileName)

        fileReference.putBytes(data, metadata)
            .addOnSuccessListener {
                // File successfully uploaded
            }
            .addOnFailureListener {
                // Failure case
            }
    }
}