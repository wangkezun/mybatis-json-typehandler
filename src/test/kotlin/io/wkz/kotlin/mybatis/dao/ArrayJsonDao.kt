package io.wkz.kotlin.mybatis.dao

import io.wkz.kotlin.mybatis.entity.ArrayJson

/**
 *
 * @author 王可尊
 * @since 1.0
 */
interface ArrayJsonDao {
	fun add(listJson: ArrayJson):Int
	fun get(id:Int):ArrayJson
}
