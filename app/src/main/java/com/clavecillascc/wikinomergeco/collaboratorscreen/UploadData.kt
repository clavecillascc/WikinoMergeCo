package com.clavecillascc.wikinomergeco.collaboratorscreen

import com.clavecillascc.wikinomergeco.interfaces.FirebaseStorageListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import java.util.Locale
import java.util.Objects

object UploadData {
    private val storage = FirebaseStorage.getInstance()
    private val storageReference = storage.reference

    fun uploadToFirebase(
        term: String,
        language: String,
        translationterm: String,
        terminsentence: String,
        translationsentence: String,
        listener: FirebaseStorageListener
    ) {

        val capitalizedTerm =
            term.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }

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

        val data = formattedContent.toByteArray(Charsets.UTF_8)

        val metadata = StorageMetadata.Builder()
            .setContentType("text/plain")
            .build()

        val fileReference = storageReference.child(folderPath + fileName)

        fileReference.putBytes(data, metadata)
            .addOnSuccessListener {
                listener.success<String>("")
            }
            .addOnFailureListener { error ->
                try {
                    listener.error(error(error.localizedMessage))
                } catch (e: Exception) {
                    listener.error(error("unknown error"))
                }
            }
    }
}