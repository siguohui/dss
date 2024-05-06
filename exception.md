https://blog.csdn.net/JokerLJG/article/details/123297513


BasicExceptionController

注意：如果既在具体Controller使用了@ExceptionHandler，也定义了全局异常处理器类（@ControllerAdvice+@ExceptionHandler），
优先使用Controller定义的@ExceptionHandler处理。如果处理不了，才会使用全局异常处理器处理。


@ControllerAdvice+@ExceptionHandler
一般说它只能处理控制器中抛出的异常，这种说法并不准确，其实它能处理DispatcherServlet
.doDispatch方法中DispatcherServlet.processDispatchResult方法之前捕捉到的所有异常，
包括：拦截器、参数绑定（参数解析、参数转换、参数校验）、控制器、返回值处理等模块抛出的异常。
此种方式是通过异常处理器实现的，使用HandlerExceptionResolverComposite异常处理器中的ExceptionHandlerExceptionResolver异常处理器处理的。

SimpleMappingExceptionResolver
使用简单映射异常处理器处理异常，通过配置SimpleMappingExceptionResolver类也是进行近似全局异常处理，但该种方式不能得到具体的异常信息，且返回的是视图，不推荐使用。

此种方式是通过异常处理器实现的，使用SimpleMappingExceptionResolver异常处理器处理的。


HandlerExceptionResolver
实现HandlerExceptionResolver接口来处理异常，该种方式是近似全局异常处理。
此种方式是通过异常处理器实现的，使用自定义的异常处理器（实现HandlerExceptionResolver接口）处理的。
public class MyExceptionResolver extends AbstractHandlerExceptionResolver {
    /**
     * 异常解析器的顺序， 数值越小，表示优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return -999999;
    }
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        try {
            response.getWriter().write(JSON.toJSONString(ApiUtil.fail(ex.getMessage())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


6. Filter
   基于过滤器的异常处理方式，比异常处理器处理的范围要大一些（能处理到Filter过滤器抛出的异常），更近似全局异常处理。使用自定义过滤器进行异常处理时，该过滤器应该放到过滤链的第一个位置，这样才能保证能处理到后续过滤器抛出的异常。

   @Bean
   ExceptionFilter exceptionFilter() {
   return new ExceptionFilter();
   }

   @Bean
   public FilterRegistrationBean exceptionFilterRegistration() {
   FilterRegistrationBean registration = new FilterRegistrationBean();
   registration.setFilter(exceptionFilter());
   registration.setName("exceptionFilter");
   //此处尽量小，要比其他Filter靠前
   registration.setOrder(-1);
   return registration;
   }
   
   14
   /**
* 自定义异常过滤器
* 用于处理Controller外抛出的异常（如Filter抛出的异常）
  */
  @Slf4j
  public class ExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException,IOException {
  try {
  filterChain.doFilter(httpServletRequest, httpServletResponse);
  } catch (IOException e) {
  httpServletResponse.getWriter().write(JSON.toJSONString(ApiUtil.fail(e.getMessage())));
  }
  }
  }
  上面的写法其实还是有一定问题的，如果进入了catch就会重复写入httpServletResponse，可能会导致产生一些列的问题。

举例一个问题来说明，通过上面的写法，这样的对响应的写入一般是累加的，可能会导致返回的数据格式有问题，比如：当异常处理器处理了Controller抛出的异常，
写入了响应，然后过滤器又抛出了异常，被ExceptionFilter给catch到，这就有一次处理了异常，写入了响应，最后的到的响应数据可能是这样的：
{
"code": 500,
"msg": "Controller error"
}{
"code": 505,
"msg": "Filter error"
}
这个时候我们一般会使用代理类来再次封装Response，filterChain.doFilter传递的是封装后的代理类。

Response代理类

/**
* Response代理类
  */
  public class ResponseWrapper extends HttpServletResponseWrapper {
  private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

  private PrintWriter printWriter = new PrintWriter(outputStream);

  public ResponseWrapper(HttpServletResponse response) {
  super(response);
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
  return new ServletOutputStream() {
  @Override
  public boolean isReady() {
  return false;
  }
           @Override
           public void setWriteListener(WriteListener writeListener) {
           }
           @Override
           public void write(int b) throws IOException {
               outputStream.write(b);
           }
           @Override
           public void write(byte[] b) throws IOException {
               outputStream.write(b);
           }
           @Override
           public void write(byte[] b, int off, int len) throws IOException {
               outputStream.write(b, off, len);
           }
           @Override
           public void flush() throws IOException {
               outputStream.flush();
           }
       };
  }

  @Override
  public PrintWriter getWriter() throws IOException {
  return printWriter;
  }

  public void flush(){
  try {
  printWriter.flush();
  printWriter.close();
  outputStream.flush();
  outputStream.close();
  } catch (IOException e) {
  e.printStackTrace();
  }
  }

  public byte[] getContent() {
  flush();
  return outputStream.toByteArray();
  }
  }
  自定义过滤器类修改为
@Slf4j
public class ExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException,IOException {
        try {
            // 封装Response，得到代理对象
            ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);
            // 使用代理对象
            filterChain.doFilter(httpServletRequest, responseWrapper);
            // 读取响应内容
            byte[] bytes = responseWrapper.getContent();
            // 这里可以对响应内容进行修改等操作
            // 模拟Filter抛出异常
            if (true) {
                throw new IOException("Filter error");
            }
            // 内容重新写入原响应对象中
            httpServletResponse.getOutputStream().write(bytes);
        } catch (Exception e) {
            httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
            httpServletResponse.getOutputStream().write(JSON.toJSONString(ApiUtil.fail(e.getMessage())).getBytes());
        }
    }
}


全局异常处理实现方案
要想实现正在的全局异常处理，显然只通过异常处理器的方式处理是不够的，这种方案处理不了过滤器等抛出的异常。

全局异常处理的几种实现方案：

基于请求转发；
基于异常处理器+请求转发补充；
基于过滤器；
基于异常处理器+过滤器补充。
1. 请求转发
   该方案貌似不好获取到特殊的异常描述信息（没仔细研究），如参数校验中的message属性信息：

@NotNull(message = "主键不能为空")
1
本方案通过自定义错误处理Controller继承BasicExceptionController来实现。

具体实现参考：常用异常处理实现方案1。

2. 异常处理器+请求转发补充
   （1）自定义异常处理Controller实现BasicExceptionController
   具体实现参考：常用异常处理实现方案1。

（2）异常处理器实现

方式1：@ControllerAdvice+@ExceptionHandler（推荐使用）
具体实现参考：常用异常处理实现方案3。
方式2：SimpleMappingExceptionResolver
具体实现参考：常用异常处理实现方案4。
方式3：HandlerExceptionResolver
具体实现参考：常用异常处理实现方案5。
3. 过滤器
   具体实现参考：常用异常处理实现方案6。

4. 异常处理器+过滤器补充
   创建自定义过滤器bean
   @Bean
   ExceptionFilter exceptionFilter() {
   return new ExceptionFilter();
   }

   @Bean
   public FilterRegistrationBean exceptionFilterRegistration() {
   FilterRegistrationBean registration = new FilterRegistrationBean();
   registration.setFilter(exceptionFilter());
   registration.setName("exceptionFilter");
   //此处尽量小，要比其他Filter靠前
   registration.setOrder(-1);
   return registration;
   }
   方式1：@ControllerAdvice+@ExceptionHandler+Filter（推荐使用）

@ControllerAdvice+@ExceptionHandler的实现参考：常用异常处理实现方案3。
Filter实现：
方式1：参考常用异常处理实现方案6。
方式2：借助异常处理器处理异常。
@Slf4j
public class ExceptionFilter extends OncePerRequestFilter {

    /**
     * 遇到的坑，ExceptionFilter对象的创建没有交给Spring容器（直接new的），导致@Autowired注入不会生效
     */
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException,IOException {
        try {
            // 封装Response，得到代理对象
            ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);
            // 使用代理对象
            filterChain.doFilter(httpServletRequest, responseWrapper);
            // 读取响应内容
            byte[] bytes = responseWrapper.getContent();
            // 这里可以对响应内容进行修改等操作

            // 模拟Filter抛出异常
            if (true) {
                throw new IOException("Filter error");
            }

            // 内容重新写入原响应对象中
            httpServletResponse.getOutputStream().write(bytes);

        } catch (Exception e) {
            handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null, e);
        }
    }
}

注入的HandlerExceptionResolver其实是HandlerExceptionResolverComposite异常处理器，最终是使用异常处理器中的ExceptionHandlerExceptionResolver异常处理器处理的。
方式2：HandlerExceptionResolver+Filter

HandlerExceptionResolver的实现参考：常用异常处理实现方案5。
Filter的实现：
方式1：参考常用异常处理实现方案6。
方式2：借助异常处理器处理异常。

@Slf4j
public class ExceptionFilter extends OncePerRequestFilter {

    @Autowired
    private MyExceptionResolver myExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException,IOException {
        try {
            // 封装Response，得到代理对象
            ResponseWrapper responseWrapper = new ResponseWrapper(httpServletResponse);
            // 使用代理对象
            filterChain.doFilter(httpServletRequest, responseWrapper);
            // 读取响应内容
            byte[] bytes = responseWrapper.getContent();
            // 这里可以对响应内容进行修改等操作

            // 模拟Filter抛出异常
            if (true) {
                throw new IOException("Filter error");
            }

            // 内容重新写入原响应对象中
            httpServletResponse.getOutputStream().write(bytes);

        } catch (Exception e) {
            myExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null, e);
        }
    }
}

注入的MyExceptionResolver是我们自定义的异常处理器。
注意事项
2、4两种通过组合的方式进行异常处理需要考虑到的问题：对于一个请求，如果两个地方都捕捉到了异常，要考虑两次异常处对response响应信息的重复写入问题。

比如：异常处理器处理了控制器抛出的异常，写入响应；过滤器处理了过滤器抛出的异常，写入响应。这就会出现响应被写入了两次的问题或者第二次写入响应直接报错。

一些处理思路：考虑使用Response代理类。第一次处理时，异常处理器写入的响应信息是写入到Response代理对象的，并可以从Response代理类中得到写入的响应信息；第二次处理，过滤器等写入的响应写入到Response原对象中的。

过程中发现一个问题：通过BasicExceptionController+异常处理器处理异常的方式时。Controller抛出了异常，被异常处理器处理，返回的过程中，Filter又抛出了一个异常，被BasicExceptionController处理，但BasicExceptionController的到的异常信息却是Controller产生的异常信息，而不是Filter产生的异常信息。但是调到BasicExceptionController去处理异常又却是是因为Filter抛出异常产生的。

个人猜想：异常处理器在处理异常时，不仅是把响应内容部部分写入了Response，还把异常信息写入了Response。当因为异常跳转到BasicExceptionController进行处理，BasicExceptionController在获取异常信息时，会先从Response获取异常信息，获取不到才会从异常中获取异常信息。

方案推荐
请求转发（推荐）。完全统一的全局异常处理，自定义异常处理Controller能达到自定义统一响应信息格式目的。

但是，现在一般项目需要的响应信息都是自定义统一格式的JSON（code、msg、data）。但对于自定义业务错误码code不好得到，对于错误信息msg有时得到的也不一定是你所想要的。

但感觉通过自定义的扩展是能得到业务状态码和特殊异常描述信息的（没详细研究）。

异常处理+请求转发补充（个人最推荐）。推荐使用@ControllerAdvice+@ExceptionHandler+BasicExceptionController的方式。

异常处理器能自定义处理大多异常（包括特殊的异常），剩余处理不到的异常交给异常处理控制器处理。

过滤器（不推荐）。异常处理全需要手写代码实现，自己的代码肯定不会太完美，可能有没考虑到的情况，容易出问题；且过滤器之前抛出的异常处理不到。

异常处理器+过滤器补充（不太推荐）。推荐使用@ControllerAdvice+@ExceptionHandler+Filter（借助异常处理器处理异常）的方式，但过滤器之前抛出的异常处理不到。



