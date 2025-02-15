package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.UserEntity;
import cn.horace.cronjob.scheduler.entities.UserEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(UserEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(UserEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(UserEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(UserEntity row);

    /**
     * 根据条件查询记录
     */
    List<UserEntity> selectByExample(UserEntityExample example);

    /**
     * 根据主键查询记录
     */
    UserEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") UserEntity row, @Param("example") UserEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") UserEntity row, @Param("example") UserEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(UserEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(UserEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<UserEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<UserEntity> list, @Param("selective") UserEntity.Column ... selective);
}