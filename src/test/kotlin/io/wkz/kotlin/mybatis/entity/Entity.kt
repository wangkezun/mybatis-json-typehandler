package io.wkz.kotlin.mybatis.entity

/**
 *
 * @author 王可尊
 * @since 1.0
 */
data class ObjectJson(val id: Int = 0, val objectJson: SubObject? = null)

data class SubObject(val name: String, val age: Int)

data class ListJson(val id: Int = 0, val listJson: List<SubObject>? = null)

data class ArrayJson(val id: Int = 0, val arrayJson: Array<SubObject>? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArrayJson

        if (id != other.id) return false
        if (arrayJson != null) {
            if (other.arrayJson == null) return false
            if (!arrayJson.contentEquals(other.arrayJson)) return false
        } else if (other.arrayJson != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (arrayJson?.contentHashCode() ?: 0)
        return result
    }
}
