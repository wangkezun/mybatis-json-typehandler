package io.wkz.kotlin.mybatis.dao

import io.wkz.kotlin.mybatis.entity.ObjectJson

/**
 *
 * @author 王可尊
 * @since 1.0
 */
interface ObjectJsonDao {
	fun add(objectJson: ObjectJson):Int
	fun get(id:Int):ObjectJson
}
