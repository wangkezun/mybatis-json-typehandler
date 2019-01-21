package io.wkz.kotlin.mybatis

import io.wkz.kotlin.mybatis.dao.ListJsonDao
import io.wkz.kotlin.mybatis.entity.ListJson
import io.wkz.kotlin.mybatis.entity.SubObject
import org.apache.ibatis.io.Resources
import org.apache.ibatis.jdbc.ScriptRunner
import org.apache.ibatis.session.SqlSessionFactory
import org.junit.BeforeClass
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
@DisplayName("Test JsonListTypeHandler")
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [DataSourceAutoConfiguration::class, MybatisAutoConfiguration::class, MybatisScanConfiguration::class])
class JsonListTypeHandlerTest {

	@Autowired
	private lateinit var listJsonDao: ListJsonDao

	@Test
	fun testInsertJsonListAsVarchar() {
		val emptyObject = ListJson()
		listJsonDao.add(emptyObject)
		assertNotEquals(0, emptyObject.id)
		val notEmptyObject = ListJson(listJson = arrayOf(SubObject("新增", 14), SubObject("新增2", 15)))
		listJsonDao.add(notEmptyObject)
		assertNotEquals(0, emptyObject.id)
	}

	@Test
	fun testGetJsonListFromVarchar() {
		val emptyListResult = listJsonDao.get(1)
		assertNotNull(emptyListResult)
		assertEquals(1, emptyListResult.id)
		println(emptyListResult)
		assertNotNull(emptyListResult.listJson)
		assertEquals(0, emptyListResult.listJson!!.size)

		val notEmptyListResult = listJsonDao.get(2)
		assertNotNull(notEmptyListResult)
		val (id, listJson) = notEmptyListResult
		assertEquals(2,id)
		assertNotNull(listJson)
		assertEquals(2,listJson!!.size)
		assertEquals(SubObject("测试",13),listJson[0])
		assertEquals(SubObject("测试2",14),listJson[1])
	}
}
