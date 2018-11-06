package io.wkz.kotlin.mybatis

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
class JsonObjectTypeHandler<Any>(clazz: Class<Any>) : BaseTypeHandler<Any>() {
    private val objectReader = MyBatisObjectMapper.objectMapper.readerFor(clazz)
    private val objectWriter = MyBatisObjectMapper.objectMapper.writerFor(clazz)

    /**
     * json 转换成对象
     */
    private fun jsonToObject(json: String?): Any? {
        return if (!json.isNullOrBlank()) {
            objectReader.readValue(json)
        } else {
            null
        }
    }

    @Throws(SQLException::class)
    override fun setNonNullParameter(ps: PreparedStatement,
                                     i: Int,
                                     parameter: Any?,
                                     jdbcType: JdbcType) {

        ps.setString(i, objectWriter.writeValueAsString(parameter))
    }

    @Throws(SQLException::class)
    override fun getNullableResult(cs: CallableStatement, columnIndex: Int): Any? {
        val json = cs.getString(columnIndex)

        return jsonToObject(json)
    }

    @Throws(SQLException::class)
    override fun getNullableResult(rs: ResultSet, columnIndex: Int): Any? {
        val json = rs.getString(columnIndex)

        return jsonToObject(json)
    }

    @Throws(SQLException::class)
    override fun getNullableResult(rs: ResultSet, columnName: String): Any? {
        val json = rs.getString(columnName)

        return jsonToObject(json)
    }

    override fun setParameter(ps: PreparedStatement?, i: Int, parameter: Any, jdbcType: JdbcType?) {
        if (parameter == null) {
            ps?.setString(i, "")
        } else {
            super.setParameter(ps, i, parameter, jdbcType)
        }
    }
}
