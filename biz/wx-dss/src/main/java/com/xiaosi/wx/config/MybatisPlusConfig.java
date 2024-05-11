package com.xiaosi.wx.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.*;
import com.xiaosi.wx.encrypt.EncryptInterceptor;
import com.xiaosi.wx.permission.handler.DssDataPermissionHandler;
import com.xiaosi.wx.permission.interceptor.DssDataPermissionInterceptor;
import com.xiaosi.wx.tenant.config.TenantProperties;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: mybatis常用配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan("com.xiaosi.wx.mapper")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class MybatisPlusConfig {

    private final TenantLineInnerInterceptor tenantLineInnerInterceptor;
    private final TenantProperties tenantProperties;
    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //添加数据权限插件
        interceptor.addInnerInterceptor(new DssDataPermissionInterceptor(new DssDataPermissionHandler()));

        interceptor.addInnerInterceptor(new EncryptInterceptor());
        //多租户插件
        if (tenantProperties.getEnable()) {
            interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
        }

        /*if (tenantProperties.getEnable()) {
            interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
                @Override
                public Expression getTenantId() {

                    String merchantCode = SystemContext.getUserInfo().getMerchantCode();
                    if (Objects.isNull(merchantCode)) {
                        return new StringValue("-1");
                    } else {
                        return new StringValue(SystemContext.getUserInfo().getMerchantCode());
                    }
                }

                // 这是 default 方法,默认返回 false 表示所有表都需要拼多租户条件
                @Override
                public boolean ignoreTable(String tableName) {
                    return tenantProperties.getExclusionTable().stream().anyMatch(
                            (t) -> t.equalsIgnoreCase(tableName));
                }

                *//**
                 * 获取多租户的字段名
                 * @return String
                 *//*
                @Override
                public String getTenantIdColumn() {
                    return tenantProperties.getColumn();
                }
            }));
        }*/
        //多租户,动态表名
//        interceptor.addInnerInterceptor(new DynamicTableNameInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        /*interceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());*/
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        /*总结: 对sql进行单次改造的优先放入,不对sql进行改造的最后放入*/
        return interceptor;
    }

    /*@Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }*/

    /**
     * 乐观锁插件 当要更新一条记录的时候，希望这条记录没有被别人更新
     * https://mybatis.plus/guide/interceptor-optimistic-locker.html#optimisticlockerinnerinterceptor
     */
    @Bean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /*@Bean
    public DssDataPermissionInterceptor myInterceptor(MybatisPlusInterceptor mybatisPlusInterceptor) {
        DssDataPermissionInterceptor sql = new DssDataPermissionInterceptor();
        sql.setDataPermissionHandler(new DssDataPermissionHandler());
        List<InnerInterceptor> list = new ArrayList<>();
        // 添加数据权限插件
        list.add(sql);
        // 分页插件
        mybatisPlusInterceptor.setInterceptors(list);
        list.add(new PaginationInnerInterceptor(DbType.MYSQL));
        return sql;
    }*/
}
