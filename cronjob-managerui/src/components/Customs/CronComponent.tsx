import React, {useEffect, useState} from "react";
import Cron, {CronFns} from "qnn-react-cron";
import {message, Tag} from "antd";
import MsgCodes from "@/typings/msgcodes";
import TaskService from "@/services/TaskService";

/**
 * 定义传递的参数
 */
interface CronProps {
    /**
     * 初始值
     */
    initValue?: string

    /**
     * 数据变更回调，? 表示非必传
     * @param data cron表达式
     */
    onDataChange?: (data: string) => void
}

/**
 * Cron表达式组件
 *
 * @constructor
 */
const CronComponent: React.FC<CronProps> = (cronProps: CronProps) => {
    let initValue = cronProps.initValue ? cronProps.initValue : "0/5 * * * * ? *";
    const [cronValue, setCronValue] = useState<string>()
    const [lastFiveTime, setLastFiveTime] = useState<string[] | undefined>()
    const [canUpdate, setCanUpdate] = useState<boolean>(true);

    // 页面渲染时调用
    useEffect(() => {
        fetchLastFiveTime(initValue).then(r => {
        });
    }, [0]);

    /**
     * 查询最近5次的执行时间
     */
    async function fetchLastFiveTime(cron: string) {
        if (canUpdate) {
            setCanUpdate(false);
            // @ts-ignore
            const result = await TaskService.getInstance().getRecentExecutionTime(cron);
            if (result.code != MsgCodes.SUCCESS) {
                message.error(result.msg + ", Cron表达式校验失败，请检查语法是否正确：" + cron);
            } else {
                setLastFiveTime(result.data);
            }
        }
    }

    return <div>
        <Cron
            value={initValue}
            getCronFns={(cronFns: CronFns) => {
                let temp = cronFns?.getValue();
                setCronValue(temp)
                fetchLastFiveTime(temp)
                if (cronProps.onDataChange) {
                    cronProps.onDataChange(temp);
                }
            }}
            onChange={(type: string, value: string) => {
                setCanUpdate(true)
            }}
            footer={[
                <div style={{textAlign: "center"}}>
                    <div style={{fontWeight: "bold"}}>
                        表达式：<Tag color={"geekblue-inverse"}>{cronValue}</Tag>
                        ，最近四次执行时间：
                    </div>
                    <br/>
                    <Tag color={"green"} style={{fontSize: 15}}>{lastFiveTime?.[0]}</Tag>
                    <Tag color={"cyan"} style={{fontSize: 15}}>{lastFiveTime?.[1]}</Tag>
                    <Tag color={"blue"} style={{fontSize: 15}}>{lastFiveTime?.[2]}</Tag>
                    <Tag color={"orange"} style={{fontSize: 15}}>{lastFiveTime?.[3]}</Tag>
                </div>
            ]}
        />
    </div>
}
export default CronComponent;