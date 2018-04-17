package io.wkz.kotlin.mybatis

import com.fasterxml.jackson.databind.ObjectReader
import com.fasterxml.jackson.databind.ObjectWriter
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedJdbcTypes
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

/**
 *
 * @author wangkezun(wangkezun@gmail.com)
 * @since 1.0
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
class JsonListTypeHandler<Any>(clazz: Class<Any>) : BaseTypeHandler<List<Any>>() {
	private val listReader: ObjectReader
	private val listWriter: ObjectWriter


	init {
		val objectMapper = MyBatisObjectMapper.objectMapper
		val type = objectMapper.typeFactory.constructCollectionType(List::class.java, clazz)
        listReader = objectMapper.readerFor(type)
        listWriter = objectMapper.writerFor(type)
	}

	/**
	 * json 转换成对象
	 */
	private fun jsonToList(json: String?): List<Any>? {
		return if (!json.isNullOrBlank()) {
			listReader.readValue(json)
		} else{
			emptyList()
		}

	}

	@Throws(SQLException::class)
	override fun setNonNullParameter(ps: PreparedStatement,
									 i: Int,
									 parameter: List<Any>?,
									 jdbcType: JdbcType) {

		ps.setString(i, listWriter.writeValueAsString(parameter))
	}

	@Throws(SQLException::class)
	override fun getNullableResult(cs: CallableStatement, columnIndex: Int): List<Any>? {
		val json = cs.getString(columnIndex)

		return jsonToList(json)
	}

	@Throws(SQLException::class)
	override fun getNullableResult(rs: ResultSet, columnIndex: Int): List<Any>? {
		val json = rs.getString(columnIndex)

		return jsonToList(json)
	}

	@Throws(SQLException::class)
	override fun getNullableResult(rs: ResultSet, columnName: String): List<Any>? {
		val json = rs.getString(columnName)

		return jsonToList(json)
	}

	override fun setParameter(ps: PreparedStatement?, i: Int, parameter: List<Any>?, jdbcType: JdbcType?) {
		if (parameter == null) {
			ps?.setString(i, "[]")
		} else {
			super.setParameter(ps, i, parameter, jdbcType)
		}
	}
}
