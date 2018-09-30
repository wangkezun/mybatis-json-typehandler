# Mybatis-Json-TypeHandler [![Build Status](https://travis-ci.org/wangkezun/mybatis-json-typehandler.svg?branch=master)](https://travis-ci.org/wangkezun/mybatis-json-typehandler) [![Codecov](https://img.shields.io/codecov/c/github/wangkezun/mybatis-json-typehandler.svg)](https://github.com/wangkezun/mybatis-json-typehandler) [![GitHub license](https://img.shields.io/github/license/wangkezun/mybatis-json-typehandler.svg)](https://github.com/wangkezun/mybatis-json-typehandler/blob/master/LICENSE)[![DepShield Badge](https://depshield.sonatype.org/badges/wangkezun/mybatis-json-typehandler/depshield.svg)](https://depshield.github.io)

---

## 目的
开发过程中经常会遇到一对多的关系，大部分情况下这种关系都存在一张表中，通过id等关系进行关联。
但是在某些特殊情况下，单独为这种情况生成一个表并不是非常必须，而且也不会对这种关系进行查询。
此时可以考虑将数据存在一对多的一这个表中，把多的数据存成JSON结构。
但是MyBatis并没有对这种情况提供原生支持。此项目提供了一种自动序列化与反序列化的功能。
调用时仅需要在mapper中配置好即可使用。

## 依赖
项目依赖于MyBatis、Jackson。基于Kotlin语言。

## 用法
1. 实体类

   与正常的实体类没有任何区别

    ```java
        public class Data {
            private int id;
            //数据库中序列化为JSON Object
            private JsonObject targetObject;
            // 数据库中序列化为JSON Array
            private List<JsonObject> targetList;
        }
        public class JsonObject {
            private String xxx;
            private int yyy;
        }
    ```
    ```kotlin
        data class Data(val id: Int = 0, val targetObject: JsonObject? = null, val targetList:List<JsonObject>? =null)
        data class JsonObject(val xxx:String, val yyy:Int)
    ```
2. mapper

   重点修改在#{}以及resultMap的result中。
   明确定义javaType以及typeHandler即可，如下所示
    ```xml
     <?xml version="1.0" encoding="UTF-8" ?> <!DOCTYPE
       mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
       "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
        <mapper namespace="io.wkz.kotlin.mybatis.dao.ListJsonDao">
           <resultMap id="Data" type="Data">
               <result column="targetObject" property="targetObject" javaType="JsonObject"
                       jdbcType="VARCHAR" typeHandler="io.wkz.kotlin.mybatis.JsonObjectTypeHandler"/>
               <result column="targetList" property="targetObject" javaType="JsonObject"
                       jdbcType="VARCHAR" typeHandler="io.wkz.kotlin.mybatis.JsonListTypeHandler"/>
           </resultMap>
           <insert id="add" useGeneratedKeys="true" keyProperty="id" keyColumn="id">
               insert into tbl_name (targetObject,targetList) value
                   (#{targetObject,javaType=JsonObject,jdbcType=VARCHAR,typeHandler=io.wkz.kotlin.mybatis.JsonObjectTypeHandler},
                   #{targetList,javaType=JsonObject,jdbcType=VARCHAR,typeHandler=io.wkz.kotlin.mybatis.JsonListTypeHandler})
           </insert>
           <select id="get" resultMap="Data">
               select *
               from tbl_name
               where id = #{id}
           </select>
        </mapper>
    ```
