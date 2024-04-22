https://blog.csdn.net/weixin_45433031/article/details/124343197


@Cacheable
这个注解含义是方法结果会被放入缓存，并且一旦缓存后，下一次调用此方法，会通过key去查找缓存是否存在，如果存在就直接取缓存值，不再执行方法。

这个注解有几个参数值，定义如下

参数	解释
cacheNames	缓存名称
value	缓存名称的别名
condition	Spring SpEL 表达式，用来确定是否缓存
key	SpEL 表达式，用来动态计算key
keyGenerator	Bean 名字，用来自定义key生成算法，跟key不能同时用
unless	SpEL 表达式，用来否决缓存，作用跟condition相反
sync	多线程同时访问时候进行同步
在计算key、condition或者unless的值得时候，可以使用到以下的特有的SpEL表达式

表达式	解释
#result	表示方法的返回结果
#root.method	当前方法
#root.target	目标对象
#root.caches	被影响到的缓存列表
#root.methodName	方法名称简称
#root.targetClass	目标类
#root.args[x]	方法的第x个参数
@CachePut
该注解在执行完方法后会触发一次缓存put操作，参数跟@Cacheable一致

@CacheEvict
该注解在执行完方法后会触发一次缓存evict操作，参数除了@Cacheable里的外，还有个特殊的allEntries， 表示将清空缓存中所有的值。

@Cacheable注解主要是在方法执行前 先查看缓存中是否有数据。如果有数据，则直接返回缓存数据；若没有数据，调用方法并将方法返回值放到缓存中。

注解中的参数传递主要使用的是**SpEL（Spring Expression Language）**对数据进行获取传递，这有点类似于JSP中的EL表达式，常用方式如下几种：

“#p0”：获取参数列表中的第一个参数。其中的“#p”为固定写法，0为下标，代表第一个；
“#root.args[0]”：获取方法中的第一个参数。其中0为下标，代表第一个。
“#user.id”：获取参数 user 的 id 属性。注意的是这里的 user 必须要和参数列表中的参数名一致
“#result.id”：获取返回值中的 id 属性。
来自Spring Cache源码：Spring Expression Language (SpEL) expression used for making the method

在@Cacheable注解中有几种常用的属性可进行需求性设置：

value：缓存的名称，每个缓存名称下面可以有多个 key
key：缓存的key。
condition：条件判断，满足条件时对数据进行缓存，值得注意的是该参数在Redis中无效
unless：条件判断，满足条件时则不对数据进行缓存，Redis中可使用该参数替代condition




#毫秒为单位
spring.cache.type=redis
spring.cache.redis.time-to-live=3600000
#如果指定了前缀就用我们指定的前缀，如果没有就默认使用缓存的名字作为前缀
spring.cache.redis.key-prefix=CACHE_
spring.cache.redis.use-key-prefix=true
#是否缓存空值。防止缓存穿透
spring.cache.redis.cache-null-values=true
