package com.clavecillascc.wikinomergeco.services

import com.clavecillascc.wikinomergeco.common.MapForm
import com.clavecillascc.wikinomergeco.interfaces.DBResponseListener
import com.clavecillascc.wikinomergeco.models.DownVote
import com.google.firebase.database.FirebaseDatabase

class DownVoteRequest {
    private val db = FirebaseDatabase.getInstance()
    private val downVotePath = "downVotes"

    fun insertDownVote(downVote: DownVote, listener: DBResponseListener) {
        val key = db.getReference(downVotePath).push().key!!
        db.getReference(downVotePath)
            .child(key)
            .setValue(MapForm.convertObjectToMap(downVote))
            .addOnSuccessListener {
                listener.success<String>(key)
            }
            .addOnFailureListener { error ->
                listener.error(error(error.localizedMessage))
            }
    }


}