package cn.horace.cronjob.scheduler.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskEntityExample {
    /**
     * t_task
     */
    protected String orderByClause;

    /**
     * t_task
     */
    protected boolean distinct;

    /**
     * t_task
     */
    protected List<Criteria> oredCriteria;

    /**
     */
    public TaskEntityExample() {
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
     * Create in 2025-01-13 18:31:39.530.
     * <p>
     * 对应数据库表：t_task
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNull() {
            addCriterion("tenant_id is null");
            return (Criteria) this;
        }

        public Criteria andTenantIdIsNotNull() {
            addCriterion("tenant_id is not null");
            return (Criteria) this;
        }

        public Criteria andTenantIdEqualTo(Long value) {
            addCriterion("tenant_id =", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotEqualTo(Long value) {
            addCriterion("tenant_id <>", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThan(Long value) {
            addCriterion("tenant_id >", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdGreaterThanOrEqualTo(Long value) {
            addCriterion("tenant_id >=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThan(Long value) {
            addCriterion("tenant_id <", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdLessThanOrEqualTo(Long value) {
            addCriterion("tenant_id <=", value, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdIn(List<Long> values) {
            addCriterion("tenant_id in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotIn(List<Long> values) {
            addCriterion("tenant_id not in", values, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdBetween(Long value1, Long value2) {
            addCriterion("tenant_id between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andTenantIdNotBetween(Long value1, Long value2) {
            addCriterion("tenant_id not between", value1, value2, "tenantId");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNull() {
            addCriterion("app_id is null");
            return (Criteria) this;
        }

        public Criteria andAppIdIsNotNull() {
            addCriterion("app_id is not null");
            return (Criteria) this;
        }

        public Criteria andAppIdEqualTo(Long value) {
            addCriterion("app_id =", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotEqualTo(Long value) {
            addCriterion("app_id <>", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThan(Long value) {
            addCriterion("app_id >", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdGreaterThanOrEqualTo(Long value) {
            addCriterion("app_id >=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThan(Long value) {
            addCriterion("app_id <", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdLessThanOrEqualTo(Long value) {
            addCriterion("app_id <=", value, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdIn(List<Long> values) {
            addCriterion("app_id in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotIn(List<Long> values) {
            addCriterion("app_id not in", values, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdBetween(Long value1, Long value2) {
            addCriterion("app_id between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppIdNotBetween(Long value1, Long value2) {
            addCriterion("app_id not between", value1, value2, "appId");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("app_name not between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppDescIsNull() {
            addCriterion("app_desc is null");
            return (Criteria) this;
        }

        public Criteria andAppDescIsNotNull() {
            addCriterion("app_desc is not null");
            return (Criteria) this;
        }

        public Criteria andAppDescEqualTo(String value) {
            addCriterion("app_desc =", value, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescNotEqualTo(String value) {
            addCriterion("app_desc <>", value, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescGreaterThan(String value) {
            addCriterion("app_desc >", value, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescGreaterThanOrEqualTo(String value) {
            addCriterion("app_desc >=", value, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescLessThan(String value) {
            addCriterion("app_desc <", value, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescLessThanOrEqualTo(String value) {
            addCriterion("app_desc <=", value, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescLike(String value) {
            addCriterion("app_desc like", value, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescNotLike(String value) {
            addCriterion("app_desc not like", value, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescIn(List<String> values) {
            addCriterion("app_desc in", values, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescNotIn(List<String> values) {
            addCriterion("app_desc not in", values, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescBetween(String value1, String value2) {
            addCriterion("app_desc between", value1, value2, "appDesc");
            return (Criteria) this;
        }

        public Criteria andAppDescNotBetween(String value1, String value2) {
            addCriterion("app_desc not between", value1, value2, "appDesc");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            addCriterion("owner like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            addCriterion("owner not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            addCriterion("owner not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andCronIsNull() {
            addCriterion("cron is null");
            return (Criteria) this;
        }

        public Criteria andCronIsNotNull() {
            addCriterion("cron is not null");
            return (Criteria) this;
        }

        public Criteria andCronEqualTo(String value) {
            addCriterion("cron =", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronNotEqualTo(String value) {
            addCriterion("cron <>", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronGreaterThan(String value) {
            addCriterion("cron >", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronGreaterThanOrEqualTo(String value) {
            addCriterion("cron >=", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronLessThan(String value) {
            addCriterion("cron <", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronLessThanOrEqualTo(String value) {
            addCriterion("cron <=", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronLike(String value) {
            addCriterion("cron like", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronNotLike(String value) {
            addCriterion("cron not like", value, "cron");
            return (Criteria) this;
        }

        public Criteria andCronIn(List<String> values) {
            addCriterion("cron in", values, "cron");
            return (Criteria) this;
        }

        public Criteria andCronNotIn(List<String> values) {
            addCriterion("cron not in", values, "cron");
            return (Criteria) this;
        }

        public Criteria andCronBetween(String value1, String value2) {
            addCriterion("cron between", value1, value2, "cron");
            return (Criteria) this;
        }

        public Criteria andCronNotBetween(String value1, String value2) {
            addCriterion("cron not between", value1, value2, "cron");
            return (Criteria) this;
        }

        public Criteria andTagIsNull() {
            addCriterion("tag is null");
            return (Criteria) this;
        }

        public Criteria andTagIsNotNull() {
            addCriterion("tag is not null");
            return (Criteria) this;
        }

        public Criteria andTagEqualTo(String value) {
            addCriterion("tag =", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotEqualTo(String value) {
            addCriterion("tag <>", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThan(String value) {
            addCriterion("tag >", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagGreaterThanOrEqualTo(String value) {
            addCriterion("tag >=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThan(String value) {
            addCriterion("tag <", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLessThanOrEqualTo(String value) {
            addCriterion("tag <=", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagLike(String value) {
            addCriterion("tag like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotLike(String value) {
            addCriterion("tag not like", value, "tag");
            return (Criteria) this;
        }

        public Criteria andTagIn(List<String> values) {
            addCriterion("tag in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotIn(List<String> values) {
            addCriterion("tag not in", values, "tag");
            return (Criteria) this;
        }

        public Criteria andTagBetween(String value1, String value2) {
            addCriterion("tag between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andTagNotBetween(String value1, String value2) {
            addCriterion("tag not between", value1, value2, "tag");
            return (Criteria) this;
        }

        public Criteria andRunStateIsNull() {
            addCriterion("run_state is null");
            return (Criteria) this;
        }

        public Criteria andRunStateIsNotNull() {
            addCriterion("run_state is not null");
            return (Criteria) this;
        }

        public Criteria andRunStateEqualTo(Integer value) {
            addCriterion("run_state =", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateNotEqualTo(Integer value) {
            addCriterion("run_state <>", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateGreaterThan(Integer value) {
            addCriterion("run_state >", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateGreaterThanOrEqualTo(Integer value) {
            addCriterion("run_state >=", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateLessThan(Integer value) {
            addCriterion("run_state <", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateLessThanOrEqualTo(Integer value) {
            addCriterion("run_state <=", value, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateIn(List<Integer> values) {
            addCriterion("run_state in", values, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateNotIn(List<Integer> values) {
            addCriterion("run_state not in", values, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateBetween(Integer value1, Integer value2) {
            addCriterion("run_state between", value1, value2, "runState");
            return (Criteria) this;
        }

        public Criteria andRunStateNotBetween(Integer value1, Integer value2) {
            addCriterion("run_state not between", value1, value2, "runState");
            return (Criteria) this;
        }

        public Criteria andMethodIsNull() {
            addCriterion("method is null");
            return (Criteria) this;
        }

        public Criteria andMethodIsNotNull() {
            addCriterion("method is not null");
            return (Criteria) this;
        }

        public Criteria andMethodEqualTo(String value) {
            addCriterion("method =", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotEqualTo(String value) {
            addCriterion("method <>", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodGreaterThan(String value) {
            addCriterion("method >", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodGreaterThanOrEqualTo(String value) {
            addCriterion("method >=", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLessThan(String value) {
            addCriterion("method <", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLessThanOrEqualTo(String value) {
            addCriterion("method <=", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodLike(String value) {
            addCriterion("method like", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotLike(String value) {
            addCriterion("method not like", value, "method");
            return (Criteria) this;
        }

        public Criteria andMethodIn(List<String> values) {
            addCriterion("method in", values, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotIn(List<String> values) {
            addCriterion("method not in", values, "method");
            return (Criteria) this;
        }

        public Criteria andMethodBetween(String value1, String value2) {
            addCriterion("method between", value1, value2, "method");
            return (Criteria) this;
        }

        public Criteria andMethodNotBetween(String value1, String value2) {
            addCriterion("method not between", value1, value2, "method");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyIsNull() {
            addCriterion("router_strategy is null");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyIsNotNull() {
            addCriterion("router_strategy is not null");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyEqualTo(Integer value) {
            addCriterion("router_strategy =", value, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyNotEqualTo(Integer value) {
            addCriterion("router_strategy <>", value, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyGreaterThan(Integer value) {
            addCriterion("router_strategy >", value, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyGreaterThanOrEqualTo(Integer value) {
            addCriterion("router_strategy >=", value, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyLessThan(Integer value) {
            addCriterion("router_strategy <", value, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyLessThanOrEqualTo(Integer value) {
            addCriterion("router_strategy <=", value, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyIn(List<Integer> values) {
            addCriterion("router_strategy in", values, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyNotIn(List<Integer> values) {
            addCriterion("router_strategy not in", values, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyBetween(Integer value1, Integer value2) {
            addCriterion("router_strategy between", value1, value2, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andRouterStrategyNotBetween(Integer value1, Integer value2) {
            addCriterion("router_strategy not between", value1, value2, "routerStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyIsNull() {
            addCriterion("expired_strategy is null");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyIsNotNull() {
            addCriterion("expired_strategy is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyEqualTo(Integer value) {
            addCriterion("expired_strategy =", value, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyNotEqualTo(Integer value) {
            addCriterion("expired_strategy <>", value, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyGreaterThan(Integer value) {
            addCriterion("expired_strategy >", value, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyGreaterThanOrEqualTo(Integer value) {
            addCriterion("expired_strategy >=", value, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyLessThan(Integer value) {
            addCriterion("expired_strategy <", value, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyLessThanOrEqualTo(Integer value) {
            addCriterion("expired_strategy <=", value, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyIn(List<Integer> values) {
            addCriterion("expired_strategy in", values, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyNotIn(List<Integer> values) {
            addCriterion("expired_strategy not in", values, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyBetween(Integer value1, Integer value2) {
            addCriterion("expired_strategy between", value1, value2, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredStrategyNotBetween(Integer value1, Integer value2) {
            addCriterion("expired_strategy not between", value1, value2, "expiredStrategy");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIsNull() {
            addCriterion("expired_time is null");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIsNotNull() {
            addCriterion("expired_time is not null");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeEqualTo(Integer value) {
            addCriterion("expired_time =", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotEqualTo(Integer value) {
            addCriterion("expired_time <>", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeGreaterThan(Integer value) {
            addCriterion("expired_time >", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("expired_time >=", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeLessThan(Integer value) {
            addCriterion("expired_time <", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeLessThanOrEqualTo(Integer value) {
            addCriterion("expired_time <=", value, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeIn(List<Integer> values) {
            addCriterion("expired_time in", values, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotIn(List<Integer> values) {
            addCriterion("expired_time not in", values, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeBetween(Integer value1, Integer value2) {
            addCriterion("expired_time between", value1, value2, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andExpiredTimeNotBetween(Integer value1, Integer value2) {
            addCriterion("expired_time not between", value1, value2, "expiredTime");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyIsNull() {
            addCriterion("failure_strategy is null");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyIsNotNull() {
            addCriterion("failure_strategy is not null");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyEqualTo(Integer value) {
            addCriterion("failure_strategy =", value, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyNotEqualTo(Integer value) {
            addCriterion("failure_strategy <>", value, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyGreaterThan(Integer value) {
            addCriterion("failure_strategy >", value, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyGreaterThanOrEqualTo(Integer value) {
            addCriterion("failure_strategy >=", value, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyLessThan(Integer value) {
            addCriterion("failure_strategy <", value, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyLessThanOrEqualTo(Integer value) {
            addCriterion("failure_strategy <=", value, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyIn(List<Integer> values) {
            addCriterion("failure_strategy in", values, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyNotIn(List<Integer> values) {
            addCriterion("failure_strategy not in", values, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyBetween(Integer value1, Integer value2) {
            addCriterion("failure_strategy between", value1, value2, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andFailureStrategyNotBetween(Integer value1, Integer value2) {
            addCriterion("failure_strategy not between", value1, value2, "failureStrategy");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountIsNull() {
            addCriterion("max_retry_count is null");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountIsNotNull() {
            addCriterion("max_retry_count is not null");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountEqualTo(Integer value) {
            addCriterion("max_retry_count =", value, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountNotEqualTo(Integer value) {
            addCriterion("max_retry_count <>", value, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountGreaterThan(Integer value) {
            addCriterion("max_retry_count >", value, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("max_retry_count >=", value, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountLessThan(Integer value) {
            addCriterion("max_retry_count <", value, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountLessThanOrEqualTo(Integer value) {
            addCriterion("max_retry_count <=", value, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountIn(List<Integer> values) {
            addCriterion("max_retry_count in", values, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountNotIn(List<Integer> values) {
            addCriterion("max_retry_count not in", values, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountBetween(Integer value1, Integer value2) {
            addCriterion("max_retry_count between", value1, value2, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andMaxRetryCountNotBetween(Integer value1, Integer value2) {
            addCriterion("max_retry_count not between", value1, value2, "maxRetryCount");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalIsNull() {
            addCriterion("failure_retry_interval is null");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalIsNotNull() {
            addCriterion("failure_retry_interval is not null");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalEqualTo(Integer value) {
            addCriterion("failure_retry_interval =", value, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalNotEqualTo(Integer value) {
            addCriterion("failure_retry_interval <>", value, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalGreaterThan(Integer value) {
            addCriterion("failure_retry_interval >", value, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalGreaterThanOrEqualTo(Integer value) {
            addCriterion("failure_retry_interval >=", value, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalLessThan(Integer value) {
            addCriterion("failure_retry_interval <", value, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalLessThanOrEqualTo(Integer value) {
            addCriterion("failure_retry_interval <=", value, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalIn(List<Integer> values) {
            addCriterion("failure_retry_interval in", values, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalNotIn(List<Integer> values) {
            addCriterion("failure_retry_interval not in", values, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalBetween(Integer value1, Integer value2) {
            addCriterion("failure_retry_interval between", value1, value2, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andFailureRetryIntervalNotBetween(Integer value1, Integer value2) {
            addCriterion("failure_retry_interval not between", value1, value2, "failureRetryInterval");
            return (Criteria) this;
        }

        public Criteria andTimeoutIsNull() {
            addCriterion("timeout is null");
            return (Criteria) this;
        }

        public Criteria andTimeoutIsNotNull() {
            addCriterion("timeout is not null");
            return (Criteria) this;
        }

        public Criteria andTimeoutEqualTo(Integer value) {
            addCriterion("timeout =", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotEqualTo(Integer value) {
            addCriterion("timeout <>", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutGreaterThan(Integer value) {
            addCriterion("timeout >", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutGreaterThanOrEqualTo(Integer value) {
            addCriterion("timeout >=", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutLessThan(Integer value) {
            addCriterion("timeout <", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutLessThanOrEqualTo(Integer value) {
            addCriterion("timeout <=", value, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutIn(List<Integer> values) {
            addCriterion("timeout in", values, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotIn(List<Integer> values) {
            addCriterion("timeout not in", values, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutBetween(Integer value1, Integer value2) {
            addCriterion("timeout between", value1, value2, "timeout");
            return (Criteria) this;
        }

        public Criteria andTimeoutNotBetween(Integer value1, Integer value2) {
            addCriterion("timeout not between", value1, value2, "timeout");
            return (Criteria) this;
        }

        public Criteria andTaskParamsIsNull() {
            addCriterion("task_params is null");
            return (Criteria) this;
        }

        public Criteria andTaskParamsIsNotNull() {
            addCriterion("task_params is not null");
            return (Criteria) this;
        }

        public Criteria andTaskParamsEqualTo(String value) {
            addCriterion("task_params =", value, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsNotEqualTo(String value) {
            addCriterion("task_params <>", value, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsGreaterThan(String value) {
            addCriterion("task_params >", value, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsGreaterThanOrEqualTo(String value) {
            addCriterion("task_params >=", value, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsLessThan(String value) {
            addCriterion("task_params <", value, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsLessThanOrEqualTo(String value) {
            addCriterion("task_params <=", value, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsLike(String value) {
            addCriterion("task_params like", value, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsNotLike(String value) {
            addCriterion("task_params not like", value, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsIn(List<String> values) {
            addCriterion("task_params in", values, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsNotIn(List<String> values) {
            addCriterion("task_params not in", values, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsBetween(String value1, String value2) {
            addCriterion("task_params between", value1, value2, "taskParams");
            return (Criteria) this;
        }

        public Criteria andTaskParamsNotBetween(String value1, String value2) {
            addCriterion("task_params not between", value1, value2, "taskParams");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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
     * Create in 2025-01-13 18:31:39.530.
     * <p>
     * 对应数据库表：t_task
     * 
     * @author Horace 
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * Create in 2025-01-13 18:31:39.530.
     * <p>
     * 对应数据库表：t_task
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