package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.LocksEntity;
import cn.horace.cronjob.scheduler.entities.LocksEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LocksEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(LocksEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(LocksEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String lockId);

    /**
     * 插入记录
     */
    int insert(LocksEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(LocksEntity row);

    /**
     * 根据条件查询记录
     */
    List<LocksEntity> selectByExample(LocksEntityExample example);

    /**
     * 根据主键查询记录
     */
    LocksEntity selectByPrimaryKey(String lockId);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") LocksEntity row, @Param("example") LocksEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") LocksEntity row, @Param("example") LocksEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(LocksEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(LocksEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<LocksEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<LocksEntity> list, @Param("selective") LocksEntity.Column ... selective);
}