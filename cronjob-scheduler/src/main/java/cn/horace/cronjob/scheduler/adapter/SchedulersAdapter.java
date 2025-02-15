package cn.horace.cronjob.scheduler.adapter;

import cn.horace.cronjob.commons.constants.Constants;
import cn.horace.cronjob.scheduler.bean.result.SchedulerInstanceItem;
import cn.horace.cronjob.scheduler.entities.SchedulerInstanceEntity;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Horace
 */
@Component
public class SchedulersAdapter {

    /**
     * 对象转换
     *
     * @param entityList 实体列表
     * @return
     */
    public List<SchedulerInstanceItem> convert(List<SchedulerInstanceEntity> entityList) {
        List<SchedulerInstanceItem> result = new ArrayList<>();
        for (SchedulerInstanceEntity entity : entityList) {
            result.add(this.convert(entity));
        }
        return result;
    }

    /**
     * 对象转换
     *
     * @param entity 实体对象
     * @return
     */
    private SchedulerInstanceItem convert(SchedulerInstanceEntity entity) {
        SchedulerInstanceItem item = new SchedulerInstanceItem();
        item.setId(entity.getId());
        item.setState(entity.getState());
        item.setAddress(entity.getAddress());
        item.setHostName(entity.getHostName());
        if (entity.getCreateTime() != null) {
            item.setCreateTime(DateFormatUtils.format(entity.getCreateTime(), Constants.DATE_FORMAT));
        }
        if (entity.getModifyTime() != null) {
            item.setModifyTime(DateFormatUtils.format(entity.getModifyTime(), Constants.DATE_FORMAT));
        }
        return item;
    }
}