package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.SchedulerInstanceEntity;
import cn.horace.cronjob.scheduler.entities.SchedulerInstanceEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SchedulerInstanceEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(SchedulerInstanceEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(SchedulerInstanceEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入记录
     */
    int insert(SchedulerInstanceEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(SchedulerInstanceEntity row);

    /**
     * 根据条件查询记录
     */
    List<SchedulerInstanceEntity> selectByExample(SchedulerInstanceEntityExample example);

    /**
     * 根据主键查询记录
     */
    SchedulerInstanceEntity selectByPrimaryKey(Integer id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") SchedulerInstanceEntity row, @Param("example") SchedulerInstanceEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") SchedulerInstanceEntity row, @Param("example") SchedulerInstanceEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(SchedulerInstanceEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(SchedulerInstanceEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<SchedulerInstanceEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<SchedulerInstanceEntity> list, @Param("selective") SchedulerInstanceEntity.Column ... selective);
}