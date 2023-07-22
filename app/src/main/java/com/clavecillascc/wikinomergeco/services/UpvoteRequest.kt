package com.clavecillascc.wikinomergeco.services

import android.util.Log
import com.clavecillascc.wikinomergeco.common.MapForm
import com.clavecillascc.wikinomergeco.interfaces.DBResponseListener
import com.clavecillascc.wikinomergeco.models.Upvote
import com.google.firebase.database.FirebaseDatabase

class UpvoteRequest {
    private val db = FirebaseDatabase.getInstance()
    private val upvotePath = "upVotes"

    fun insertUpvote(upvote: Upvote, listener: DBResponseListener) {
        try {
            val key: String = db.getReference(upvotePath).push().key!!
            db.getReference(upvotePath)
                .child(key)
                .setValue(MapForm.convertObjectToMap(upvote))
                .addOnSuccessListener {
                    listener.success<String>(key)
                }
                .addOnFailureListener { err ->
                    listener.error(error(err.localizedMessage))
                }
        } catch (e: Exception) {
            if (e != null && e.localizedMessage != null) {
                Log.e("insertUpvote_err", e.localizedMessage)
            }
            listener.error(error(e.localizedMessage))
        }
    }
}