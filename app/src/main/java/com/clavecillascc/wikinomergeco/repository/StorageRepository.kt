package com.clavecillascc.wikinomergeco.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StorageRepository {
    fun signOut() = Firebase.auth.signOut()
}