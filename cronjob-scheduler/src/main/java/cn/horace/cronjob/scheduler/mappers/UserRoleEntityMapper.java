package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.UserRoleEntity;
import cn.horace.cronjob.scheduler.entities.UserRoleEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(UserRoleEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(UserRoleEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(UserRoleEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(UserRoleEntity row);

    /**
     * 根据条件查询记录
     */
    List<UserRoleEntity> selectByExample(UserRoleEntityExample example);

    /**
     * 根据主键查询记录
     */
    UserRoleEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") UserRoleEntity row, @Param("example") UserRoleEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") UserRoleEntity row, @Param("example") UserRoleEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(UserRoleEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserRoleEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<UserRoleEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<UserRoleEntity> list, @Param("selective") UserRoleEntity.Column ... selective);
}