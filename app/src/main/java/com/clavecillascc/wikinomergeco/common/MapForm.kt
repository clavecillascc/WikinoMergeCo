package com.clavecillascc.wikinomergeco.common

import com.clavecillascc.wikinomergeco.models.CommentWord
import com.fasterxml.jackson.databind.ObjectMapper

class MapForm {
    companion object {
        fun convertObjectToMap(obj: Any): Map<String, Any> {
            val objectMap = ObjectMapper()
            val params: Map<String, Any> =
                objectMap.convertValue(obj, Map::class.java) as Map<String, Any>
            return params
        }

        fun convertMapToListOfComments(obj: Map<String, Any>, word: String): List<CommentWord> {
            var list = mutableListOf<CommentWord>()
            var hashData = obj.values
            for (eachVal in hashData) {
                var rowData = eachVal as Map<String, Any>
                if (rowData["word"] == word) {
                    val commentWord = CommentWord.CommentWordBuilder()
                        .setDocumentID(rowData["documentID"].toString())
                        .setWord(rowData["word"].toString())
                        .setComments(rowData["comments"].toString())
                        .build()
                    list.add(commentWord)
                }

            }

            return list
        }
    }
}