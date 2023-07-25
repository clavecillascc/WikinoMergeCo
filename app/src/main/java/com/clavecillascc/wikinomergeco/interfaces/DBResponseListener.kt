package com.clavecillascc.wikinomergeco.interfaces

import java.lang.Error

interface DBResponseListener {
    fun <T> success(any: Any)
    fun error(error: Error)
}