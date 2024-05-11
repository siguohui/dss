@TableName(value = "redpig_sys_user",autoResultMap = true)
autoResultMap是当表中有json字段类型的时候，我们需要在@TableName注解加上这个属性，并且在实体属性上加上注解 
@TableField(typeHandler = JacksonTypeHandler.class)。要不然查出来是null。
以上是前提。适用的MP版本是3.3.1。并且配置 map-underscore-to-camel-case: false


MyBatis Plus 提供了一个 InterceptorIgnoreProperties 注解，可以用来忽略拦截器。你可以在你的实体类上使用这个注解，
然后在对应的拦截器中使用 InterceptorIgnoreHelper 类来判断是否需要忽略该实体类。
例如，你可以在你的实体类上使用 @InterceptorIgnoreProperties({"createTime", "updateTime"}) 注解，
然后在你的拦截器中使用下面的代码来忽略这两个字段的更新：
if (InterceptorIgnoreHelper.isIgnore(entity, "createTime") || InterceptorIgnoreHelper.isIgnore(entity, "updateTime")) {
return;
}
 
