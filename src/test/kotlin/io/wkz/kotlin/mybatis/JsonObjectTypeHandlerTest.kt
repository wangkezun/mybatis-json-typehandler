package io.wkz.kotlin.mybatis

import io.wkz.kotlin.mybatis.dao.ObjectJsonDao
import io.wkz.kotlin.mybatis.entity.ObjectJson
import io.wkz.kotlin.mybatis.entity.SubObject
import org.apache.ibatis.io.Resources
import org.apache.ibatis.jdbc.ScriptRunner
import org.apache.ibatis.session.SqlSessionFactory
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.function.Executable
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
@DisplayName("Test JsonObjectTypeHandler")
@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [DataSourceAutoConfiguration::class, MybatisAutoConfiguration::class, MybatisScanConfiguration::class])
internal class JsonObjectTypeHandlerTest {
	@Autowired
	private lateinit var objectJsonDao: ObjectJsonDao

	@Test
	fun testInsertJsonObjectAsVarchar() {
		val notEmptyObjectJson = ObjectJson(objectJson = SubObject("测试", 13))
		objectJsonDao.add(notEmptyObjectJson)
		assertNotEquals(0, notEmptyObjectJson.id)
		val emptyObjectJson = ObjectJson()
		objectJsonDao.add(emptyObjectJson)
		assertNotEquals(0, emptyObjectJson.id)
	}

	@Test
	fun testGetJsonObjectFromVarchar() {
		val emptyObject = objectJsonDao.get(1)
		assertAll(
				Executable { assertNotNull(emptyObject) },
				Executable { assertEquals(1, emptyObject.id) },
				Executable { assertNull(emptyObject.objectJson) }
		)
		val notEmptyObject = objectJsonDao.get(2)
		assertAll(
				Executable { assertNotNull(notEmptyObject) },
				Executable { assertEquals(2, notEmptyObject.id) },
				Executable { assertNotNull(notEmptyObject.objectJson) },
				Executable { assertEquals("测试", notEmptyObject.objectJson!!.name) },
				Executable { assertEquals(13, notEmptyObject.objectJson!!.age) }
		)

	}
}
