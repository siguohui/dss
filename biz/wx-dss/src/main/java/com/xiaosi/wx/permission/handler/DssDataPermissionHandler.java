package com.xiaosi.wx.permission.handler;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.xiaosi.wx.common.Constant;
import com.xiaosi.wx.entity.SysRole;
import com.xiaosi.wx.entity.SysUser;
import com.xiaosi.wx.mapper.SysUserMapper;
import com.xiaosi.wx.permission.annotation.DssDataPermission;
import com.xiaosi.wx.permission.enums.DataPermission;
import com.xiaosi.wx.permission.enums.DataScope;
import com.xiaosi.wx.utils.ApplicationContextUtils;
import com.xiaosi.wx.utils.SecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 添加自定义的数据权限处理器
 */
@Slf4j
public class DssDataPermissionHandler implements DataPermissionHandler{

    /**
     * 获取数据权限 SQL 片段
     *
     * @param plainSelect  查询对象
     * @param whereSegment 查询条件片段
     * @return JSqlParser 条件表达式
     */
    @SneakyThrows(Exception.class)
    @Override
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

    @Override
    public Expression getSqlSegment(Expression where, String mappedStatementId) {
        try {
            Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (!methodName.equals(method.getName())) {
                    continue;
                }
                // 获取自定义注解，无此注解则不控制数据权限
                DssDataPermission annotation = method.getAnnotation(DssDataPermission.class);
                if (annotation == null) {
                    continue;
                }
                // 自定义的用户上下文，获取到用户的id
                SysUser user = SecurityUtils.getSysUser();
                // 如果是特权用户，不控制数据权限
                if (Constant.ADMIN_RULE.equals(user.getId())) {
                    return where;
                }
                // 员工用户
                /*if (UserTypeEnum.USER_TYPE_EMPLOYEE.getCode().equals(user.getUsertype())) {
                    // 员工用户的权限字段
                    String field = annotation.field().getValue();
                    // 单据类型
                    String billType = annotation.billType().getFuncno();
                    // 操作类型
                    OperationTypeEnum operationType = annotation.operation();
                    // 权限字段为空则为不控制数据权限
                    if (StringUtils.isNotEmpty(field)) {
                        List<DataPermission> dataPermissions = userRepository.selectUserFuncnoDataPermission(userId, billType);
                        if (dataPermissions.size() == 0) {
                            // 没数据权限，但有功能权限则取所有数据
                            return where;
                        }
                        // 构建in表达式
                        InExpression inExpression = new InExpression();
                        inExpression.setLeftExpression(new Column(field));
                        List<Expression> conditions = null;
                        switch(operationType) {
                            case SELECT:
                                conditions = dataPermissions.stream().map(res -> new StringValue(res.getStkid())).collect(Collectors.toList());
                                break;
                            case INSERT:
                                conditions = dataPermissions.stream().filter(DataPermission::isAddright).map(res -> new StringValue(res.getStkid())).collect(Collectors.toList());
                                break;
                            case UPDATE:
                                conditions = dataPermissions.stream().filter(DataPermission::isModright).map(res -> new StringValue(res.getStkid())).collect(Collectors.toList());
                                break;
                            case APPROVE:
                                conditions = dataPermissions.stream().filter(DataPermission::isCheckright).map(res -> new StringValue(res.getStkid())).collect(Collectors.toList());
                                break;
                            default:
                                break;
                        }
                        if (conditions == null) {
                            return where;
                        }
                        conditions.add(new StringValue(Constants.ALL_STORE));
                        ItemsList itemsList = new ExpressionList(conditions);
                        inExpression.setRightItemsList(itemsList);
                        if (where == null) {
                            return inExpression;
                        }
                        return new AndExpression(where, inExpression);

                    } else {
                        return where;
                    }
                } else {
                    // 供应商用户的权限字段
                    String field = annotation.vendorfield().getValue();
                    if (StringUtils.isNotEmpty(field)) {
                        // 供应商如果控制权限，则只能看到自己的单据。直接使用EqualsTo
                        EqualsTo equalsTo = new EqualsTo();
                        equalsTo.setLeftExpression(new Column(field));
                        equalsTo.setRightExpression(new StringValue(userId));
                        if (where == null) {
                            return equalsTo;
                        }
                        // 创建 AND 表达式 拼接Where 和 = 表达式
                        return new AndExpression(where, equalsTo);
                    } else {
                        return where;
                    }
                }*/
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return where;
    }

    @Override
    public Expression getSqlSegment2(PlainSelect plainSelect, String whereSegment) {
        // 待执行 SQL Where 条件表达式
        Expression where = plainSelect.getWhere();
        if (where == null) {
            where = new HexValue(" 1 = 1 ");
        }
        log.info("开始进行权限过滤,where: {},mappedStatementId: {}", where, whereSegment);
        try {
            //获取mapper名称
            String className = whereSegment.substring(0, whereSegment.lastIndexOf("."));
            //获取方法名
            String methodName = whereSegment.substring(whereSegment.lastIndexOf(".") + 1);
            Table fromItem = (Table) plainSelect.getFromItem();
            // 有别名用别名，无别名用表名，防止字段冲突报错
            Alias fromItemAlias = fromItem.getAlias();
            String mainTableName = fromItemAlias == null ? fromItem.getName() : fromItemAlias.getName();

            Class<?> clazz = Class.forName(className);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                DssDataPermission annotation = method.getAnnotation(DssDataPermission.class);
                if (ObjectUtils.isNotEmpty(annotation) && (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName))) {
                    // 获取当前的用户
//                  LoginUser loginUser = SpringUtils.getBean(TokenService.class).getLoginUser(ServletUtils.getRequest());
                    // 自定义的用户上下文，获取到用户的id
                    SysUser user = SecurityUtils.getSysUser();
                    if (ObjectUtils.isNotEmpty(user) && 1L == user.getId()) {
                        return dataScopeFilter(user, mainTableName, where);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return where;
    }


    /**
     * 构建过滤条件
     *
     * @param user 当前登录用户
     * @param where 当前查询条件
     * @return 构建后查询条件
     */
    public static Expression dataScopeFilter(SysUser user, String tableAlias, Expression where) {
        Expression expression = null;
        for (SysRole role : user.getRoleSet()) {
            String dataScope = role.getDataScope();
            if (DataPermission.DATA_MANAGER.getCode().equals(dataScope)) {
                return where;
            }
            if (DataPermission.DATA_AUDITOR.getCode().equals(dataScope)) {
                InExpression inExpression = new InExpression();
                inExpression.setLeftExpression(buildColumn(tableAlias, "dept_id"));
                SubSelect subSelect = new SubSelect();
                PlainSelect select = new PlainSelect();
                select.setSelectItems(Collections.singletonList(new SelectExpressionItem(new Column("dept_id"))));
                select.setFromItem(new Table("sys_role_dept"));
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column("role_id"));
                equalsTo.setRightExpression(new LongValue(role.getId()));
                select.setWhere(equalsTo);
                subSelect.setSelectBody(select);
                inExpression.setRightExpression(subSelect);
                expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, inExpression) : inExpression;
            }
            if (DataPermission.DATA_AUDITOR.getCode().equals(dataScope)) {
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(buildColumn(tableAlias, "dept_id"));
                equalsTo.setRightExpression(new LongValue(user.getDeptId()));
                expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, equalsTo) : equalsTo;
            }
            if (DataPermission.DATA_OPERATOR.getCode().equals(dataScope)) {
                InExpression inExpression = new InExpression();
                inExpression.setLeftExpression(buildColumn(tableAlias, "dept_id"));
                SubSelect subSelect = new SubSelect();
                PlainSelect select = new PlainSelect();
                select.setSelectItems(Collections.singletonList(new SelectExpressionItem(new Column("dept_id"))));
                select.setFromItem(new Table("sys_dept"));
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(new Column("dept_id"));
                equalsTo.setRightExpression(new LongValue(user.getDeptId()));
                Function function = new Function();
                function.setName("find_in_set");
                function.setParameters(new ExpressionList(new LongValue(user.getDeptId()) , new Column("ancestors")));
                select.setWhere(new OrExpression(equalsTo, function));
                subSelect.setSelectBody(select);
                inExpression.setRightExpression(subSelect);
                expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, inExpression) : inExpression;
            }
            if (DataPermission.DATA_AUDITOR.getCode().equals(dataScope)) {
                EqualsTo equalsTo = new EqualsTo();
                equalsTo.setLeftExpression(buildColumn(tableAlias, "create_by"));
                equalsTo.setRightExpression(new LongValue(user.getId()));
                expression = ObjectUtils.isNotEmpty(expression) ? new OrExpression(expression, equalsTo) : equalsTo;
            }
        }
        return ObjectUtils.isNotEmpty(where) ? new AndExpression(where, new Parenthesis(expression)) : expression;
    }

    /**
     * 构建Column
     *
     * @param tableAlias 表别名
     * @param columnName 字段名称
     * @return 带表别名字段
     */
    public static Column buildColumn(String tableAlias, String columnName) {
        if (StringUtils.isNotEmpty(tableAlias)) {
            columnName = tableAlias + "." + columnName;
        }
        return new Column(columnName);
    }
}
