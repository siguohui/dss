package com.xiaosi.wx.encrypt;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xiaosi.wx.encrypt.annotation.EncryptedColumn;
import com.xiaosi.wx.encrypt.annotation.EncryptedTable;
import com.xiaosi.wx.utils.DBAesUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.springframework.core.annotation.AnnotationUtils;
import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

@Intercepts({
        @Signature(type = ResultSetHandler.class, method = "handleResultSets", args = {Statement.class})
})
public class DecryptInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object resultObject = invocation.proceed();
        if (Objects.isNull(resultObject)) {
            return null;
        }
        if(resultObject instanceof IPage){
            IPage page = (IPage) resultObject;
            if(page!=null&& CollUtil.isNotEmpty(page.getRecords())){
                if (needToDecrypt(page.getRecords().get(0))) {
                    for (Object result : page.getRecords()) {
                        //逐一解密
                        decrypt(result);
                    }
                }
            }
        }
        else if (resultObject instanceof ArrayList) {
            //基于selectList
            ArrayList resultList = (ArrayList) resultObject;
            if (CollUtil.isNotEmpty(resultList) && needToDecrypt(resultList.get(0))) {
                for (Object result : resultList) {
                    //逐一解密
                    decrypt(result);
                }
            }
        } else if (needToDecrypt(resultObject)) {
            //基于selectOne
            decrypt(resultObject);
        }
        return resultObject;
    }

    /**
     * 校验该实例的类是否被@EncryptedTable所注解
     */
    private boolean needToDecrypt(Object object) {
        Class<?> objectClass = object.getClass();
        EncryptedTable sensitiveData = AnnotationUtils.findAnnotation(objectClass, EncryptedTable.class);
        return Objects.nonNull(sensitiveData);
    }

    @Override
    public Object plugin(Object o) {
        if(o instanceof ResultSetHandler) {
            return   Plugin.wrap(o, this);
        }else{
            return o;
        }
    }

    private <T> T decrypt(T result) throws Exception {
        //取出resultType的类
        Class<?> resultClass = result.getClass();
        Field[] declaredFields = ReflectUtil.getFields(resultClass);
        for (Field field : declaredFields) {
            //取出所有被EncryptedColumn注解的字段
            EncryptedColumn sensitiveField = field.getAnnotation(EncryptedColumn.class);
            if (!Objects.isNull(sensitiveField)) {
                field.setAccessible(true);
                Object object = field.get(result);
                //只支持String的解密
                if (object instanceof String) {
                    String value = (String) object;
                    //对注解的字段进行逐一解密
                    String results =  DBAesUtils.Decrypt(value);
                    if(StrUtil.isNotEmpty(results)){
                        field.set(result, results);
                    }else{
                        field.set(result, value);
                    }

                }
            }
        }
        return result;
    }
}
