package io.wkz.kotlin.mybatis

import io.wkz.kotlin.mybatis.dao.ArrayJsonDao
import io.wkz.kotlin.mybatis.entity.ArrayJson
import io.wkz.kotlin.mybatis.entity.SubObject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

/**
 *
 * @author 王可尊
 * @since 1.0
 */
@DisplayName("Test JsonArrayTypeHandler")
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [DataSourceAutoConfiguration::class, MybatisAutoConfiguration::class, MybatisScanConfiguration::class])
class JsonArrayTypeHandlerTest {

    @Autowired
    private lateinit var arrayJsonDao: ArrayJsonDao

    @Test
    fun testInsertJsonListAsVarchar() {
        val emptyObject = ArrayJson()
        arrayJsonDao.add(emptyObject)
        assertNotEquals(0, emptyObject.id)
        val notEmptyObject = ArrayJson(arrayJson = arrayOf(SubObject("新增", 14), SubObject("新增2", 15)))
        arrayJsonDao.add(notEmptyObject)
        assertNotEquals(0, emptyObject.id)
    }

    @Test
    fun testGetJsonListFromVarchar() {
        val emptyListResult = arrayJsonDao.get(1)
        assertNotNull(emptyListResult)
        assertEquals(1, emptyListResult.id)
        println(emptyListResult)
        assertNull(emptyListResult.arrayJson)
        assertEquals(0, emptyListResult.arrayJson!!.size)

        val notEmptyListResult = arrayJsonDao.get(2)
        assertNotNull(notEmptyListResult)
        val (id, listJson) = notEmptyListResult
        assertEquals(2, id)
        assertNotNull(listJson)
        assertEquals(2, listJson!!.size)
        assertEquals(SubObject("测试", 13), listJson[0])
        assertEquals(SubObject("测试2", 14), listJson[1])
    }
}
