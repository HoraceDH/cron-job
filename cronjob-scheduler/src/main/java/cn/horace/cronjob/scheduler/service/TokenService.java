package cn.horace.cronjob.scheduler.service;

import cn.horace.cronjob.scheduler.entities.TokenEntity;

/**
 * Token 服务
 *
 * @author Horace
 */
public interface TokenService {
    /**
     * 生成Token
     *
     * @param userId 用户ID
     * @return
     */
    String generateToken(long userId);

    /**
     * 根据TOKEN获取信息
     *
     * @param token 用户TOKEN
     * @return
     */
    TokenEntity getToken(String token);

    /**
     * 将Token过期
     *
     * @param token 用户Token
     */
    void expireToken(String token);

    /**
     * Token是否有效
     *
     * @param entity Token实体
     * @return
     */
    boolean isValid(TokenEntity entity);

    /**
     * Token 是否有效
     *
     * @param token Token
     * @return
     */
    boolean isValid(String token);

    /**
     * 将Token过期
     *
     * @param userId 用户ID
     */
    void expireToken(long userId);
}
