package com.clavecillascc.wikinomergeco.models

class CommentWord private constructor(builder: CommentWordBuilder) {


    private val documentID: String?
    private val word: String?
    private val comments: String?

    constructor() : this(CommentWordBuilder())

    init {
        documentID = builder.documentID
        word = builder.word
        comments = builder.comments
    }

    fun getDocumentID(): String? {
        return this.documentID
    }

    fun getWord(): String? {
        return this.word
    }

    fun getComments(): String? {
        return this.comments
    }

    class CommentWordBuilder {
        var documentID: String? = null
        var word: String? = null
        var comments: String? = null

        fun setDocumentID(id: String): CommentWordBuilder {
            this.documentID = id
            return this
        }

        fun setWord(word: String): CommentWordBuilder {
            this.word = word
            return this
        }


        fun setComments(comments: String): CommentWordBuilder {
            this.comments = comments
            return this
        }

        fun build(): CommentWord {
            return CommentWord(this)
        }
    }
}