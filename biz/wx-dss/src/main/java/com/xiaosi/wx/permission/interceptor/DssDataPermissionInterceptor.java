package com.xiaosi.wx.permission.interceptor;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;

import com.xiaosi.wx.permission.handler.DssDataPermissionHandler;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SetOperationList;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.List;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DssDataPermissionInterceptor extends JsqlParserSupport implements InnerInterceptor {

    /*继承抽象类JsqlParserSupport并重写processSelect方法。JSqlParser是一个SQL语句解析器，它将SQL转换为Java类的可遍历层次结构。plus中也引入了JSqlParser包，processSelect可以对Select语句进行处理。
    实现InnerInterceptor接口并重写beforeQuery方法。InnerInterceptor是plus的插件接口，beforeQuery可以对查询语句执行前进行处理。
    DataPermissionHandler作为数据权限处理器，是一个接口，提供getSqlSegment方法添加数据权限 SQL 片段。
    由上可知，我们只需要实现DataPermissionHandler接口，并按照业务规则处理SQL，就可以实现数据权限的功能。*/

    /*willDoQuery: 当执行查询操作时，MyBatis 会调用该方法，判断是否需要执行查询操作。默认返回true，表示继续执行查询操作，如果需要阻止查询操作，则可以在实现该方法时返回false。
    beforeQuery: 在执行查询操作之前，MyBatis 会调用该方法。通过实现该方法，可以在查询之前进行一些必要的操作，例如设置数据范围、修改 SQL 等。
    willDoUpdate: 当执行更新操作时，MyBatis 会调用该方法，判断是否需要执行更新操作。默认返回true，表示继续执行更新操作，如果需要阻止更新操作，则可以在实现该方法时返回false。
    beforeUpdate: 在执行更新操作之前，MyBatis 会调用该方法。通过实现该方法，可以在更新之前进行一些必要的操作，例如设置更新时间、加密数据等。
    beforePrepare: 在执行 SQL 之前，MyBatis 会调用该方法。通过实现该方法，可以在 SQL 执行之前进行一些必要的操作，例如设置事务隔离级别、设置查询超时时间等。
    beforeGetBoundSql: 在获取 BoundSql 对象之前，MyBatis 会调用该方法。通过实现该方法，可以在获取 BoundSql 对象之前进行一些必要的操作，例如设置参数、修改 SQL 等。
    setProperties: 设置拦截器属性。该方法在创建拦截器实例时调用，用于设置拦截器的属性。
    pluginExecutor: 在创建 Executor 对象时调用，可以在这里对 Executor 对象进行包装或其他处理。*/

    /**
     * 数据权限处理器
     */
    private DssDataPermissionHandler dataPermissionHandler;

    @Override
    public boolean willDoQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
//        String whereSegment = ms.getId();
//        String className = whereSegment.substring(0, whereSegment.lastIndexOf("."));
//        String methodName = whereSegment.substring(whereSegment.lastIndexOf(".") + 1);
//        try {
//            Method[] methods = Class.forName(className).getMethods();
//            for (Method method : methods) {
//                if(method.getName().equals(methodName)){
//                    DssDataPermission annotation = AnnotationUtils.getAnnotation(method, DssDataPermission.class);
//                    if(Objects.nonNull(annotation)){
//                        return true;
//                    }
//                }
//            }
//           /*Class<?> aClass = ClassUtils.getClass(className);
//            Method method = ReflectionUtils.findMethod(aClass, methodName);*/
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        return true;
    }

    //设置数据范围、修改 SQL
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
        if (InterceptorIgnoreHelper.willIgnoreDataPermission(ms.getId())) return;
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        mpBs.sql(this.parserSingle(mpBs.sql(), ms.getId()));
    }

    //更新时间、加密数据
    /*@Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        InnerInterceptor.super.beforeUpdate(executor, ms, parameter);
    }*/

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        SelectBody selectBody = select.getSelectBody();
        if (selectBody instanceof PlainSelect) {
            this.setWhere((PlainSelect) selectBody, (String) obj);
        } else if (selectBody instanceof SetOperationList) {
            SetOperationList setOperationList = (SetOperationList) selectBody;
            List<SelectBody> selectBodyList = setOperationList.getSelects();
            selectBodyList.forEach(s -> this.setWhere((PlainSelect) s, (String) obj));
        }
    }

    /**
     * 设置 where 条件
     *
     * @param plainSelect  查询对象
     * @param whereSegment 查询条件片段
     */
    private void setWhere(PlainSelect plainSelect, String whereSegment) {
        /*Expression sqlSegment = dataPermissionHandler.getSqlSegment(plainSelect.getWhere(), whereSegment);*/
        Expression sqlSegment = this.dataPermissionHandler.getSqlSegment(plainSelect, whereSegment);
        if (null != sqlSegment) {
            plainSelect.setWhere(sqlSegment);
        }
    }
}
