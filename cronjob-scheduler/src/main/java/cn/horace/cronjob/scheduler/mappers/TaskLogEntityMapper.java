package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.TaskLogEntity;
import cn.horace.cronjob.scheduler.entities.TaskLogEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskLogEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(TaskLogEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(TaskLogEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(TaskLogEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(TaskLogEntity row);

    /**
     */
    List<TaskLogEntity> selectByExampleWithBLOBs(TaskLogEntityExample example);

    /**
     * 根据条件查询记录
     */
    List<TaskLogEntity> selectByExample(TaskLogEntityExample example);

    /**
     * 根据主键查询记录
     */
    TaskLogEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") TaskLogEntity row, @Param("example") TaskLogEntityExample example);

    /**
     */
    int updateByExampleWithBLOBs(@Param("row") TaskLogEntity row, @Param("example") TaskLogEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") TaskLogEntity row, @Param("example") TaskLogEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(TaskLogEntity row);

    /**
     */
    int updateByPrimaryKeyWithBLOBs(TaskLogEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TaskLogEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<TaskLogEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<TaskLogEntity> list, @Param("selective") TaskLogEntity.Column ... selective);
}