package io.wkz.kotlin.mybatis

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mybatis.spring.annotation.MapperScan

/**
 * @author wangkezun(wangkezun@gmail.com)
 * @since 1.0
 */
@DisplayName("MyBatisObjectMapper Test Cases")
@MapperScan("io.wkz.kotlin.mybatis.dao")
internal class MyBatisObjectMapperTest {

    @Test
    @DisplayName("Test get ObjectMapper success")
    fun getObjectMapper() {
		assertNotNull(MyBatisObjectMapper.objectMapper)
    }
}
