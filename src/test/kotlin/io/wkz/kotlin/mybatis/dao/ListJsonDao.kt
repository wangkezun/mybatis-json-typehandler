package io.wkz.kotlin.mybatis.dao

import io.wkz.kotlin.mybatis.entity.ListJson

/**
 *
 * @author 王可尊
 * @since 1.0
 */
interface ListJsonDao {
	fun add(listJson:ListJson):Int
	fun get(id:Int):ListJson
}
