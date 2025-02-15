package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.TenantEntity;
import cn.horace.cronjob.scheduler.entities.TenantEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TenantEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(TenantEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(TenantEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(TenantEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(TenantEntity row);

    /**
     * 根据条件查询记录
     */
    List<TenantEntity> selectByExample(TenantEntityExample example);

    /**
     * 根据主键查询记录
     */
    TenantEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") TenantEntity row, @Param("example") TenantEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") TenantEntity row, @Param("example") TenantEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(TenantEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TenantEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<TenantEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<TenantEntity> list, @Param("selective") TenantEntity.Column ... selective);
}