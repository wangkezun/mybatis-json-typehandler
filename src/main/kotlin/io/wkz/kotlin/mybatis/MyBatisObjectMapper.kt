package io.wkz.kotlin.mybatis

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * This class generate a [ObjectMapper] to serialize or deserialize object or list.
 * TypeHandler don't use it directly,
 * but use it to create [com.fasterxml.jackson.databind.ObjectReader] and
 * [com.fasterxml.jackson.databind.ObjectWriter] to ensure thread-safe and optimize serialize or deserialize speed
 * @author wangkezun(wangkezun@gmail.com)
 * @since 1.0
 */
internal object MyBatisObjectMapper {

	val objectMapper = ObjectMapper().apply{
        // You could change some config in this block  like this
        //this.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        this.registerModule(KotlinModule())
    }


}
