package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.RoleMenuEntity;
import cn.horace.cronjob.scheduler.entities.RoleMenuEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RoleMenuEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(RoleMenuEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(RoleMenuEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(RoleMenuEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(RoleMenuEntity row);

    /**
     * 根据条件查询记录
     */
    List<RoleMenuEntity> selectByExample(RoleMenuEntityExample example);

    /**
     * 根据主键查询记录
     */
    RoleMenuEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") RoleMenuEntity row, @Param("example") RoleMenuEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") RoleMenuEntity row, @Param("example") RoleMenuEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(RoleMenuEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(RoleMenuEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<RoleMenuEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<RoleMenuEntity> list, @Param("selective") RoleMenuEntity.Column ... selective);
}