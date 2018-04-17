package io.wkz.kotlin.mybatis.entity

/**
 *
 * @author 王可尊
 * @since 1.0
 */
data class ObjectJson(val id: Int = 0, val objectJson: SubObject? = null) {
//	constructor(id: Long,test:String):this(id=id.toInt(),objectJson = null)
}

data class SubObject(val name: String, val age: Int)

data class ListJson(val id: Int = 0, val listJson: List<SubObject>? = null)
