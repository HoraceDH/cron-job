package cn.horace.cronjob.scheduler.service.impl;

import cn.horace.cronjob.commons.utils.IPUtils;
import cn.horace.cronjob.scheduler.config.AppConfig;
import cn.horace.cronjob.scheduler.constants.LockState;
import cn.horace.cronjob.scheduler.entities.LocksEntity;
import cn.horace.cronjob.scheduler.entities.LocksEntityExample;
import cn.horace.cronjob.scheduler.mappers.LocksEntityMapper;
import cn.horace.cronjob.scheduler.service.LocksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 锁的服务类
 * <p>基于数据库的分布式锁
 * <p>----------------------------------
 * <p>1、利用唯一索引的方式
 * <p>    加锁：插入锁记录，插入成功代表获取锁成功
 * <p>    释放锁：删除记录，表示释放锁，注意不要释放了别人的锁
 * <p>
 * <p>2、使用乐观锁方式
 * <p>    加锁：当锁状态是空闲时进行更新，并且设置自己为锁拥有者
 * <p>    释放锁：更改锁状态为空闲状态，注意不要释放了别人的锁
 * <p>
 * <p>3、利用数据库事务加上行锁的方式
 * <p>    简要过程：要合理使用索引，否则容易升级成表锁
 * <p>    connection.setAutoCommit(0)
 * <p>    select * from t_locks where lock_id = x for update;
 * <p>    update table t_locks set state = 1 where lock_id = x;
 * <p>    connection.commit();
 * <p>
 *
 * @author Horace
 */
@Service
public class LocksServiceImpl implements LocksService {
    private static final Logger logger = LoggerFactory.getLogger(LocksServiceImpl.class);
    @Resource
    private AppConfig appConfig;
    @Resource
    private LocksEntityMapper locksEntityMapper;

    /**
     * 获取锁的拥有者
     *
     * @return
     */
    @Override
    public String getLockOwner() {
        return this.appConfig.getServerId() + "_" + IPUtils.getLocalIpAddress() + "_" + Thread.currentThread().getName();
    }

    /**
     * 获取分布式锁
     *
     * @param lockId     锁ID
     * @param owner      锁的所有者
     * @param expireTime 锁过期时间，单位秒
     * @param lockDesc   锁描述
     * @return true表示加锁成功，false表示加锁失败
     */
    @Override
    public boolean lock(String lockId, String owner, int expireTime, String lockDesc) {
        // 如果锁是释放状态或者锁已经超时，那么将锁的拥有者更新为自己
        Date currentDate = new Date();
        LocksEntity locksEntity = new LocksEntity();
        locksEntity.setLockId(lockId);
        locksEntity.setLockOwner(lockId + "_" + owner);
        locksEntity.setLockState(LockState.LOCKED.getValue());
        locksEntity.setExpireTime(new Date(System.currentTimeMillis() + (expireTime * 1000L)));
        LocksEntityExample example = new LocksEntityExample();

        // 由于不支持 a and (b or c)，所以通过转换的方式达到效果 (a and b) or (a and c)
        example.or().andLockIdEqualTo(lockId).andLockStateEqualTo(LockState.RELEASED.getValue());
        example.or().andLockIdEqualTo(lockId).andExpireTimeLessThan(currentDate);

        int count = this.locksEntityMapper.updateByExampleSelective(locksEntity, example);
        return count == 1;
    }

    /**
     * 释放锁
     *
     * @param lockId 锁ID
     * @param owner  锁的所有者
     * @return
     */
    @Override
    public boolean releaseLock(String lockId, String owner) {
        // 将自己的锁修改为释放状态
        LocksEntity locksEntity = new LocksEntity();
        locksEntity.setLockState(LockState.RELEASED.getValue());
        LocksEntityExample example = new LocksEntityExample();
        example.or()
                .andLockIdEqualTo(lockId)
                .andLockOwnerEqualTo(lockId + "_" + owner);
        int count = this.locksEntityMapper.updateByExampleSelective(locksEntity, example);
        // 释放锁失败
        if (count != 1) {
            logger.error("failed to release distributed lock, lockId:{}, owner:{}, entity:{}", lockId, owner, this.locksEntityMapper.selectByPrimaryKey(lockId));
        }
        return count == 1;
    }
}