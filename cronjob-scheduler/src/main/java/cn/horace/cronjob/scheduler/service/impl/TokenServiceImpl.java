package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.scheduler.entities.TokenEntity;
import cn.horace.cronjob.scheduler.entities.TokenEntityExample;
import cn.horace.cronjob.scheduler.mappers.TokenEntityMapper;
import cn.horace.cronjob.scheduler.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Token服务
 * <p>
 *
 * @author Horace
 */
@Service
public class TokenServiceImpl implements TokenService {
    private static final Logger logger = LoggerFactory.getLogger(TokenServiceImpl.class);
    @Resource
    private TokenEntityMapper mapper;

    /**
     * 生成Token
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public String generateToken(long userId) {
        String token = UUID.randomUUID().toString();
        token = token.replace("-", "");
        TokenEntity entity = new TokenEntity();
        Date date = new Date();
        entity.setId(token);
        entity.setUserId(userId);
        entity.setExpireTime(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)));
        entity.setCreateTime(date);
        this.mapper.insertSelective(entity);
        return token;
    }

    /**
     * 根据TOKEN获取信息
     *
     * @param token 用户TOKEN
     * @return
     */
    @Override
    public TokenEntity getToken(String token) {
        return this.mapper.selectByPrimaryKey(token);
    }

    /**
     * 将Token过期
     *
     * @param token 用户Token
     */
    @Override
    public void expireToken(String token) {
        TokenEntity entity = this.mapper.selectByPrimaryKey(token);
        if (entity != null) {
            this.expireToken(entity.getUserId());
        }
    }

    /**
     * Token是否有效
     *
     * @param entity Token实体
     * @return
     */
    @Override
    public boolean isValid(TokenEntity entity) {
        return entity != null && entity.getExpireTime().getTime() >= System.currentTimeMillis();
    }

    /**
     * Token 是否有效
     *
     * @param token Token
     * @return
     */
    @Override
    public boolean isValid(String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }
        TokenEntity entity = this.getToken(token);
        return this.isValid(entity);
    }

    /**
     * 将Token过期
     *
     * @param userId 用户ID
     */
    @Override
    public void expireToken(long userId) {
        TokenEntity temp = new TokenEntity();
        temp.setExpireTime(new Date());
        TokenEntityExample example = new TokenEntityExample();
        example.or().andUserIdEqualTo(userId);
        this.mapper.updateByExampleSelective(temp, example);
    }
}