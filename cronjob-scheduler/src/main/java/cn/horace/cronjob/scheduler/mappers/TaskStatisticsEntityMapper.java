package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.TaskStatisticsEntity;
import cn.horace.cronjob.scheduler.entities.TaskStatisticsEntityExample;
import cn.horace.cronjob.scheduler.entities.TaskStatisticsEntityKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskStatisticsEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(TaskStatisticsEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(TaskStatisticsEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(TaskStatisticsEntityKey key);

    /**
     * 插入记录
     */
    int insert(TaskStatisticsEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(TaskStatisticsEntity row);

    /**
     * 根据条件查询记录
     */
    List<TaskStatisticsEntity> selectByExample(TaskStatisticsEntityExample example);

    /**
     * 根据主键查询记录
     */
    TaskStatisticsEntity selectByPrimaryKey(TaskStatisticsEntityKey key);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") TaskStatisticsEntity row, @Param("example") TaskStatisticsEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") TaskStatisticsEntity row, @Param("example") TaskStatisticsEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(TaskStatisticsEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TaskStatisticsEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<TaskStatisticsEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<TaskStatisticsEntity> list, @Param("selective") TaskStatisticsEntity.Column ... selective);
}