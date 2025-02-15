package cn.horace.cronjob.scheduler.controller;

import cn.horace.cronjob.commons.bean.MsgObject;
import cn.horace.cronjob.commons.bean.Result;
import cn.horace.cronjob.scheduler.bean.params.GetLineDataParams;
import cn.horace.cronjob.scheduler.bean.result.LineDataItem;
import cn.horace.cronjob.scheduler.bean.result.SummaryDataResult;
import cn.horace.cronjob.scheduler.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created in 2024-11-05 22:34.
 *
 * @author Horace
 */
@RestController
@RequestMapping("/manager-api/statistics")
public class StatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);
    @Resource
    private StatisticsService statisticsService;

    /**
     * 获取概要统计数据
     *
     * @return
     */
    @PostMapping(name = "获取概要统计数据", value = "/getSummaryData")
    public MsgObject getSummaryData() {
        Result<SummaryDataResult> result = this.statisticsService.getSummaryData();
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }

    /**
     * 获取曲线图数据
     *
     * @return
     */
    @PostMapping(name = "获取曲线图数据", value = "/getLineData")
    public MsgObject getLineData(@RequestBody GetLineDataParams params) {
        Result<List<LineDataItem>> result = this.statisticsService.getLineData(params);
        if (result.isSuccess()) {
            return MsgObject.success(result.getData());
        }
        return MsgObject.msgCodes(result.getMsgCodes());
    }
}