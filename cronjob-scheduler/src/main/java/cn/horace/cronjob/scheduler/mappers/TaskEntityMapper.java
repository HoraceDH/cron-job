package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.TaskEntity;
import cn.horace.cronjob.scheduler.entities.TaskEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TaskEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(TaskEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(TaskEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(TaskEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(TaskEntity row);

    /**
     * 根据条件查询记录
     */
    List<TaskEntity> selectByExample(TaskEntityExample example);

    /**
     * 根据主键查询记录
     */
    TaskEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") TaskEntity row, @Param("example") TaskEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") TaskEntity row, @Param("example") TaskEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(TaskEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TaskEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<TaskEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<TaskEntity> list, @Param("selective") TaskEntity.Column ... selective);
}