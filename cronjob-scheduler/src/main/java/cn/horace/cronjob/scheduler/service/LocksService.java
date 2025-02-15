package cn.horace.cronjob.scheduler.service;

/**
 *
 * @author Horace
 */
public interface LocksService {

    /**
     * 获取锁的拥有者
     *
     * @return
     */
    public String getLockOwner();

    /**
     * 获取分布式锁
     *
     * @param lockId     锁ID
     * @param owner      锁的所有者
     * @param expireTime 锁过期时间，单位秒
     * @param lockDesc   锁描述
     * @return true表示加锁成功，false表示加锁失败
     */
    public boolean lock(String lockId, String owner, int expireTime, String lockDesc);

    /**
     * 释放锁
     *
     * @param lockId 锁ID
     * @param owner  锁的所有者
     * @return
     */
    public boolean releaseLock(String lockId, String owner);
}
