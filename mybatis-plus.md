@TableName(value = "redpig_sys_user",autoResultMap = true)
autoResultMap是当表中有json字段类型的时候，我们需要在@TableName注解加上这个属性，并且在实体属性上加上注解 
@TableField(typeHandler = JacksonTypeHandler.class)。要不然查出来是null。
以上是前提。适用的MP版本是3.3.1。并且配置 map-underscore-to-camel-case: false
 
