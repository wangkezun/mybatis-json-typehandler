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
 * 针对object的typeHandler。
 *
 * @author wangkezun(wangkezun@gmail.com)
 * *
 * @since 1.0
 * @param clazz 需要进行转换的java类
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
class JsonArrayTypeHandler<Any>(clazz: Class<Any>) : BaseTypeHandler<Array<Any>>() {
    private val arrayReader: ObjectReader
    private val arrayWriter: ObjectWriter

    init {
        val objectMapper = MyBatisObjectMapper.objectMapper
        val type = objectMapper.typeFactory.constructArrayType(clazz)
        arrayReader = objectMapper.readerFor(type)
        arrayWriter = objectMapper.writerFor(type)
    }

    /**
     * json 转换成对象
     */
    private fun jsonToObject(json: String?): Array<Any>? {
        return if (!json.isNullOrBlank()) {
            arrayReader.readValue(json)
        } else {
            null
        }
    }

    @Throws(SQLException::class)
    override fun setNonNullParameter(ps: PreparedStatement,
                                     i: Int,
                                     parameter: Array<Any>?,
                                     jdbcType: JdbcType) {

        ps.setString(i, arrayWriter.writeValueAsString(parameter))
    }

    @Throws(SQLException::class)
    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): Array<Any>? {
        val json = cs.getString(columnIndex)

        return jsonToObject(json)
    }

    @Throws(SQLException::class)
    override fun getNullableResult(rs: ResultSet, columnIndex: Int): Array<Any>? {
        val json = rs.getString(columnIndex)

        return jsonToObject(json)
    }

    @Throws(SQLException::class)
    override fun getNullableResult(rs: ResultSet, columnName: String): Array<Any>? {
        val json = rs.getString(columnName)

        return jsonToObject(json)
    }

    override fun setParameter(ps: PreparedStatement?, i: Int, parameter: Array<Any>?, jdbcType: JdbcType?) {
        if (parameter == null) {
            ps?.setString(i, "[]")
        } else {
            super.setParameter(ps, i, parameter, jdbcType)
        }
    }
}
