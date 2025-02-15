package cn.horace.cronjob.scheduler.mappers;

import cn.horace.cronjob.scheduler.entities.TokenEntity;
import cn.horace.cronjob.scheduler.entities.TokenEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TokenEntityMapper {
    /**
     * 根据条件统计符合的记录数
     */
    long countByExample(TokenEntityExample example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(TokenEntityExample example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String id);

    /**
     * 插入记录
     */
    int insert(TokenEntity row);

    /**
     * 插入记录，不为空的字段将作为插入字段
     */
    int insertSelective(TokenEntity row);

    /**
     * 根据条件查询记录
     */
    List<TokenEntity> selectByExample(TokenEntityExample example);

    /**
     * 根据主键查询记录
     */
    TokenEntity selectByPrimaryKey(String id);

    /**
     * 根据条件更新不为空的字段
     */
    int updateByExampleSelective(@Param("row") TokenEntity row, @Param("example") TokenEntityExample example);

    /**
     * 根据条件更新所有字段
     */
    int updateByExample(@Param("row") TokenEntity row, @Param("example") TokenEntityExample example);

    /**
     * 根据主键将不为空的字段更新记录
     */
    int updateByPrimaryKeySelective(TokenEntity row);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(TokenEntity row);

    /**
     * 批量插入记录
     */
    int batchInsert(@Param("list") List<TokenEntity> list);

    /**
     * 根据字段批量插入记录
     */
    int batchInsertSelective(@Param("list") List<TokenEntity> list, @Param("selective") TokenEntity.Column ... selective);
}