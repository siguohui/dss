package com.xiaosi.wx.permission.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.google.common.collect.Lists;
import com.xiaosi.wx.entity.SysUser;
import com.xiaosi.wx.mapper.SysUserMapper;
import com.xiaosi.wx.permission.annotation.DssDataPermission;
import com.xiaosi.wx.permission.enums.DataPermission;
import com.xiaosi.wx.permission.enums.DataScope;
import com.xiaosi.wx.utils.ApplicationContextUtils;
import com.xiaosi.wx.utils.SecurityUtils;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
                // 2、当前角色即角色或角色类型（可能多种角色）
                Set<String> roleTypeSet = user.getRoleSet().stream().map(d->d.getDataScope()).collect(Collectors.toSet());
                DataScope scopeType = DataPermission.getScope(roleTypeSet);
                switch (scopeType) {
                    // 查看全部
                    case ALL:
                        return where;
                    case DEPT:
                        SysUserMapper sysUserMapper = ApplicationContextUtils.getApplicationContext().getBean(SysUserMapper.class);
                        List<SysUser> list = new LambdaQueryChainWrapper<>(sysUserMapper)
                                .eq(SysUser::getCompanyId, user.getCompanyId())
                                .eq(SysUser::getOfficeId, user.getOfficeId()).list();
                        ItemsList deptList = new ExpressionList(list.stream().map(SysUser::getId).map(LongValue::new).collect(Collectors.toList()));
                        InExpression inExpressiondept = new InExpression(new Column(mainTableName + ".create_by"), deptList);
                        return new AndExpression(where, inExpressiondept);
                    case MYSELF:
                        EqualsTo usesEqualsTo = new EqualsTo();
                        usesEqualsTo.setLeftExpression(new Column(mainTableName + ".create_by"));
                        usesEqualsTo.setRightExpression(new LongValue(user.getId()));
                        return new AndExpression(where, usesEqualsTo);
                    default:
                        break;
                }
                // 查看自己的数据
                //  = 表达式
//              EqualsTo usesEqualsTo = new EqualsTo();
//              usesEqualsTo.setLeftExpression(new Column(mainTableName + ".creator_code"));
//              usesEqualsTo.setRightExpression(new StringValue(user.getCreatorCode()));
//              return new AndExpression(where, usesEqualsTo);
            }
        }
        //说明无权查看，
        where = new HexValue(" 1 = 2 ");
        return where;
    }

}
