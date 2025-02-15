package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.ExecutorEntity;
import cn.horace.cronjob.scheduler.entities.ExecutorEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ExecutorEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(ExecutorEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(ExecutorEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String address);

    /**
     * 插入记录
     */
    int insert(ExecutorEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(ExecutorEntity row);

    /**
     * 根据条件查询记录
     */
    List<ExecutorEntity> selectByExample(ExecutorEntityExample example);

    /**
     * 根据主键查询记录
     */
    ExecutorEntity selectByPrimaryKey(String address);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") ExecutorEntity row, @Param("example") ExecutorEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") ExecutorEntity row, @Param("example") ExecutorEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(ExecutorEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(ExecutorEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<ExecutorEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<ExecutorEntity> list, @Param("selective") ExecutorEntity.Column ... selective);
}