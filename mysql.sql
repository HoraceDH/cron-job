create database if not exists `cron-job` default charset='utf8';
use `cron-job`;

# 租户表
create table `t_tenant` (
    `id` bigint primary key comment '主键',
    `name` varchar(50) not null comment '租户名称',
    `tenant` varchar(50) not null unique comment '租户编码',
    `alarm_config` varchar(500) not null default '{"chatId":"不设置告警","type":0}' comment '告警配置，AlarmConfig，json格式',
    `remark` varchar(500) not null comment '租户描述',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间'  on update current_timestamp(3)
) comment '租户表';

# 应用表
create table `t_app` (
    `id` bigint primary key comment '主键',
    `tenant_id` bigint not null comment '租户ID',
    `app_name` varchar(100) not null comment '应用名',
    `app_desc` varchar(100) not null comment '应用描述',
    `state` int not null default 0 comment '1：运行，2：停止',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间'  on update current_timestamp(3),
    unique key(`tenant_id`, `app_name`)
) comment '应用表';

# 用户表
create table `t_user` (
    `id` bigint primary key comment '用户ID',
    `username` varchar(255) unique not null comment '用户名',
    `password` varchar(255) not null comment '密码',
    `nickname` varchar(255) not null comment '昵称',
    `email` varchar(255) not null comment '邮箱',
    `state` int not null default 0 comment '0：正常，1：禁用',
    `phone` varchar(255) not null comment '手机号',
    `avatar` varchar(255) not null comment '头像地址',
    `address` varchar(255) default '' comment '地址',
    `signature` varchar(255) default '' comment '签名',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
) comment '用户表';

# 用户租户表
create table `t_user_tenant` (
    `id` bigint primary key comment 'ID',
    `user_id` bigint not null comment '用户ID',
    `tenant_id` bigint not null comment '租户ID',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3),
    unique (`user_id`,`tenant_id`)
) comment '用户租户表';

# 登录Token表
create table `t_token` (
    `id` varchar(255) primary key comment '用户Token',
    `user_id` bigint not null comment '用户ID',
    `expire_time` datetime(3) not null comment '过期时间',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
) comment '登录Token表';

# 菜单表
create table `t_menu` (
    `id` bigint primary key comment '菜单ID',
    `parent_id` bigint not null default 0 comment '父级菜单，0：表示顶级',
    `menu` bool not null comment '是否是菜单',
    `name` varchar(50) not null comment '菜单名称',
    `icon` varchar(100) not null comment '菜单ICON，例如 DashboardFilled',
    `path` varchar(200) comment '菜单路径',
    `locale` bool default false comment '是否国际化，默认false',
    `component` varchar(100) not null comment '对应视图组件',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
) comment '菜单表';

# 角色表
create table `t_role` (
    `id` bigint primary key comment '角色ID',
    `name` varchar(50) not null comment '角色名称',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
) comment '角色表';

# 角色菜单表
create table `t_role_menu` (
    `id` bigint primary key comment 'ID',
    `role_id` bigint not null comment '角色ID',
    `menu_id` bigint not null comment '菜单ID',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
) comment '角色菜单表';

# 用户角色表
create table `t_user_role` (
    `id` bigint primary key comment 'ID',
    `user_id` bigint not null comment '用户ID',
    `role_id` bigint not null comment '角色ID',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
) comment '用户角色表';


# 分布式锁记录表
create table `t_locks` (
    `lock_id` varchar(50) primary key comment '锁ID',
    `lock_owner` varchar(255) unique not null comment '锁的所有者',
    `lock_state` int not null comment '锁状态，1:已加锁，2:已释放，参考LockState枚举',
    `expire_time` datetime(3) not null comment '锁的过期时间',
    `lock_desc` varchar(500) default '' comment '锁的描述信息',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
) comment '用来处理分布式锁的表';

# 调度器实例表
create table `t_scheduler_instance` (
    `id` int primary key comment '调度器服务ID',
    `state` int not null comment '调度器状态，1：在线，2：离线',
    `address` varchar(255) not null unique comment '主机地址，例如：localhost:9527',
    `host_name` varchar(100) comment '主机名',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
) comment '调度器实例表';

# 执行器表
create table `t_executor` (
    `address` varchar(50) primary key comment '主键，执行器地址',
    `tenant_id` bigint not null comment '租户ID',
    `app_id` bigint not null comment '应用ID',
    `app_name` varchar(100) not null comment '应用名',
    `host_name` varchar(100) comment '主机名',
    `app_desc` varchar(100) not null comment '应用描述',
    `tag` varchar(100) not null  comment '执行器标签',
    `state` int not null comment '执行器状态，1：在线，2：离线',
    `version` varchar(500) not null comment '执行器SDK版本',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3),
    index idx_app_id_state(`app_id`, `state`),
    index idx_modify_time(`modify_time`)
) comment '执行器表';

# 任务表
create table `t_task` (
    `id` bigint primary key comment '主键',
    `tenant_id` bigint not null comment '租户ID',
    `app_id` bigint not null comment '应用ID',
    `app_name` varchar(100) not null comment '应用名',
    `app_desc` varchar(100) not null comment '应用描述',
    `name` varchar(100) not null comment '任务名称',
    `owner` varchar(50) default '' comment '任务负责人',
    `cron` varchar(100) not null comment 'cron表达式',
    `tag` varchar(100) comment '任务标签',
    `run_state` int not null comment '运行状态，1：启动，2：停止，3：未找到执行方法（停止）',
    `method` varchar(500) not null comment '任务方法，类全限定名',
    `router_strategy` int not null comment '路由策略，1：随机策略，2：分片策略',
    `expired_strategy` int not null comment '过期策略，1：过期丢弃，2：过期执行',
    `expired_time` int not null comment '过期时间，毫秒，任务超过过期时间调度时，则走过期策略',
    `failure_strategy` int not null comment '失败策略，1：失败重试，2：失败丢弃',
    `max_retry_count` int not null comment '失败重试次数',
    `failure_retry_interval` int not null comment '失败重试间隔时间，毫秒',
    `timeout` int not null comment '任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒',
    `task_params` varchar(500) comment '任务参数，一个JSON字符串，任务方法需要用String来接收',
    `remark` varchar(500) comment '描述',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3),
    unique index idx_tenant_app_method(`tenant_id`, `app_id`, `method`)
) comment '任务表';

# 任务日志表
create table `t_task_log` (
    `id` bigint primary key comment '主键',
    `tenant_id` bigint not null comment '租户ID',
    `app_id` bigint not null comment '应用ID',
    `app_name` varchar(100) not null comment '应用名',
    `app_desc` varchar(100) not null comment '应用描述',
    `name` varchar(50) not null comment '任务名',
    `owner` varchar(50) default '' comment '任务负责人',
    `task_id` bigint not null comment '任务ID',
    `executor_address` varchar(50) comment '执行器地址',
    `executor_host_name` varchar(100) comment '主机名',
    `method` varchar(500) not null comment '任务方法，类全限定名',
    `exe_type` int not null default 0 comment '执行类型，0：常规任务调度，1：管理后台立即执行，2：过期执行',
    `elapsed_time` int comment '执行耗时',
    `version` int not null comment '版本号',
    `cron` varchar(100) not null comment 'cron表达式',
    `tag` varchar(100) default '' comment '任务标签',
    `router_strategy` int not null comment '路由策略，1：随机策略，2：分片策略',
    `expired_strategy` int not null comment '过期策略，1：过期丢弃，2：过期执行',
    `expired_time` int not null comment '过期时间，任务超过过期时间调度时，则走过期策略',
    `failure_strategy` int not null comment '失败策略，1：失败重试，2：失败丢弃',
    `max_retry_count` int not null comment '失败重试次数',
    `retry_count` int default 0 comment '已经重试的次数',
    `failure_retry_interval` int not null comment '失败重试间隔时间，毫秒',
    `timeout` int not null comment '任务超时时间，超过此时间没有反馈执行结果给调度器，则认为执行器执行失败，调度器按照策略进行重试，单位毫秒',
    `scheduler_address` varchar(100) default '' comment '调度器地址，表示这个任务由哪个调度器调度',
    `state` int not null comment '执行状态，1：初始化，2：队列中，3：调度中，4：执行成功，5：执行失败，6：取消执行（预生成日志后，任务取消等情况），7：任务过期（超过执行时间而未被调度），8：执行失败，已丢弃，9：执行失败，重试中，10：执行失败，未找到执行方法',
    `task_params` varchar(500) default '' comment '任务参数，一个JSON字符串，任务方法需要用String来接收',
    `page` int not null default 1 comment '分片索引号，一般在分片任务场景用',
    `total` int not null default 1 comment '分片总数，一般在分片任务场景用',
    `parent_id` bigint comment '父级ID，在分片或者广播的路由策略下使用',
    `remark` varchar(500) default null comment '描述',
    `failed_reason` text comment '失败原因',
    `execution_time` datetime not null comment '任务执行时间',
    `dispatch_time` datetime(3) comment '调度的时间',
    `real_execution_time` datetime(3) comment '实际的任务执行时间',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3),
    index idx_create_time(`create_time`),
    index idx_task_id_state(`task_id`, `state`),
    index idx_task_id_create_time(`task_id`, `create_time`),
    index idx_execution_time_state(`execution_time`, `state`),
    index idx_task_id_execution_time(`task_id`, `execution_time`),
    index idx_tenant_id_app_name_method_execution_time(`tenant_id`, `app_name`, `method`, `execution_time`)
) comment '任务日志表';

# 统计数据表
create table `t_statistics` (
    `date_scale` datetime(3) primary key comment '统计时间，分钟级',
    `scheduler_success` int default 0 comment '调度成功',
    `scheduler_failed` int default 0 comment '调度失败',
    `delay_avg` double(10, 2) default 0 comment '平均延迟',
    `delay_max` double(10, 2) default 0 comment '最大延迟',
    `delay_min` double(10, 2) default 0 comment '最小延迟',
    `elapsed_avg` double(10, 2) default 0 comment '平均耗时',
    `elapsed_max` double(10, 2) default 0 comment '最大耗时',
    `elapsed_min` double(10, 2) default 0 comment '最小耗时',
    `before_avg` double(10, 2) default 0 comment '平均提前调度时间，毫秒',
    `before_max` double(10, 2) default 0 comment '最大提前调度时间，毫秒',
    `before_min` double(10, 2) default 0 comment '最小提前调度时间，毫秒',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3)
)  comment '统计数据表';

# 任务级别统计数据表
create table `t_task_statistics` (
    `date_scale` datetime(3) comment '统计时间，分钟级',
    `task_id` bigint not null comment '任务ID',
    `task_name` varchar(100) not null comment '任务名',
    `scheduler_success` int default 0 comment '调度成功',
    `scheduler_failed` int default 0 comment '调度失败',
    `delay_avg` double(10, 2) default 0 comment '平均延迟',
    `delay_max` double(10, 2) default 0 comment '最大延迟',
    `delay_min` double(10, 2) default 0 comment '最小延迟',
    `elapsed_avg` double(10, 2) default 0 comment '平均耗时',
    `elapsed_max` double(10, 2) default 0 comment '最大耗时',
    `elapsed_min` double(10, 2) default 0 comment '最小耗时',
    `before_avg` double(10, 2) default 0 comment '平均提前调度时间，毫秒',
    `before_max` double(10, 2) default 0 comment '最大提前调度时间，毫秒',
    `before_min` double(10, 2) default 0 comment '最小提前调度时间，毫秒',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3),
    primary key (`date_scale`, `task_id`)
)  comment '任务统计数据表';

# 告警表
create table `t_alarm` (
    `id` bigint primary key comment '主键',
    `task_log_id` bigint unique not null comment '任务日志ID',
    `app_name` varchar(100) not null comment '应用名',
    `task_name` varchar(100) not null comment '任务名',
    `executor_address` varchar(50) not null comment '执行器地址',
    `executor_host_name` varchar(100) comment '执行器主机名',
    `method` varchar(500) not null comment '任务方法，类全限定名',
    `alarm_type` int not null default 0 comment '告警方式，AlarmType枚举',
    `state` int not null default 0 comment '告警状态，AlarmState枚举',
    `alarm_group_name` varchar(200) default '' comment '告警群名称',
    `create_time` datetime(3) not null default current_timestamp(3) comment '创建时间',
    `modify_time` datetime(3) not null default current_timestamp(3) comment '修改时间' on update current_timestamp(3),
    index idx_app_name(`app_name`),
    index idx_task_name(`task_name`),
    index idx_create_time(`create_time`)
) comment '告警记录表';

# 初始化锁信息
insert into `t_locks` (lock_id, lock_owner, lock_state, expire_time, lock_desc) values ('LOCK_GENERATE_TASK', 'default1', 2, now(), '生成调度任务的分布式锁');
insert into `t_locks` (lock_id, lock_owner, lock_state, expire_time, lock_desc) values ('LOCK_GENERATE_STATISTICS', 'default2', 2, now(), '生成统计数据的分布式锁');

# 初始化用户信息，admin/admin
insert into `t_user` (id, username, password, nickname, email, phone, avatar, address, signature)
values(5982332211972542543, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'Horace', 'xxxxx@163.com', '13588888888', '/icons/default-avatar.png', '广东省广州市', '路虽远，行则将至；事虽难，做则必成！');

# 初始化菜单信息
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (1, 0, true, '调度大盘', 'DashboardTwoTone', '/welcome', './Welcome');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (2, 0, true, '系统管理', 'SettingTwoTone', '', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (3, 2, true, '用户管理', 'SmileTwoTone', '/system/user/index', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (4, 2, true, '租户管理', 'SmileTwoTone', '/system/tenant/index', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (5, 2, true, '调度器管理', 'SmileTwoTone', '/system/schedulers/index', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (6, 2, false, '菜单管理', 'SmileTwoTone', '', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (7, 0, true, '调度管理', 'AppstoreTwoTone', '', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (8, 7, true, '执行器管理', 'SmileTwoTone', '/schedulers/executor/index', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (9, 7, true, '应用管理', 'SmileTwoTone', '/schedulers/app/index', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (10, 7, true, '任务管理', 'SmileTwoTone', '/schedulers/task/index', '');
insert into `t_menu` (`id`, `parent_id`, `menu`, `name`, `icon`, `path`, `component`) values (11, 7, true, '任务日志', 'SmileTwoTone', '/schedulers/tasklog/index', '');
