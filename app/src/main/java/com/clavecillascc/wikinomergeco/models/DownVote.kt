package com.clavecillascc.wikinomergeco.models

class DownVote private constructor(builder: DownVoteBuilder) {

    private val documentID: String?
    private val word: String?
    private val translation: String?
    private val count: Int?

    constructor() : this(DownVoteBuilder())

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


    class DownVoteBuilder {
        var documentID: String? = null
        var word: String? = null
        var translation: String? = null
        var count: Int? = null

        fun setDocumentID(id: String): DownVoteBuilder {
            this.documentID = id
            return this
        }

        fun setWord(word: String): DownVoteBuilder {
            this.word = word
            return this
        }

        fun setCount(count: Int): DownVoteBuilder {
            this.count = count
            return this
        }

        fun setTranslation(translation: String): DownVoteBuilder {
            this.translation = translation
            return this
        }

        fun build(): DownVote {
            return DownVote(this)
        }
    }
}