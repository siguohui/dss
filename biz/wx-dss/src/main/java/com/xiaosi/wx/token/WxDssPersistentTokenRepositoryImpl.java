package com.xiaosi.wx.token;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xiaosi.wx.mapper.SysPersistentLoginMapper;
import com.xiaosi.wx.entity.SysPersistentLogin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class WxDssPersistentTokenRepositoryImpl implements PersistentTokenRepository {

    private final SysPersistentLoginMapper sysPersistentLoginMapper;

    public WxDssPersistentTokenRepositoryImpl(SysPersistentLoginMapper persistentLoginsMapper) {
        this.sysPersistentLoginMapper = persistentLoginsMapper;
    }

    /**
     * 创建token
     * @param token
     */
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        log.info("createNewToken=====>");
        SysPersistentLogin persistentLogins = new SysPersistentLogin();
        persistentLogins.setSeries(token.getSeries());
        persistentLogins.setUsername(token.getUsername());
        persistentLogins.setToken(token.getTokenValue());
        persistentLogins.setLastUsed(token.getDate());
        // 存储到数据库中
        sysPersistentLoginMapper.insert(persistentLogins);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        LambdaUpdateWrapper<SysPersistentLogin> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.set(SysPersistentLogin::getToken,tokenValue).set(SysPersistentLogin::getLastUsed,lastUsed)
                .eq(SysPersistentLogin::getSeries,series);
        // 调用修改方法
        sysPersistentLoginMapper.update(null,updateWrapper);
    }

    /**
     * 获取token
     * @param seriesId
     * @return
     */
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        LambdaQueryWrapper<SysPersistentLogin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPersistentLogin::getSeries,seriesId);
        // 调用查询方法
        SysPersistentLogin result = sysPersistentLoginMapper.selectOne(queryWrapper);

        PersistentRememberMeToken retrunResult = new PersistentRememberMeToken(
                result.getUsername(),result.getSeries(),result.getToken(),result.getLastUsed()
        );
        return retrunResult;
    }

    @Override
    public void removeUserTokens(String username) {
        LambdaUpdateWrapper<SysPersistentLogin> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SysPersistentLogin::getUsername,username);
        sysPersistentLoginMapper.delete(updateWrapper);
    }
}
