package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.StatisticsEntity;
import cn.horace.cronjob.scheduler.entities.StatisticsEntityExample;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StatisticsEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(StatisticsEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(StatisticsEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Date dateScale);

    /**
     * 插入记录
     */
    int insert(StatisticsEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(StatisticsEntity row);

    /**
     * 根据条件查询记录
     */
    List<StatisticsEntity> selectByExample(StatisticsEntityExample example);

    /**
     * 根据主键查询记录
     */
    StatisticsEntity selectByPrimaryKey(Date dateScale);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") StatisticsEntity row, @Param("example") StatisticsEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") StatisticsEntity row, @Param("example") StatisticsEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(StatisticsEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(StatisticsEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<StatisticsEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<StatisticsEntity> list, @Param("selective") StatisticsEntity.Column ... selective);
}