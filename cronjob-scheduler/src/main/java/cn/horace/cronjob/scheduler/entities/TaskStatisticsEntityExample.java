package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskStatisticsEntityExample {
    /**
     * t_task_statistics
     */
    protected String orderByClause;

    /**
     * t_task_statistics
     */
    protected boolean distinct;

    /**
     * t_task_statistics
     */
    protected List<Criteria> oredCriteria;

    /**
     */
    public TaskStatisticsEntityExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * Create in 2025-03-01 20:03:00.771.
     * <p>
     * 对应数据库表：t_task_statistics
     * 
     * @author Horace 
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andDateScaleIsNull() {
            addCriterion("date_scale is null");
            return (Criteria) this;
        }

        public Criteria andDateScaleIsNotNull() {
            addCriterion("date_scale is not null");
            return (Criteria) this;
        }

        public Criteria andDateScaleEqualTo(Date value) {
            addCriterion("date_scale =", value, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleNotEqualTo(Date value) {
            addCriterion("date_scale <>", value, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleGreaterThan(Date value) {
            addCriterion("date_scale >", value, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleGreaterThanOrEqualTo(Date value) {
            addCriterion("date_scale >=", value, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleLessThan(Date value) {
            addCriterion("date_scale <", value, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleLessThanOrEqualTo(Date value) {
            addCriterion("date_scale <=", value, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleIn(List<Date> values) {
            addCriterion("date_scale in", values, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleNotIn(List<Date> values) {
            addCriterion("date_scale not in", values, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleBetween(Date value1, Date value2) {
            addCriterion("date_scale between", value1, value2, "dateScale");
            return (Criteria) this;
        }

        public Criteria andDateScaleNotBetween(Date value1, Date value2) {
            addCriterion("date_scale not between", value1, value2, "dateScale");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(Long value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(Long value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(Long value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(Long value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(Long value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(Long value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<Long> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<Long> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(Long value1, Long value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(Long value1, Long value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNull() {
            addCriterion("task_name is null");
            return (Criteria) this;
        }

        public Criteria andTaskNameIsNotNull() {
            addCriterion("task_name is not null");
            return (Criteria) this;
        }

        public Criteria andTaskNameEqualTo(String value) {
            addCriterion("task_name =", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotEqualTo(String value) {
            addCriterion("task_name <>", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThan(String value) {
            addCriterion("task_name >", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameGreaterThanOrEqualTo(String value) {
            addCriterion("task_name >=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThan(String value) {
            addCriterion("task_name <", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLessThanOrEqualTo(String value) {
            addCriterion("task_name <=", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameLike(String value) {
            addCriterion("task_name like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotLike(String value) {
            addCriterion("task_name not like", value, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameIn(List<String> values) {
            addCriterion("task_name in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotIn(List<String> values) {
            addCriterion("task_name not in", values, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameBetween(String value1, String value2) {
            addCriterion("task_name between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andTaskNameNotBetween(String value1, String value2) {
            addCriterion("task_name not between", value1, value2, "taskName");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessIsNull() {
            addCriterion("scheduler_success is null");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessIsNotNull() {
            addCriterion("scheduler_success is not null");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessEqualTo(Integer value) {
            addCriterion("scheduler_success =", value, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessNotEqualTo(Integer value) {
            addCriterion("scheduler_success <>", value, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessGreaterThan(Integer value) {
            addCriterion("scheduler_success >", value, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessGreaterThanOrEqualTo(Integer value) {
            addCriterion("scheduler_success >=", value, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessLessThan(Integer value) {
            addCriterion("scheduler_success <", value, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessLessThanOrEqualTo(Integer value) {
            addCriterion("scheduler_success <=", value, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessIn(List<Integer> values) {
            addCriterion("scheduler_success in", values, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessNotIn(List<Integer> values) {
            addCriterion("scheduler_success not in", values, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessBetween(Integer value1, Integer value2) {
            addCriterion("scheduler_success between", value1, value2, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerSuccessNotBetween(Integer value1, Integer value2) {
            addCriterion("scheduler_success not between", value1, value2, "schedulerSuccess");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedIsNull() {
            addCriterion("scheduler_failed is null");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedIsNotNull() {
            addCriterion("scheduler_failed is not null");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedEqualTo(Integer value) {
            addCriterion("scheduler_failed =", value, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedNotEqualTo(Integer value) {
            addCriterion("scheduler_failed <>", value, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedGreaterThan(Integer value) {
            addCriterion("scheduler_failed >", value, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedGreaterThanOrEqualTo(Integer value) {
            addCriterion("scheduler_failed >=", value, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedLessThan(Integer value) {
            addCriterion("scheduler_failed <", value, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedLessThanOrEqualTo(Integer value) {
            addCriterion("scheduler_failed <=", value, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedIn(List<Integer> values) {
            addCriterion("scheduler_failed in", values, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedNotIn(List<Integer> values) {
            addCriterion("scheduler_failed not in", values, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedBetween(Integer value1, Integer value2) {
            addCriterion("scheduler_failed between", value1, value2, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andSchedulerFailedNotBetween(Integer value1, Integer value2) {
            addCriterion("scheduler_failed not between", value1, value2, "schedulerFailed");
            return (Criteria) this;
        }

        public Criteria andDelayAvgIsNull() {
            addCriterion("delay_avg is null");
            return (Criteria) this;
        }

        public Criteria andDelayAvgIsNotNull() {
            addCriterion("delay_avg is not null");
            return (Criteria) this;
        }

        public Criteria andDelayAvgEqualTo(Double value) {
            addCriterion("delay_avg =", value, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgNotEqualTo(Double value) {
            addCriterion("delay_avg <>", value, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgGreaterThan(Double value) {
            addCriterion("delay_avg >", value, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgGreaterThanOrEqualTo(Double value) {
            addCriterion("delay_avg >=", value, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgLessThan(Double value) {
            addCriterion("delay_avg <", value, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgLessThanOrEqualTo(Double value) {
            addCriterion("delay_avg <=", value, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgIn(List<Double> values) {
            addCriterion("delay_avg in", values, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgNotIn(List<Double> values) {
            addCriterion("delay_avg not in", values, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgBetween(Double value1, Double value2) {
            addCriterion("delay_avg between", value1, value2, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayAvgNotBetween(Double value1, Double value2) {
            addCriterion("delay_avg not between", value1, value2, "delayAvg");
            return (Criteria) this;
        }

        public Criteria andDelayMaxIsNull() {
            addCriterion("delay_max is null");
            return (Criteria) this;
        }

        public Criteria andDelayMaxIsNotNull() {
            addCriterion("delay_max is not null");
            return (Criteria) this;
        }

        public Criteria andDelayMaxEqualTo(Double value) {
            addCriterion("delay_max =", value, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxNotEqualTo(Double value) {
            addCriterion("delay_max <>", value, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxGreaterThan(Double value) {
            addCriterion("delay_max >", value, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxGreaterThanOrEqualTo(Double value) {
            addCriterion("delay_max >=", value, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxLessThan(Double value) {
            addCriterion("delay_max <", value, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxLessThanOrEqualTo(Double value) {
            addCriterion("delay_max <=", value, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxIn(List<Double> values) {
            addCriterion("delay_max in", values, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxNotIn(List<Double> values) {
            addCriterion("delay_max not in", values, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxBetween(Double value1, Double value2) {
            addCriterion("delay_max between", value1, value2, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMaxNotBetween(Double value1, Double value2) {
            addCriterion("delay_max not between", value1, value2, "delayMax");
            return (Criteria) this;
        }

        public Criteria andDelayMinIsNull() {
            addCriterion("delay_min is null");
            return (Criteria) this;
        }

        public Criteria andDelayMinIsNotNull() {
            addCriterion("delay_min is not null");
            return (Criteria) this;
        }

        public Criteria andDelayMinEqualTo(Double value) {
            addCriterion("delay_min =", value, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinNotEqualTo(Double value) {
            addCriterion("delay_min <>", value, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinGreaterThan(Double value) {
            addCriterion("delay_min >", value, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinGreaterThanOrEqualTo(Double value) {
            addCriterion("delay_min >=", value, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinLessThan(Double value) {
            addCriterion("delay_min <", value, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinLessThanOrEqualTo(Double value) {
            addCriterion("delay_min <=", value, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinIn(List<Double> values) {
            addCriterion("delay_min in", values, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinNotIn(List<Double> values) {
            addCriterion("delay_min not in", values, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinBetween(Double value1, Double value2) {
            addCriterion("delay_min between", value1, value2, "delayMin");
            return (Criteria) this;
        }

        public Criteria andDelayMinNotBetween(Double value1, Double value2) {
            addCriterion("delay_min not between", value1, value2, "delayMin");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgIsNull() {
            addCriterion("elapsed_avg is null");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgIsNotNull() {
            addCriterion("elapsed_avg is not null");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgEqualTo(Double value) {
            addCriterion("elapsed_avg =", value, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgNotEqualTo(Double value) {
            addCriterion("elapsed_avg <>", value, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgGreaterThan(Double value) {
            addCriterion("elapsed_avg >", value, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgGreaterThanOrEqualTo(Double value) {
            addCriterion("elapsed_avg >=", value, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgLessThan(Double value) {
            addCriterion("elapsed_avg <", value, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgLessThanOrEqualTo(Double value) {
            addCriterion("elapsed_avg <=", value, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgIn(List<Double> values) {
            addCriterion("elapsed_avg in", values, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgNotIn(List<Double> values) {
            addCriterion("elapsed_avg not in", values, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgBetween(Double value1, Double value2) {
            addCriterion("elapsed_avg between", value1, value2, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedAvgNotBetween(Double value1, Double value2) {
            addCriterion("elapsed_avg not between", value1, value2, "elapsedAvg");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxIsNull() {
            addCriterion("elapsed_max is null");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxIsNotNull() {
            addCriterion("elapsed_max is not null");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxEqualTo(Double value) {
            addCriterion("elapsed_max =", value, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxNotEqualTo(Double value) {
            addCriterion("elapsed_max <>", value, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxGreaterThan(Double value) {
            addCriterion("elapsed_max >", value, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxGreaterThanOrEqualTo(Double value) {
            addCriterion("elapsed_max >=", value, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxLessThan(Double value) {
            addCriterion("elapsed_max <", value, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxLessThanOrEqualTo(Double value) {
            addCriterion("elapsed_max <=", value, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxIn(List<Double> values) {
            addCriterion("elapsed_max in", values, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxNotIn(List<Double> values) {
            addCriterion("elapsed_max not in", values, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxBetween(Double value1, Double value2) {
            addCriterion("elapsed_max between", value1, value2, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMaxNotBetween(Double value1, Double value2) {
            addCriterion("elapsed_max not between", value1, value2, "elapsedMax");
            return (Criteria) this;
        }

        public Criteria andElapsedMinIsNull() {
            addCriterion("elapsed_min is null");
            return (Criteria) this;
        }

        public Criteria andElapsedMinIsNotNull() {
            addCriterion("elapsed_min is not null");
            return (Criteria) this;
        }

        public Criteria andElapsedMinEqualTo(Double value) {
            addCriterion("elapsed_min =", value, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinNotEqualTo(Double value) {
            addCriterion("elapsed_min <>", value, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinGreaterThan(Double value) {
            addCriterion("elapsed_min >", value, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinGreaterThanOrEqualTo(Double value) {
            addCriterion("elapsed_min >=", value, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinLessThan(Double value) {
            addCriterion("elapsed_min <", value, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinLessThanOrEqualTo(Double value) {
            addCriterion("elapsed_min <=", value, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinIn(List<Double> values) {
            addCriterion("elapsed_min in", values, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinNotIn(List<Double> values) {
            addCriterion("elapsed_min not in", values, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinBetween(Double value1, Double value2) {
            addCriterion("elapsed_min between", value1, value2, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andElapsedMinNotBetween(Double value1, Double value2) {
            addCriterion("elapsed_min not between", value1, value2, "elapsedMin");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgIsNull() {
            addCriterion("before_avg is null");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgIsNotNull() {
            addCriterion("before_avg is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgEqualTo(Double value) {
            addCriterion("before_avg =", value, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgNotEqualTo(Double value) {
            addCriterion("before_avg <>", value, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgGreaterThan(Double value) {
            addCriterion("before_avg >", value, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgGreaterThanOrEqualTo(Double value) {
            addCriterion("before_avg >=", value, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgLessThan(Double value) {
            addCriterion("before_avg <", value, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgLessThanOrEqualTo(Double value) {
            addCriterion("before_avg <=", value, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgIn(List<Double> values) {
            addCriterion("before_avg in", values, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgNotIn(List<Double> values) {
            addCriterion("before_avg not in", values, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgBetween(Double value1, Double value2) {
            addCriterion("before_avg between", value1, value2, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeAvgNotBetween(Double value1, Double value2) {
            addCriterion("before_avg not between", value1, value2, "beforeAvg");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxIsNull() {
            addCriterion("before_max is null");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxIsNotNull() {
            addCriterion("before_max is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxEqualTo(Double value) {
            addCriterion("before_max =", value, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxNotEqualTo(Double value) {
            addCriterion("before_max <>", value, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxGreaterThan(Double value) {
            addCriterion("before_max >", value, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxGreaterThanOrEqualTo(Double value) {
            addCriterion("before_max >=", value, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxLessThan(Double value) {
            addCriterion("before_max <", value, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxLessThanOrEqualTo(Double value) {
            addCriterion("before_max <=", value, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxIn(List<Double> values) {
            addCriterion("before_max in", values, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxNotIn(List<Double> values) {
            addCriterion("before_max not in", values, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxBetween(Double value1, Double value2) {
            addCriterion("before_max between", value1, value2, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMaxNotBetween(Double value1, Double value2) {
            addCriterion("before_max not between", value1, value2, "beforeMax");
            return (Criteria) this;
        }

        public Criteria andBeforeMinIsNull() {
            addCriterion("before_min is null");
            return (Criteria) this;
        }

        public Criteria andBeforeMinIsNotNull() {
            addCriterion("before_min is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeMinEqualTo(Double value) {
            addCriterion("before_min =", value, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinNotEqualTo(Double value) {
            addCriterion("before_min <>", value, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinGreaterThan(Double value) {
            addCriterion("before_min >", value, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinGreaterThanOrEqualTo(Double value) {
            addCriterion("before_min >=", value, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinLessThan(Double value) {
            addCriterion("before_min <", value, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinLessThanOrEqualTo(Double value) {
            addCriterion("before_min <=", value, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinIn(List<Double> values) {
            addCriterion("before_min in", values, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinNotIn(List<Double> values) {
            addCriterion("before_min not in", values, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinBetween(Double value1, Double value2) {
            addCriterion("before_min between", value1, value2, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andBeforeMinNotBetween(Double value1, Double value2) {
            addCriterion("before_min not between", value1, value2, "beforeMin");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }
    }

    /**
     * Create in 2025-03-01 20:03:00.771.
     * <p>
     * 对应数据库表：t_task_statistics
     * 
     * @author Horace 
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * Create in 2025-03-01 20:03:00.771.
     * <p>
     * 对应数据库表：t_task_statistics
     * 
     * @author Horace 
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}