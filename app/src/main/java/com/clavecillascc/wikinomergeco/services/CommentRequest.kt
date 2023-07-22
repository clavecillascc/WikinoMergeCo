package com.clavecillascc.wikinomergeco.services

import com.clavecillascc.wikinomergeco.common.MapForm
import com.clavecillascc.wikinomergeco.interfaces.DBResponseListener
import com.clavecillascc.wikinomergeco.models.CommentWord
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import java.lang.Exception

class CommentRequest {
    private val db = FirebaseDatabase.getInstance()
    private val commentWordPath = "comments"

    fun insertComment(commentWord: CommentWord, listener: DBResponseListener) {
        val key = db.getReference(commentWordPath).push().key!!
        db.getReference(commentWordPath)
            .child(key)
            .setValue(MapForm.convertObjectToMap(commentWord))
            .addOnSuccessListener {
                listener.success<String>(key)
            }
            .addOnFailureListener { error ->
                listener.error(error(error.localizedMessage))
            }
    }

    fun getComments(word: String, listener: DBResponseListener) {
        db.getReference(commentWordPath)
            .get()
            .addOnSuccessListener { dataSnapshot ->
                if (dataSnapshot.exists()) {
                    try {
                        val map = dataSnapshot.getValue<Map<String, Any>>()!!
                        val list = MapForm.convertMapToListOfComments(map, word)
                        listener.success<List<CommentWord>>(list)
                    } catch (e: Exception) {
                        listener.error(error(e.localizedMessage))
                    }
                }
            }
            .addOnFailureListener { error ->
                listener.error(error(error.localizedMessage))
            }
    }
}