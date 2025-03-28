package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.UserTenantEntity;
import cn.horace.cronjob.scheduler.entities.UserTenantEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserTenantEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(UserTenantEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(UserTenantEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(UserTenantEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(UserTenantEntity row);

    /**
     * 根据条件查询记录
     */
    List<UserTenantEntity> selectByExample(UserTenantEntityExample example);

    /**
     * 根据主键查询记录
     */
    UserTenantEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") UserTenantEntity row, @Param("example") UserTenantEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") UserTenantEntity row, @Param("example") UserTenantEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(UserTenantEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserTenantEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<UserTenantEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<UserTenantEntity> list, @Param("selective") UserTenantEntity.Column ... selective);
}