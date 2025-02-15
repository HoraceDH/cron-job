package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.AppEntity;
import cn.horace.cronjob.scheduler.entities.AppEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(AppEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(AppEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(AppEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(AppEntity row);

    /**
     * 根据条件查询记录
     */
    List<AppEntity> selectByExample(AppEntityExample example);

    /**
     * 根据主键查询记录
     */
    AppEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") AppEntity row, @Param("example") AppEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") AppEntity row, @Param("example") AppEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(AppEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(AppEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<AppEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<AppEntity> list, @Param("selective") AppEntity.Column ... selective);
}