package com.clavecillascc.wikinomergeco.models

class Upvote private constructor(builder: UpvoteBuilder) {

    private val documentID: String?
    private val word: String?
    private val translation: String?
    private val count: Int?

    constructor() : this(UpvoteBuilder())

    init {
        documentID = builder.documentID
        word = builder.word
        count = builder.count
        translation = builder.translation
    }

    fun getDocumentID(): String? {
        return this.documentID
    }

    fun getWord(): String? {
        return this.word
    }

    fun getCount(): Int? {
        return this.count
    }

    fun getTranslation(): String? {
        return this.translation
    }

    class UpvoteBuilder {
        var documentID: String? = null
        var word: String? = null
        var count: Int? = null
        var translation: String? = null


        fun setDocumentID(id: String): UpvoteBuilder {
            this.documentID = id
            return this
        }

        fun setWord(word: String): UpvoteBuilder {
            this.word = word
            return this
        }

        fun setCount(count: Int): UpvoteBuilder {
            this.count = count
            return this
        }

        fun setTranslation(translation: String): UpvoteBuilder {
            this.translation = translation
            return this
        }

        fun build(): Upvote {
            return Upvote(this)
        }
    }

}