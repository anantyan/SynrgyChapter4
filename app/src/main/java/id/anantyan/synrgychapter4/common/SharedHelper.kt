package id.anantyan.synrgychapter4.common

interface SharedHelper {
    fun setLogin(value: Boolean)
    fun getLogin(): Boolean
    fun setUsrId(value: Long)
    fun getUsrId(): Long
    fun setInsertedCategories(value: Boolean)
    fun getInsertedCategories(): Boolean
}