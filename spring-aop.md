
![img_1.png](img_1.png)

https://blog.csdn.net/weixin_39977988/article/details/128219618

AopInfrastructureBean：免被AOP代理的标记接口
AopInfrastructureBean是一个标记接口。若Bean实现了此接口，表明它是一个Spring AOP的基础类，那么这个类是不会被AOP给代理的，即使它能被切面切进去~~~
