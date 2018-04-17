package io.wkz.kotlin.mybatis

import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

/**
 *
 * @author 王可尊
 * @since 1.0
 */
@Configuration
@MapperScan("io.wkz.kotlin.mybatis.dao")
open class MybatisScanConfiguration {
}
