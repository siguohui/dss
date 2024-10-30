package com.xiaosi.springmvc.methodArgumentResolver;

import com.xiaosi.springmvc.annotation.Token;
import com.xiaosi.springmvc.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class TokenMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Token.class)?true:false;
    }


   /* public boolean supportsParameter(MethodParameter parameter) {
        CurrUser ann = parameter.getParameterAnnotation(CurrUser.class);
        Class<?> parameterType = parameter.getParameterType();
        return (ann != null &&
                (CurrUserVo.class.isAssignableFrom(parameterType)
                        || Map.class.isAssignableFrom(parameterType)
                        || Object.class.isAssignableFrom(parameterType)));
    }
*/
    /*@Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        // 从请求头中拿到token
        String token = request.getHeader("Authorization");
        if (StringUtils.isEmpty(token)) {
            return null; // 此处不建议做异常处理，因为校验token的事不应该属于它来做，别好管闲事
        }

        // 此处作为测试：new一个处理（写死的）
        CurrUserVo userVo = new CurrUserVo();
        userVo.setId(1L);
        userVo.setName("fsx");

        // 判断参数类型进行返回
        Class<?> parameterType = parameter.getParameterType();
        if (Map.class.isAssignableFrom(parameterType)) {
            Map<String, Object> map = new HashMap<>();
            BeanUtils.copyProperties(userVo, map);
            return map;
        } else {
            return userVo;
        }

    }*/





    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

        String parameter1 = request.getParameter(parameter.getParameterName());

        System.out.println(parameter1);

        if(parameter.getParameterAnnotation(Token.class) instanceof Token){
            String token = webRequest.getHeader("token");
            //解析token,获取useId,从数据库获缓存中获取用户User对象,这里模拟，这里简单写一下
            Long userId= 1L;
            User user = User.builder()
                    .userId(userId)
                    .username("admin")
                    .passworld("123456")
                    .token(token)
                    .build();
            return user;
        }
        return null;
    }
}
