package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.scheduler.bean.result.AlarmItem;
import cn.horace.cronjob.scheduler.entities.AlarmEntity;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created in 2025-03-22 18:39.
 *
 * @author Horace
 */
@Component
public class AlarmAdapter {

    /**
     * 转换
     *
     * @param entityList 实体列表
     * @return
     */
    public List<AlarmItem> convert(List<AlarmEntity> entityList) {
        List<AlarmItem> result = new ArrayList<>();
        for (AlarmEntity entity : entityList) {
            result.add(this.convert(entity));
        }
        return result;
    }

    /**
     * 转换
     *
     * @param entity 实体
     * @return
     */
    private AlarmItem convert(AlarmEntity entity) {
        AlarmItem alarmItem = new AlarmItem();
        alarmItem.setId(String.valueOf(entity.getId()));
        alarmItem.setTaskLogId(String.valueOf(entity.getTaskLogId()));
        alarmItem.setAppName(entity.getAppName());
        alarmItem.setTaskName(entity.getTaskName());
        alarmItem.setExecutorAddress(entity.getExecutorAddress());
        alarmItem.setExecutorHostName(entity.getExecutorHostName());
        alarmItem.setMethod(entity.getMethod());
        alarmItem.setState(entity.getState());
        alarmItem.setAlarmType(entity.getAlarmType());
        alarmItem.setAlarmGroupName(entity.getAlarmGroupName());
        if (entity.getCreateTime() != null) {
            alarmItem.setCreateTime(DateFormatUtils.format(entity.getCreateTime(), Constants.DATE_FORMAT));
        }
        if (entity.getModifyTime() != null) {
            alarmItem.setModifyTime(DateFormatUtils.format(entity.getModifyTime(), Constants.DATE_FORMAT));
        }
        return alarmItem;
    }
}