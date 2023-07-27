package com.clavecillascc.wikinomergeco.collaboratorscreen

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata

object UploadData {
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    fun uploadToFirebase(term: String, language: String, translationterm: String, terminsentence: String, translationsentence: String) {
        val fileName = "$term.txt"
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