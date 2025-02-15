package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocksEntityExample {
    /**
     * t_locks
     */
    protected String orderByClause;

    /**
     * t_locks
     */
    protected boolean distinct;

    /**
     * t_locks
     */
    protected List<Criteria> oredCriteria;

    /**
     */
    public LocksEntityExample() {
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
     * Create in 2024-07-27 12:24:48.
     * <p>
     * 对应数据库表：t_locks
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

        public Criteria andLockIdIsNull() {
            addCriterion("lock_id is null");
            return (Criteria) this;
        }

        public Criteria andLockIdIsNotNull() {
            addCriterion("lock_id is not null");
            return (Criteria) this;
        }

        public Criteria andLockIdEqualTo(String value) {
            addCriterion("lock_id =", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdNotEqualTo(String value) {
            addCriterion("lock_id <>", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdGreaterThan(String value) {
            addCriterion("lock_id >", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdGreaterThanOrEqualTo(String value) {
            addCriterion("lock_id >=", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdLessThan(String value) {
            addCriterion("lock_id <", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdLessThanOrEqualTo(String value) {
            addCriterion("lock_id <=", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdLike(String value) {
            addCriterion("lock_id like", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdNotLike(String value) {
            addCriterion("lock_id not like", value, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdIn(List<String> values) {
            addCriterion("lock_id in", values, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdNotIn(List<String> values) {
            addCriterion("lock_id not in", values, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdBetween(String value1, String value2) {
            addCriterion("lock_id between", value1, value2, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockIdNotBetween(String value1, String value2) {
            addCriterion("lock_id not between", value1, value2, "lockId");
            return (Criteria) this;
        }

        public Criteria andLockOwnerIsNull() {
            addCriterion("lock_owner is null");
            return (Criteria) this;
        }

        public Criteria andLockOwnerIsNotNull() {
            addCriterion("lock_owner is not null");
            return (Criteria) this;
        }

        public Criteria andLockOwnerEqualTo(String value) {
            addCriterion("lock_owner =", value, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerNotEqualTo(String value) {
            addCriterion("lock_owner <>", value, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerGreaterThan(String value) {
            addCriterion("lock_owner >", value, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("lock_owner >=", value, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerLessThan(String value) {
            addCriterion("lock_owner <", value, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerLessThanOrEqualTo(String value) {
            addCriterion("lock_owner <=", value, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerLike(String value) {
            addCriterion("lock_owner like", value, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerNotLike(String value) {
            addCriterion("lock_owner not like", value, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerIn(List<String> values) {
            addCriterion("lock_owner in", values, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerNotIn(List<String> values) {
            addCriterion("lock_owner not in", values, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerBetween(String value1, String value2) {
            addCriterion("lock_owner between", value1, value2, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockOwnerNotBetween(String value1, String value2) {
            addCriterion("lock_owner not between", value1, value2, "lockOwner");
            return (Criteria) this;
        }

        public Criteria andLockStateIsNull() {
            addCriterion("lock_state is null");
            return (Criteria) this;
        }

        public Criteria andLockStateIsNotNull() {
            addCriterion("lock_state is not null");
            return (Criteria) this;
        }

        public Criteria andLockStateEqualTo(Integer value) {
            addCriterion("lock_state =", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateNotEqualTo(Integer value) {
            addCriterion("lock_state <>", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateGreaterThan(Integer value) {
            addCriterion("lock_state >", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("lock_state >=", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateLessThan(Integer value) {
            addCriterion("lock_state <", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateLessThanOrEqualTo(Integer value) {
            addCriterion("lock_state <=", value, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateIn(List<Integer> values) {
            addCriterion("lock_state in", values, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateNotIn(List<Integer> values) {
            addCriterion("lock_state not in", values, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateBetween(Integer value1, Integer value2) {
            addCriterion("lock_state between", value1, value2, "lockState");
            return (Criteria) this;
        }

        public Criteria andLockStateNotBetween(Integer value1, Integer value2) {
            addCriterion("lock_state not between", value1, value2, "lockState");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNull() {
            addCriterion("expire_time is null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIsNotNull() {
            addCriterion("expire_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpireTimeEqualTo(Date value) {
            addCriterion("expire_time =", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotEqualTo(Date value) {
            addCriterion("expire_time <>", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThan(Date value) {
            addCriterion("expire_time >", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("expire_time >=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThan(Date value) {
            addCriterion("expire_time <", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeLessThanOrEqualTo(Date value) {
            addCriterion("expire_time <=", value, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeIn(List<Date> values) {
            addCriterion("expire_time in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotIn(List<Date> values) {
            addCriterion("expire_time not in", values, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeBetween(Date value1, Date value2) {
            addCriterion("expire_time between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andExpireTimeNotBetween(Date value1, Date value2) {
            addCriterion("expire_time not between", value1, value2, "expireTime");
            return (Criteria) this;
        }

        public Criteria andLockDescIsNull() {
            addCriterion("lock_desc is null");
            return (Criteria) this;
        }

        public Criteria andLockDescIsNotNull() {
            addCriterion("lock_desc is not null");
            return (Criteria) this;
        }

        public Criteria andLockDescEqualTo(String value) {
            addCriterion("lock_desc =", value, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescNotEqualTo(String value) {
            addCriterion("lock_desc <>", value, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescGreaterThan(String value) {
            addCriterion("lock_desc >", value, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescGreaterThanOrEqualTo(String value) {
            addCriterion("lock_desc >=", value, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescLessThan(String value) {
            addCriterion("lock_desc <", value, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescLessThanOrEqualTo(String value) {
            addCriterion("lock_desc <=", value, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescLike(String value) {
            addCriterion("lock_desc like", value, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescNotLike(String value) {
            addCriterion("lock_desc not like", value, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescIn(List<String> values) {
            addCriterion("lock_desc in", values, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescNotIn(List<String> values) {
            addCriterion("lock_desc not in", values, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescBetween(String value1, String value2) {
            addCriterion("lock_desc between", value1, value2, "lockDesc");
            return (Criteria) this;
        }

        public Criteria andLockDescNotBetween(String value1, String value2) {
            addCriterion("lock_desc not between", value1, value2, "lockDesc");
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
     * Create in 2024-07-27 12:24:48.
     * <p>
     * 对应数据库表：t_locks
     * 
     * @author Horace 
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * Create in 2024-07-27 12:24:48.
     * <p>
     * 对应数据库表：t_locks
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