package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.MenuEntity;
import cn.horace.cronjob.scheduler.entities.MenuEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MenuEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(MenuEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(MenuEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     */
    int insert(MenuEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(MenuEntity row);

    /**
     * 根据条件查询记录
     */
    List<MenuEntity> selectByExample(MenuEntityExample example);

    /**
     * 根据主键查询记录
     */
    MenuEntity selectByPrimaryKey(Long id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") MenuEntity row, @Param("example") MenuEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") MenuEntity row, @Param("example") MenuEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(MenuEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(MenuEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<MenuEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<MenuEntity> list, @Param("selective") MenuEntity.Column ... selective);
}