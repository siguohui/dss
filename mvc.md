HandlerMethodReturnValueHandler
HandlerMethodReturnValueHandler 的作用是对处理器的处理结果再进行一次二次加工，这个接口里边有两个方法：
返回值解析器
用于对controller的返回值进行二次处理

// 返回值解析器
public interface HandlerMethodReturnValueHandler {

	// 判断 HandlerMethodReturnValueHandler 是否支持 MethodParameter
	boolean supportsReturnType(MethodParameter returnType);
 
	// 处理给定的返回值
	void handleReturnValue(@Nullable Object returnValue, MethodParameter returnType,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception;
}



DispatcherServlet#doDispatch()
AbstractHandlerMethodAdapter#handle()
RequestMappingHandlerAdapter#handleInternal()
// 在这里会创建ServletInvocableHandlerMethod，此时已经将controller的函数（HandlerMethod）的参数注入到InvocableHandlerMethod（类变量）中
RequestMappingHandlerAdapter#invokeHandlerMethod()
// 在这里，invokeForRequest通过反射的方式调用controller中的方法并返回returnValue
ServletInvocableHandlerMethod#invokeAndHandle()
// 处理返回值方法
HandlerMethodReturnValueHandlerComposite#handleReturnValue
// 获取返回值处理器
HandlerMethodReturnValueHandlerComposite#selectHandler
// 处理返回值
HandlerMethodReturnValueHandler#handleReturnValue


RequestCondition
DispatchServlet
HanderMapping 

在 spring mvc 中，通过DispatchServlet接收客户端发起的一个请求之后，
会通过 HanderMapping 来获取对应的请求处理器；
而 HanderMapping 如何找到可以处理这个请求的处理器呢，这就需要 RequestCondition 来决定了。
https://www.jianshu.com/p/50531a355f97


AbstractHandlerMethodMapping


HandlerMapping

AbstractHandlerMethodMapping
