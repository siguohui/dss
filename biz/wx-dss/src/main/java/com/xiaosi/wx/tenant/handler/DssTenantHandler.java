package com.xiaosi.wx.tenant.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.xiaosi.wx.tenant.config.TenantProperties;
import com.xiaosi.wx.utils.TokenUtils;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.schema.Column;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 租户处理器 -主要实现mybatis-plus   TenantLineHandler
 * <p>
 * 如果用了分页插件注意先 add TenantLineInnerInterceptor
 * 再 add PaginationInnerInterceptor
 * 用了分页插件必须设置 MybatisConfiguration#useDeprecatedExecutor = false
 **/
@Slf4j
@Component
public class DssTenantHandler implements TenantLineHandler {
    @Resource
    private TokenUtils tokenUtils;
    @Resource
    private TenantProperties tenantProperties;
    /**
     * 获取租户 ID 值表达式，只支持单个 ID 值
     * <p>
     *
     * @return 租户 ID 值表达式
     */
    @Override
    public Expression getTenantId() {
        // 获取当前租户信息
        Long tenantId = tokenUtils.getTenantId();
        return new LongValue(tenantId);
    }


    /**
     * 获取租户字段名
     * <p>
     * 默认字段名叫: tenant_id
     *
     * @return 租户字段名
     */
    @Override
    public String getTenantIdColumn() {
        return tenantProperties.getColumn();
    }


    /**
     * 根据表名判断是否忽略拼接多租户条件
     * <p>
     * 默认都要进行解析并拼接多租户条件
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
     */
    @Override
    public boolean ignoreTable(String tableName) {
        return tenantProperties.getExclusionTable().contains(tableName);
    }

    @Override
    public boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
        // 新增排除自己携带了这个多租户字段的新增
        for (Column column : columns) {
            if (column.getColumnName().equalsIgnoreCase(tenantIdColumn)) {
                return true;
            }
        }
        return false;
    }
}
