package com.xiaosi.wx.permission.handler;

import com.xiaosi.wx.entity.SysUser;
import com.xiaosi.wx.permission.annotation.DssDataPermission;
import com.xiaosi.wx.utils.SecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
public class DssDataPermissionHandler {

    /**
     * 获取数据权限 SQL 片段
     *
     * @param plainSelect  查询对象
     * @param whereSegment 查询条件片段
     * @return JSqlParser 条件表达式
     */
    @SneakyThrows(Exception.class)
    public Expression getSqlSegment(PlainSelect plainSelect, String whereSegment) {
        // 待执行 SQL Where 条件表达式
        Expression where = plainSelect.getWhere();
        if (where == null) {
            where = new HexValue(" 1 = 1 ");
        }
        log.info("开始进行权限过滤,where: {},mappedStatementId: {}", where, whereSegment);
        //获取mapper名称
        String className = whereSegment.substring(0, whereSegment.lastIndexOf("."));
        //获取方法名
        String methodName = whereSegment.substring(whereSegment.lastIndexOf(".") + 1);
        Table fromItem = (Table) plainSelect.getFromItem();
        // 有别名用别名，无别名用表名，防止字段冲突报错
        Alias fromItemAlias = fromItem.getAlias();
        String mainTableName = fromItemAlias == null ? fromItem.getName() : fromItemAlias.getName();
        //获取当前mapper 的方法
        Method[] methods = Class.forName(className).getMethods();
        //遍历判断mapper 的所以方法，判断方法上是否有 UserDataPermission
        for (Method m : methods) {
            if (Objects.equals(m.getName(), methodName)) {
                DssDataPermission annotation = m.getAnnotation(DssDataPermission.class);
                if (annotation == null) {
                    return where;
                }
                // 1、当前用户Code
                SysUser user = SecurityUtils.getSysUser();
                // 查看自己的数据
                //  = 表达式
                EqualsTo usesEqualsTo = new EqualsTo();
                usesEqualsTo.setLeftExpression(new Column(mainTableName + ".creator_code"));
                usesEqualsTo.setRightExpression(new StringValue(user.getCreatorCode()));
                return new AndExpression(where, usesEqualsTo);
            }
        }
        //说明无权查看，
        where = new HexValue(" 1 = 2 ");
        return where;
    }

}
