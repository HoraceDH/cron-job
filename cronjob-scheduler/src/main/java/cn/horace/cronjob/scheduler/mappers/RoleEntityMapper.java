package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.RoleEntity;
import cn.horace.cronjob.scheduler.entities.RoleEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(RoleEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(RoleEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(RoleEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(RoleEntity row);

    /**
     * 根据条件查询记录
     */
    List<RoleEntity> selectByExample(RoleEntityExample example);

    /**
     * 根据主键查询记录
     */
    RoleEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") RoleEntity row, @Param("example") RoleEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") RoleEntity row, @Param("example") RoleEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(RoleEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(RoleEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<RoleEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<RoleEntity> list, @Param("selective") RoleEntity.Column ... selective);
}