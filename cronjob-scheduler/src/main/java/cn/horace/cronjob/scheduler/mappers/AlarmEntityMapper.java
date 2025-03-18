package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.AlarmEntity;
import cn.horace.cronjob.scheduler.entities.AlarmEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AlarmEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(AlarmEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(AlarmEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(AlarmEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(AlarmEntity row);

    /**
     * 根据条件查询记录
     */
    List<AlarmEntity> selectByExample(AlarmEntityExample example);

    /**
     * 根据主键查询记录
     */
    AlarmEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") AlarmEntity row, @Param("example") AlarmEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") AlarmEntity row, @Param("example") AlarmEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(AlarmEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AlarmEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<AlarmEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<AlarmEntity> list, @Param("selective") AlarmEntity.Column ... selective);
}