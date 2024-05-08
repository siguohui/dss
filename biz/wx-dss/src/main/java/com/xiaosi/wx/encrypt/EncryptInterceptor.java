package com.xiaosi.wx.encrypt;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.xiaosi.wx.encrypt.annotation.EncryptedColumn;
import com.xiaosi.wx.encrypt.annotation.EncryptedTable;
import com.xiaosi.wx.utils.DBAesUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings({"rawtypes"})
public class EncryptInterceptor extends JsqlParserSupport implements InnerInterceptor {
    /**
     * 变量占位符正则
     */
    private static final Pattern PARAM_PAIRS_RE = Pattern.compile("#\\{ew\\.paramNameValuePairs\\.(" + Constants.WRAPPER_PARAM + "\\d+)\\}");

    @Override
    public void beforeUpdate(Executor executor, MappedStatement mappedStatement, Object parameterObject) throws SQLException {
        if (Objects.isNull(parameterObject)) {
            return;
        }
        // 通过MybatisPlus自带API（save、insert等）新增数据库时
        if (!(parameterObject instanceof Map)) {
            if (needToDecrypt(parameterObject.getClass())) {
                encryptEntity(parameterObject);
            }
            return;
        }
        Map paramMap = (Map) parameterObject;
        Object param;
        // 通过MybatisPlus自带API（update、updateById等）修改数据库时
        if (paramMap.containsKey(Constants.ENTITY) && null != (param = paramMap.get(Constants.ENTITY))) {
            if (needToDecrypt(param.getClass())) {
                encryptEntity(param);
            }
            return;
        }
        // 通过在mapper.xml中自定义API修改数据库时
        if (paramMap.containsKey("entity") && null != (param = paramMap.get("entity"))) {
            if (needToDecrypt(param.getClass())) {
                encryptEntity(param);
            }
            return;
        }
        // 通过UpdateWrapper、LambdaUpdateWrapper修改数据库时
        if (paramMap.containsKey(Constants.WRAPPER) && null != (param = paramMap.get(Constants.WRAPPER))) {
            if (param instanceof Update && param instanceof AbstractWrapper) {
                Class<?> entityClass = mappedStatement.getParameterMap().getType();
                if (needToDecrypt(entityClass)) {
                    encryptWrapper(entityClass, param);
                }
            }
            return;
        }
    }

    /**
     * 校验该实例的类是否被@EncryptedTable所注解
     */
    private boolean needToDecrypt(Class<?> objectClass) {
        try {
            EncryptedTable sensitiveData = AnnotationUtils.findAnnotation(objectClass, EncryptedTable.class);
            return Objects.nonNull(sensitiveData);
        }catch (Exception ex){
            return  false;
        }

    }

    /**
     * 通过API（save、updateById等）修改数据库时
     *
     * @param parameter
     */
    private void encryptEntity(Object parameter) {
        //取出parameterType的类
        Class<?> resultClass = parameter.getClass();

        Field[] declaredFields =  ReflectUtil.getFields(resultClass);
        for (Field field : declaredFields) {
            //取出所有被EncryptedColumn注解的字段
            EncryptedColumn sensitiveField = field.getAnnotation(EncryptedColumn.class);
            if (!Objects.isNull(sensitiveField)) {
                field.setAccessible(true);
                Object object = null;
                try {
                    object = field.get(parameter);
                } catch (IllegalAccessException e) {
                    continue;
                }
                //只支持String的解密
                if (object instanceof String) {
                    String value = (String) object;
                    //对注解的字段进行逐一加密
                    try {
                        field.set(parameter, DBAesUtils.Encrypt(value));
                    } catch (IllegalAccessException e) {
                        continue;
                    }
                }
            }
        }
    }

    /**
     * 通过UpdateWrapper、LambdaUpdateWrapper修改数据库时
     *
     * @param entityClass
     * @param ewParam
     */
    private void encryptWrapper(Class<?> entityClass, Object ewParam) {
        AbstractWrapper updateWrapper = (AbstractWrapper) ewParam;
        String sqlSet = updateWrapper.getSqlSet();
        String[] elArr = sqlSet.split(",");
        Map<String, String> propMap = new HashMap<>(elArr.length);
        Arrays.stream(elArr).forEach(el -> {
            String[] elPart = el.split("=");
            propMap.put(elPart[0], elPart[1]);
        });

        //取出parameterType的类
        Field[] declaredFields = ReflectUtil.getFields(entityClass);
        for (Field field : declaredFields) {
            //取出所有被EncryptedColumn注解的字段
            EncryptedColumn sensitiveField = field.getAnnotation(EncryptedColumn.class);
            if (Objects.isNull(sensitiveField)) {
                continue;
            }
            String el = propMap.get(field.getName());
            Matcher matcher = PARAM_PAIRS_RE.matcher(el);
            if (matcher.matches()) {
                String valueKey = matcher.group(1);
                Object value = updateWrapper.getParamNameValuePairs().get(valueKey);
                updateWrapper.getParamNameValuePairs().put(valueKey, DBAesUtils.Encrypt(value.toString()));
            }
        }
    }
}
