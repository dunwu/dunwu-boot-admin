SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for code_global_config
-- ----------------------------
DROP TABLE IF EXISTS `code_global_config`;
CREATE TABLE `code_global_config` (
    `id`                BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `enable_permission` BIT(1)       DEFAULT NULL COMMENT '开启权限校验',
    `enable_override`   BIT(1)       DEFAULT NULL COMMENT '开启文件覆盖模式',
    `enable_swagger`    BIT(1)       DEFAULT NULL COMMENT '开启Swagger',
    `author`            VARCHAR(255) DEFAULT NULL COMMENT '作者',
    `output_dir`        VARCHAR(255) DEFAULT NULL COMMENT '输出路径',
    `backend_path`      VARCHAR(255) DEFAULT NULL COMMENT '后端代码路径',
    `frontend_path`     VARCHAR(255) DEFAULT NULL COMMENT '前端代码路径',
    `package_path`      VARCHAR(255) DEFAULT NULL COMMENT '包路径',
    `id_type`           VARCHAR(255) DEFAULT NULL COMMENT '主键类型',
    `date_type`         VARCHAR(255) DEFAULT NULL COMMENT '时间类型',
    `date_pattern`      VARCHAR(255) DEFAULT NULL COMMENT '时间格式化',
    `create_by`         VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`         VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time`       DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = INNODB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='代码生成-全局配置';


-- ----------------------------
-- Table structure for code_table_config
-- ----------------------------

DROP TABLE IF EXISTS `code_table_config`;
CREATE TABLE `code_table_config` (
    `id`                BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `db_id`             BIGINT(20)   NOT NULL COMMENT '数据库ID',
    `schema_name`       VARCHAR(255) NOT NULL COMMENT 'Schema名称',
    `table_name`        VARCHAR(255) NOT NULL COMMENT 'Table名称',
    `comment`           VARCHAR(255) DEFAULT NULL COMMENT 'Table注释',
    `enable_permission` BIT(1)       DEFAULT NULL COMMENT '开启权限校验',
    `enable_override`   BIT(1)       DEFAULT NULL COMMENT '开启文件覆盖模式',
    `enable_swagger`    BIT(1)       DEFAULT NULL COMMENT '开启Swagger2',
    `author`            VARCHAR(255) DEFAULT NULL COMMENT '作者',
    `output_dir`        VARCHAR(255) DEFAULT NULL COMMENT '输出路径',
    `backend_path`      VARCHAR(255) DEFAULT NULL COMMENT '后端代码路径',
    `frontend_path`     VARCHAR(255) DEFAULT NULL COMMENT '前端代码路径',
    `package_path`      VARCHAR(255) DEFAULT NULL COMMENT '包路径',
    `id_type`           VARCHAR(255) DEFAULT NULL COMMENT '主键类型',
    `date_type`         VARCHAR(255) DEFAULT NULL COMMENT '时间类型',
    `date_pattern`      VARCHAR(255) DEFAULT NULL COMMENT '时间格式',
    `enable_form`       BIT(1)       DEFAULT NULL COMMENT '允许表单',
    `enable_list`       BIT(1)       DEFAULT NULL COMMENT '允许列表',
    `enable_query`      BIT(1)       DEFAULT NULL COMMENT '允许查询',
    `enable_sort`       BIT(1)       DEFAULT NULL COMMENT '允许排序',
    `enable_validate`   BIT(1)       DEFAULT NULL COMMENT '允许校验',
    `module_name`       VARCHAR(255) DEFAULT NULL COMMENT '模块名称',
    `table_prefix`      VARCHAR(255) DEFAULT NULL COMMENT '表前缀',
    `api_base_url`      VARCHAR(255) DEFAULT NULL COMMENT 'REST接口根路径',
    `create_by`         VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`         VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time`       DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`       DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE `uniq_schema_table`(`schema_name`, `table_name`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='代码生成-表级别配置';

-- ----------------------------
-- Table structure for code_column_config
-- ----------------------------
DROP TABLE IF EXISTS `code_column_config`;
CREATE TABLE `code_column_config` (
    `id`              BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `table_id`        BIGINT(20)   DEFAULT NULL COMMENT 'Table ID',
    `schema_name`     VARCHAR(255) NOT NULL COMMENT 'Schema名称',
    `table_name`      VARCHAR(255) NOT NULL COMMENT 'Table名称',
    `field_name`      VARCHAR(255) NOT NULL COMMENT '字段名称',
    `property_name`   VARCHAR(255) DEFAULT NULL COMMENT '字段展示名称（实体字段）',
    `comment`         VARCHAR(255) DEFAULT NULL COMMENT '字段注释',
    `type`            VARCHAR(255) DEFAULT NULL COMMENT '字段数据类型',
    `java_type`       VARCHAR(255) DEFAULT NULL COMMENT '字段 Java 类型',
    `key_type`        VARCHAR(255) DEFAULT NULL COMMENT '键类型',
    `not_null`        BIT(1)       DEFAULT NULL COMMENT '不允许为空',
    `enable_form`     BIT(1)       DEFAULT NULL COMMENT '出现在表单',
    `enable_list`     BIT(1)       DEFAULT NULL COMMENT '出现在列表',
    `enable_query`    BIT(1)       DEFAULT NULL COMMENT '出现在查询',
    `enable_sort`     BIT(1)       DEFAULT NULL COMMENT '允许排序',
    `enable_validate` BIT(1)       DEFAULT NULL COMMENT '允许校验',
    `form_type`       VARCHAR(255) DEFAULT NULL COMMENT '表单类型',
    `list_type`       VARCHAR(255) DEFAULT NULL COMMENT '列表类型',
    `query_type`      VARCHAR(255) DEFAULT NULL COMMENT '查询类型',
    `sort_type`       VARCHAR(255) DEFAULT NULL COMMENT '排序类型',
    `validate_type`   VARCHAR(255) DEFAULT NULL COMMENT '校验类型',
    `date_type`       VARCHAR(255) DEFAULT NULL COMMENT '时间类型',
    `date_pattern`    VARCHAR(255) DEFAULT NULL COMMENT '时间格式',
    `dict_name`       VARCHAR(255) DEFAULT NULL COMMENT '字典名称',
    `fill`            VARCHAR(255) DEFAULT NULL COMMENT '@TableField 填充属性',
    `extra`           VARCHAR(255) DEFAULT NULL COMMENT '扩展属性',
    `create_by`       VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`       VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time`     DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE `uniq_schema_table_field`(`schema_name`, `table_name`, `field_name`)
)
    ENGINE = INNODB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='代码生成-字段级别配置';

-- ----------------------------
-- Table structure for mnt_app
-- ----------------------------
DROP TABLE IF EXISTS `mnt_app`;
CREATE TABLE `mnt_app` (
    `app_id`        BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`          VARCHAR(255)  DEFAULT NULL COMMENT '应用名称',
    `upload_path`   VARCHAR(255)  DEFAULT NULL COMMENT '上传目录',
    `deploy_path`   VARCHAR(255)  DEFAULT NULL COMMENT '部署路径',
    `backup_path`   VARCHAR(255)  DEFAULT NULL COMMENT '备份路径',
    `port`          INT(255)      DEFAULT NULL COMMENT '应用端口',
    `start_script`  VARCHAR(4000) DEFAULT NULL COMMENT '启动脚本',
    `deploy_script` VARCHAR(4000) DEFAULT NULL COMMENT '部署脚本',
    `create_by`     VARCHAR(255)  DEFAULT NULL COMMENT '创建者',
    `update_by`     VARCHAR(255)  DEFAULT NULL COMMENT '更新者',
    `create_time`   DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`app_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='应用管理';

-- ----------------------------
-- Table structure for mnt_database
-- ----------------------------
DROP TABLE IF EXISTS `mnt_database`;
CREATE TABLE `mnt_database` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(255) NOT NULL COMMENT '名称',
    `jdbc_url`    VARCHAR(255) NOT NULL COMMENT 'jdbc连接',
    `username`    VARCHAR(255) NOT NULL COMMENT '账号',
    `password`    VARCHAR(255) NOT NULL COMMENT '密码',
    `schema_name` VARCHAR(255) DEFAULT NULL COMMENT 'Schema 名',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='数据库管理';

-- ----------------------------
-- Table structure for mnt_deploy
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy`;
CREATE TABLE `mnt_deploy` (
    `deploy_id`   BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `app_id`      BIGINT(20)   DEFAULT NULL COMMENT '应用编号',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`deploy_id`) USING BTREE,
    KEY `fk6sy157pseoxx4fmcqr1vnvvhy`(`app_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='部署管理';

-- ----------------------------
-- Table structure for mnt_deploy_history
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy_history`;
CREATE TABLE `mnt_deploy_history` (
    `history_id`  VARCHAR(50)  NOT NULL COMMENT 'ID',
    `app_name`    VARCHAR(255) NOT NULL COMMENT '应用名称',
    `deploy_date` DATETIME     NOT NULL COMMENT '部署日期',
    `deploy_user` VARCHAR(50)  NOT NULL COMMENT '部署用户',
    `ip`          VARCHAR(20)  NOT NULL COMMENT '服务器IP',
    `deploy_id`   BIGINT(20) DEFAULT NULL COMMENT '部署编号',
    PRIMARY KEY (`history_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='部署历史管理';

-- ----------------------------
-- Table structure for mnt_deploy_server
-- ----------------------------
DROP TABLE IF EXISTS `mnt_deploy_server`;
CREATE TABLE `mnt_deploy_server` (
    `deploy_id` BIGINT(20) NOT NULL COMMENT '部署ID',
    `server_id` BIGINT(20) NOT NULL COMMENT '服务ID',
    PRIMARY KEY (`deploy_id`, `server_id`) USING BTREE,
    KEY `fkeaaha7jew9a02b3bk9ghols53`(`server_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='应用与服务器关联';

-- ----------------------------
-- Table structure for mnt_server
-- ----------------------------
DROP TABLE IF EXISTS `mnt_server`;
CREATE TABLE `mnt_server` (
    `server_id`   BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `account`     VARCHAR(50)  DEFAULT NULL COMMENT '账号',
    `ip`          VARCHAR(20)  DEFAULT NULL COMMENT 'IP地址',
    `name`        VARCHAR(100) DEFAULT NULL COMMENT '名称',
    `password`    VARCHAR(100) DEFAULT NULL COMMENT '密码',
    `port`        INT(11)      DEFAULT NULL COMMENT '端口',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`server_id`) USING BTREE,
    KEY `idx_ip`(`ip`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='服务器管理';

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`         BIGINT(20)   DEFAULT 0 COMMENT '上级部门',
    `sub_count`   INT(5)       DEFAULT 0 COMMENT '子部门数目',
    `name`        VARCHAR(255) NOT NULL COMMENT '名称',
    `weight`      INT(5)       DEFAULT 999 COMMENT '排序',
    `enabled`     BIT(1)       NOT NULL COMMENT '状态',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_pid`(`pid`),
    KEY `key_enabled`(`enabled`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 18
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='部门';

-- Records of sys_dept
BEGIN;
INSERT INTO `sys_dept`
VALUES (2, 7, 1, '研发部', 3, b'1', NULL, 'admin', 'admin', '2019-03-25 09:15:32', '2020-08-02 14:48:47');
INSERT INTO `sys_dept`
VALUES (5, 7, 0, '运维部', 4, b'1', NULL, 'admin', 'admin', '2019-03-25 09:20:44', '2020-05-17 14:27:27');
INSERT INTO `sys_dept`
VALUES (6, 8, 0, '测试部', 6, b'1', NULL, 'admin', 'admin', '2019-03-25 09:52:18', '2020-06-08 11:59:21');
INSERT INTO `sys_dept`
VALUES (7, 0, 2, '华南分部', 0, b'1', NULL, 'admin', 'admin', '2019-03-25 11:04:50', '2020-06-08 12:08:56');
INSERT INTO `sys_dept`
VALUES (8, 0, 2, '华北分部', 1, b'1', NULL, 'admin', 'admin', '2019-03-25 11:04:53', '2020-05-14 12:54:00');
INSERT INTO `sys_dept`
VALUES (15, 8, 0, 'UI部门', 7, b'1', NULL, 'admin', 'admin', '2020-05-13 22:56:53', '2020-05-14 12:54:13');
INSERT INTO `sys_dept`
VALUES (17, 2, 0, '研发一组', 999, b'1', NULL, 'admin', 'admin', '2020-08-02 14:49:07', '2020-08-02 14:49:07');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `code`        VARCHAR(255) NOT NULL COMMENT '字典编码',
    `name`        VARCHAR(255) NOT NULL COMMENT '字典名称',
    `enabled`     BIT(1)       NOT NULL COMMENT '状态',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='数据字典';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict`
VALUES (1, 'user_status', '用户状态', b'1', '用户状态', 'admin', NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict`
VALUES (2, 'dept_status', '部门状态', b'1', '部门状态', 'admin', NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict`
VALUES (3, 'job_status', '岗位状态', b'1', '岗位状态', 'admin', NULL, '2019-10-27 20:31:36', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_option
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_option`;
CREATE TABLE `sys_dict_option` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dict_id`     BIGINT(11)   DEFAULT NULL COMMENT '字典id',
    `code`        VARCHAR(255) NOT NULL COMMENT '字典选项编码',
    `name`        VARCHAR(255) NOT NULL COMMENT '字典选项名称',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    KEY `key_dict_id`(`dict_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='数据字典详情';

-- ----------------------------
-- Records of sys_dict_option
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_option`
VALUES (1, 1, 'true', '激活', 'admin', NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_option`
VALUES (2, 1, 'false', '禁用', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_dict_option`
VALUES (3, 2, 'true', '启用', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_dict_option`
VALUES (4, 2, 'false', '停用', 'admin', NULL, '2019-10-27 20:31:36', NULL);
INSERT INTO `sys_dict_option`
VALUES (5, 3, 'true', '启用', 'admin', NULL, NULL, NULL);
INSERT INTO `sys_dict_option`
VALUES (6, 3, 'false', '停用', 'admin', NULL, '2019-10-27 20:31:36', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(255) NOT NULL COMMENT '岗位名称',
    `weight`      INT(5)       DEFAULT NULL COMMENT '排序',
    `dept_id`     BIGINT(20)   DEFAULT NULL COMMENT '部门ID',
    `enabled`     BIT(1)       NOT NULL COMMENT '岗位状态',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_name`(`name`),
    KEY `key_enabled`(`enabled`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='岗位';

-- ----------------------------
-- Records of sys_job
-- ----------------------------
BEGIN;
INSERT INTO `sys_job`
VALUES (1, '人事专员', 3, NULL, b'1', NULL, 'admin', 'admin', '2019-03-29 14:52:28', NULL);
INSERT INTO `sys_job`
VALUES (2, '产品经理', 4, NULL, b'1', NULL, 'admin', 'admin', '2019-03-29 14:55:51', NULL);
INSERT INTO `sys_job`
VALUES (3, '全栈开发', 2, NULL, b'1', NULL, 'admin', 'admin', '2019-03-31 13:39:30', '2020-05-05 11:33:43');
INSERT INTO `sys_job`
VALUES (4, '软件测试', 5, NULL, b'1', NULL, 'admin', 'admin', '2019-03-31 13:39:43', '2020-05-10 19:56:26');
COMMIT;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
    `id`               BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `description`      VARCHAR(255) NULL     DEFAULT NULL COMMENT '日志描述信息',
    `level`            VARCHAR(10)  NULL     DEFAULT NULL COMMENT '日志级别',
    `exception`        TEXT         NULL     DEFAULT NULL COMMENT '异常信息，只有日志级别为ERROR时才有值',
    `method`           VARCHAR(255) NULL     DEFAULT NULL COMMENT '被调用方法的名称',
    `params`           TEXT         NULL     DEFAULT NULL COMMENT '被调用方法的参数',
    `username`         VARCHAR(255) NULL     DEFAULT NULL COMMENT '用户名',
    `request_ip`       VARCHAR(255) NULL     DEFAULT NULL COMMENT 'HTTP请求的IP地址',
    `request_location` VARCHAR(255) NULL     DEFAULT NULL COMMENT 'HTTP请求的地理地址',
    `request_browser`  VARCHAR(255) NULL     DEFAULT NULL COMMENT 'HTTP请求的浏览器',
    `request_time`     BIGINT(20)   NULL     DEFAULT NULL COMMENT 'HTTP请求的耗时',
    `create_time`      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '日志记录时间',
    PRIMARY KEY (`id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT = '系统日志记录';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
    `id`          BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `pid`         BIGINT(20)   DEFAULT NULL COMMENT '上级菜单ID',
    `sub_count`   INT(5)       DEFAULT 0 COMMENT '子菜单数目',
    `type`        INT(11)      DEFAULT NULL COMMENT '菜单类型',
    `title`       VARCHAR(255) DEFAULT NULL COMMENT '菜单标题',
    `name`        VARCHAR(255) DEFAULT NULL COMMENT '组件名称',
    `component`   VARCHAR(255) DEFAULT NULL COMMENT '组件',
    `weight`      INT(5)       DEFAULT NULL COMMENT '排序',
    `icon`        VARCHAR(255) DEFAULT NULL COMMENT '图标',
    `path`        VARCHAR(255) DEFAULT NULL COMMENT '链接地址',
    `i_frame`     BIT(1)       DEFAULT NULL COMMENT '是否外链',
    `cache`       BIT(1)       DEFAULT b'0' COMMENT '缓存',
    `hidden`      BIT(1)       DEFAULT b'0' COMMENT '隐藏',
    `permission`  VARCHAR(255) DEFAULT NULL COMMENT '权限',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_title`(`title`),
    UNIQUE KEY `uniq_name`(`name`),
    KEY `key_pid`(`pid`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='系统菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO `sys_menu` VALUES (1, NULL, 7, 0, '系统管理', NULL, NULL, 1, 'system', 'system', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:11:29', NULL);
INSERT INTO `sys_menu` VALUES (2, 1, 3, 1, '用户管理', 'User', 'system/user/index', 2, 'peoples', 'user', b'0', b'0', b'0', 'user:view', NULL, NULL, '2018-12-18 15:14:44', NULL);
INSERT INTO `sys_menu` VALUES (3, 1, 3, 1, '角色管理', 'Role', 'system/role/index', 3, 'role', 'role', b'0', b'0', b'0', 'roles:view', NULL, NULL, '2018-12-18 15:16:07', NULL);
INSERT INTO `sys_menu` VALUES (5, 1, 3, 1, '菜单管理', 'Menu', 'system/menu/index', 5, 'menu', 'menu', b'0', b'0', b'0', 'menu:view', NULL, NULL, '2018-12-18 15:17:28', NULL);
INSERT INTO `sys_menu` VALUES (6, NULL, 5, 0, '系统监控', NULL, NULL, 10, 'monitor', 'monitor', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:17:48', NULL);
INSERT INTO `sys_menu` VALUES (7, 6, 0, 1, '应用日志', 'Log', 'monitor/log/index', 11, 'log', 'log', b'0', b'1', b'0', NULL, NULL, 'admin', '2018-12-18 15:18:26', '2020-06-06 13:11:57');
INSERT INTO `sys_menu` VALUES (9, 6, 0, 1, 'SQL监控', 'Sql', 'monitor/sql/index', 18, 'sqlMonitor', 'druid', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-18 15:19:34', NULL);
INSERT INTO `sys_menu` VALUES (10, NULL, 5, 0, '组件管理', NULL, NULL, 50, 'zujian', 'components', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-19 13:38:16', NULL);
INSERT INTO `sys_menu` VALUES (11, 10, 0, 1, '图标库', 'Icons', 'components/icons/index', 51, 'icon', 'icon', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-19 13:38:49', NULL);
INSERT INTO `sys_menu` VALUES (14, 36, 0, 1, '邮件工具', 'Email', 'tools/email/index', 35, 'email', 'email', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-27 10:13:09', NULL);
INSERT INTO `sys_menu` VALUES (15, 10, 0, 1, '富文本', 'Editor', 'components/Editor', 52, 'fwb', 'tinymce', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-27 11:58:25', NULL);
INSERT INTO `sys_menu` VALUES (18, 36, 3, 1, '存储管理', 'Storage', 'tools/storage/index', 34, 'qiniu', 'storage', b'0', b'0', b'0', 'storage:view', NULL, NULL, '2018-12-31 11:12:15', NULL);
INSERT INTO `sys_menu` VALUES (19, 36, 0, 1, '支付宝工具', 'AliPay', 'tools/aliPay/index', 37, 'alipay', 'aliPay', b'0', b'0', b'0', NULL, NULL, NULL, '2018-12-31 14:52:38', NULL);
INSERT INTO `sys_menu` VALUES (21, NULL, 2, 0, '多级菜单', NULL, '', 900, 'menu', 'nested', b'0', b'0', b'0', NULL, NULL, 'admin', '2019-01-04 16:22:03', '2020-06-21 17:27:35');
INSERT INTO `sys_menu` VALUES (22, 21, 2, 0, '二级菜单1', NULL, '', 999, 'menu', 'menu1', b'0', b'0', b'0', NULL, NULL, 'admin', '2019-01-04 16:23:29', '2020-06-21 17:27:20');
INSERT INTO `sys_menu` VALUES (23, 21, 0, 1, '二级菜单2', NULL, 'nested/menu2/index', 999, 'menu', 'menu2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-04 16:23:57', NULL);
INSERT INTO `sys_menu` VALUES (24, 22, 0, 1, '三级菜单1', 'Test', 'nested/menu1/menu1-1', 999, 'menu', 'menu1-1', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-04 16:24:48', NULL);
INSERT INTO `sys_menu` VALUES (27, 22, 0, 1, '三级菜单2', NULL, 'nested/menu1/menu1-2', 999, 'menu', 'menu1-2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-01-07 17:27:32', NULL);
INSERT INTO `sys_menu` VALUES (28, 1, 3, 1, '任务调度', 'Timing', 'system/timing/index', 999, 'timing', 'timing', b'0', b'0', b'0', 'timing:view', NULL, NULL, '2019-01-07 20:34:40', NULL);
INSERT INTO `sys_menu` VALUES (30, 36, 0, 1, '代码生成', 'GeneratorIndex', 'generator/index', 32, 'dev', 'generator', b'0', b'1', b'0', NULL, NULL, NULL, '2019-01-11 15:45:55', NULL);
INSERT INTO `sys_menu` VALUES (33, 10, 0, 1, 'Markdown', 'Markdown', 'components/MarkDown', 53, 'markdown', 'markdown', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-08 13:46:44', NULL);
INSERT INTO `sys_menu` VALUES (34, 10, 0, 1, 'Yaml编辑器', 'YamlEdit', 'components/YamlEdit', 54, 'dev', 'yaml', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-08 15:49:40', NULL);
INSERT INTO `sys_menu` VALUES (35, 1, 3, 1, '部门管理', 'Dept', 'system/dept/index', 6, 'dept', 'dept', b'0', b'0', b'0', 'dept:view', NULL, NULL, '2019-03-25 09:46:00', NULL);
INSERT INTO `sys_menu` VALUES (36, NULL, 7, 0, '系统工具', NULL, '', 30, 'sys-tools', 'sys-tools', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-29 10:57:35', NULL);
INSERT INTO `sys_menu` VALUES (37, 1, 3, 1, '岗位管理', 'Job', 'system/job/index', 7, 'Steve-Jobs', 'job', b'0', b'0', b'0', 'job:view', NULL, NULL, '2019-03-29 13:51:18', NULL);
INSERT INTO `sys_menu` VALUES (38, 36, 0, 1, '接口文档', 'Swagger', 'tools/swagger/index', 36, 'swagger', 'swagger2', b'0', b'0', b'0', NULL, NULL, NULL, '2019-03-29 19:57:53', NULL);
INSERT INTO `sys_menu` VALUES (39, 1, 3, 1, '字典管理', 'Dict', 'system/dict/index', 8, 'dictionary', 'dict', b'0', b'0', b'0', 'dict:view', NULL, NULL, '2019-04-10 11:49:04', NULL);
INSERT INTO `sys_menu` VALUES (41, 6, 0, 1, '在线用户', 'OnlineUser', 'monitor/online/index', 10, 'Steve-Jobs', 'online', b'0', b'0', b'0', NULL, NULL, NULL, '2019-10-26 22:08:43', NULL);
INSERT INTO `sys_menu` VALUES (44, 2, 0, 2, '用户新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'user:add', NULL, NULL, '2019-10-29 10:59:46', NULL);
INSERT INTO `sys_menu` VALUES (45, 2, 0, 2, '用户编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'user:edit', NULL, NULL, '2019-10-29 11:00:08', NULL);
INSERT INTO `sys_menu` VALUES (46, 2, 0, 2, '用户删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'user:del', NULL, NULL, '2019-10-29 11:00:23', NULL);
INSERT INTO `sys_menu` VALUES (48, 3, 0, 2, '角色创建', NULL, '', 2, '', '', b'0', b'0', b'0', 'roles:add', NULL, NULL, '2019-10-29 12:45:34', NULL);
INSERT INTO `sys_menu` VALUES (49, 3, 0, 2, '角色修改', NULL, '', 3, '', '', b'0', b'0', b'0', 'roles:edit', NULL, NULL, '2019-10-29 12:46:16', NULL);
INSERT INTO `sys_menu` VALUES (50, 3, 0, 2, '角色删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'roles:del', NULL, NULL, '2019-10-29 12:46:51', NULL);
INSERT INTO `sys_menu` VALUES (52, 5, 0, 2, '菜单新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'menu:add', NULL, NULL, '2019-10-29 12:55:07', NULL);
INSERT INTO `sys_menu` VALUES (53, 5, 0, 2, '菜单编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'menu:edit', NULL, NULL, '2019-10-29 12:55:40', NULL);
INSERT INTO `sys_menu` VALUES (54, 5, 0, 2, '菜单删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'menu:del', NULL, NULL, '2019-10-29 12:56:00', NULL);
INSERT INTO `sys_menu` VALUES (56, 35, 0, 2, '部门新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'dept:add', NULL, NULL, '2019-10-29 12:57:09', NULL);
INSERT INTO `sys_menu` VALUES (57, 35, 0, 2, '部门编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'dept:edit', NULL, NULL, '2019-10-29 12:57:27', NULL);
INSERT INTO `sys_menu` VALUES (58, 35, 0, 2, '部门删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'dept:del', NULL, NULL, '2019-10-29 12:57:41', NULL);
INSERT INTO `sys_menu` VALUES (60, 37, 0, 2, '岗位新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'job:add', NULL, NULL, '2019-10-29 12:58:27', NULL);
INSERT INTO `sys_menu` VALUES (61, 37, 0, 2, '岗位编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'job:edit', NULL, NULL, '2019-10-29 12:58:45', NULL);
INSERT INTO `sys_menu` VALUES (62, 37, 0, 2, '岗位删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'job:del', NULL, NULL, '2019-10-29 12:59:04', NULL);
INSERT INTO `sys_menu` VALUES (64, 39, 0, 2, '字典新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'dict:add', NULL, NULL, '2019-10-29 13:00:17', NULL);
INSERT INTO `sys_menu` VALUES (65, 39, 0, 2, '字典编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'dict:edit', NULL, NULL, '2019-10-29 13:00:42', NULL);
INSERT INTO `sys_menu` VALUES (66, 39, 0, 2, '字典删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'dict:del', NULL, NULL, '2019-10-29 13:00:59', NULL);
INSERT INTO `sys_menu` VALUES (73, 28, 0, 2, '任务新增', NULL, '', 2, '', '', b'0', b'0', b'0', 'timing:add', NULL, NULL, '2019-10-29 13:07:28', NULL);
INSERT INTO `sys_menu` VALUES (74, 28, 0, 2, '任务编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'timing:edit', NULL, NULL, '2019-10-29 13:07:41', NULL);
INSERT INTO `sys_menu` VALUES (75, 28, 0, 2, '任务删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'timing:del', NULL, NULL, '2019-10-29 13:07:54', NULL);
INSERT INTO `sys_menu` VALUES (77, 18, 0, 2, '上传文件', NULL, '', 2, '', '', b'0', b'0', b'0', 'storage:add', NULL, NULL, '2019-10-29 13:09:09', NULL);
INSERT INTO `sys_menu` VALUES (78, 18, 0, 2, '文件编辑', NULL, '', 3, '', '', b'0', b'0', b'0', 'storage:edit', NULL, NULL, '2019-10-29 13:09:22', NULL);
INSERT INTO `sys_menu` VALUES (79, 18, 0, 2, '文件删除', NULL, '', 4, '', '', b'0', b'0', b'0', 'storage:del', NULL, NULL, '2019-10-29 13:09:34', NULL);
INSERT INTO `sys_menu` VALUES (80, 6, 0, 1, '服务监控', 'ServerMonitor', 'monitor/server/index', 14, 'codeConsole', 'server', b'0', b'0', b'0', 'monitor:view', NULL, 'admin', '2019-11-07 13:06:39', '2020-05-04 18:20:50');
INSERT INTO `sys_menu` VALUES (82, 36, 0, 1, '生成配置', 'GeneratorConfig', 'generator/config', 33, 'dev', 'generator/config/:dbId/:schemaName/:tableName', b'0', b'1', b'1', '', NULL, NULL, '2019-11-17 20:08:56', NULL);
INSERT INTO `sys_menu` VALUES (83, 10, 0, 1, '图表库', 'Echarts', 'components/Echarts', 50, 'chart', 'echarts', b'0', b'1', b'0', '', NULL, NULL, '2019-11-21 09:04:32', NULL);
INSERT INTO `sys_menu` VALUES (90, NULL, 5, 1, '运维管理', 'Mnt', '', 20, 'mnt', 'mnt', b'0', b'0', b'0', NULL, NULL, NULL, '2019-11-09 10:31:08', NULL);
INSERT INTO `sys_menu` VALUES (92, 90, 3, 1, '服务器', 'ServerDeploy', 'mnt/server/index', 22, 'server', 'mnt/serverDeploy', b'0', b'0', b'0', 'serverDeploy:view', NULL, NULL, '2019-11-10 10:29:25', NULL);
INSERT INTO `sys_menu` VALUES (93, 90, 3, 1, '应用管理', 'App', 'mnt/app/index', 23, 'app', 'mnt/app', b'0', b'0', b'0', 'app:view', NULL, NULL, '2019-11-10 11:05:16', NULL);
INSERT INTO `sys_menu` VALUES (94, 90, 3, 1, '部署管理', 'Deploy', 'mnt/deploy/index', 24, 'deploy', 'mnt/deploy', b'0', b'0', b'0', 'deploy:view', NULL, NULL, '2019-11-10 15:56:55', NULL);
INSERT INTO `sys_menu` VALUES (97, 90, 1, 1, '部署备份', 'DeployHistory', 'mnt/deployHistory/index', 25, 'backup', 'mnt/deployHistory', b'0', b'0', b'0', 'deployHistory:view', NULL, NULL, '2019-11-10 16:49:44', NULL);
INSERT INTO `sys_menu` VALUES (98, 90, 3, 1, '数据库管理', 'Database', 'mnt/database/index', 26, 'database', 'mnt/database', b'0', b'0', b'0', 'database:view', NULL, NULL, '2019-11-10 20:40:04', NULL);
INSERT INTO `sys_menu` VALUES (102, 97, 0, 2, '删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'deployHistory:del', NULL, NULL, '2019-11-17 09:32:48', NULL);
INSERT INTO `sys_menu` VALUES (103, 92, 0, 2, '服务器新增', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:add', NULL, NULL, '2019-11-17 11:08:33', NULL);
INSERT INTO `sys_menu` VALUES (104, 92, 0, 2, '服务器编辑', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:edit', NULL, NULL, '2019-11-17 11:08:57', NULL);
INSERT INTO `sys_menu` VALUES (105, 92, 0, 2, '服务器删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'serverDeploy:del', NULL, NULL, '2019-11-17 11:09:15', NULL);
INSERT INTO `sys_menu` VALUES (106, 93, 0, 2, '应用新增', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:add', NULL, NULL, '2019-11-17 11:10:03', NULL);
INSERT INTO `sys_menu` VALUES (107, 93, 0, 2, '应用编辑', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:edit', NULL, NULL, '2019-11-17 11:10:28', NULL);
INSERT INTO `sys_menu` VALUES (108, 93, 0, 2, '应用删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'app:del', NULL, NULL, '2019-11-17 11:10:55', NULL);
INSERT INTO `sys_menu` VALUES (109, 94, 0, 2, '部署新增', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:add', NULL, NULL, '2019-11-17 11:11:22', NULL);
INSERT INTO `sys_menu` VALUES (110, 94, 0, 2, '部署编辑', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:edit', NULL, NULL, '2019-11-17 11:11:41', NULL);
INSERT INTO `sys_menu` VALUES (111, 94, 0, 2, '部署删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'deploy:del', NULL, NULL, '2019-11-17 11:12:01', NULL);
INSERT INTO `sys_menu` VALUES (112, 98, 0, 2, '数据库新增', NULL, '', 999, '', '', b'0', b'0', b'0', 'database:add', NULL, NULL, '2019-11-17 11:12:43', NULL);
INSERT INTO `sys_menu` VALUES (113, 98, 0, 2, '数据库编辑', NULL, '', 999, '', '', b'0', b'0', b'0', 'database:edit', NULL, NULL, '2019-11-17 11:12:58', NULL);
INSERT INTO `sys_menu` VALUES (114, 98, 0, 2, '数据库删除', NULL, '', 999, '', '', b'0', b'0', b'0', 'database:del', NULL, NULL, '2019-11-17 11:13:14', NULL);
INSERT INTO `sys_menu` VALUES (116, 36, 0, 1, '生成预览', 'Preview', 'generator/preview', 999, 'java', 'generator/preview/:dbId/:schemaName/:tableName', b'0', b'1', b'1', NULL, NULL, NULL, '2019-11-26 14:54:36', NULL);
INSERT INTO `sys_menu` VALUES (117, NULL, 5, 0, '项目案例', NULL, NULL, 50, 'demo', 'demo', b'0', b'0', b'0', NULL, NULL, NULL, '2021-12-19 13:38:16', NULL);
COMMIT;
-- @formatter:on

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(255) NOT NULL COMMENT '名称',
    `level`       INT(255)     DEFAULT NULL COMMENT '角色级别',
    `data_scope`  VARCHAR(255) DEFAULT NULL COMMENT '数据权限',
    `enabled`     BIT(1)       NOT NULL COMMENT '岗位状态',
    `note`        VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_name`(`name`),
    KEY `role_name_index`(`name`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role`
VALUES (1, '超级管理员', 1, '全部', b'1', '-', NULL, 'admin', '2018-11-23 11:04:37', '2020-08-06 16:10:24');
INSERT INTO `sys_role`
VALUES (2, '普通用户', 2, '本级', b'1', '-', NULL, 'admin', '2018-11-23 13:09:06', '2020-09-05 10:45:12');
COMMIT;

-- ----------------------------
-- Table structure for sys_roles_depts
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_depts`;
CREATE TABLE `sys_roles_depts` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `role_id` BIGINT(20)          NOT NULL,
    `dept_id` BIGINT(20)          NOT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu`(`role_id`, `dept_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色部门关联';

-- ----------------------------
-- Table structure for sys_roles_menus
-- ----------------------------
DROP TABLE IF EXISTS `sys_roles_menus`;
CREATE TABLE `sys_roles_menus` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `menu_id` BIGINT(20)          NOT NULL COMMENT '菜单ID',
    `role_id` BIGINT(20)          NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_role_menu`(`menu_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='角色菜单关联';

-- ----------------------------
-- Records of sys_roles_menus
-- ----------------------------
BEGIN;
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (1, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (2, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (3, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (5, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (6, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (7, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (9, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (10, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (11, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (14, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (15, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (18, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (19, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (21, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (22, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (23, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (24, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (27, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (28, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (30, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (33, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (34, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (35, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (36, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (37, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (38, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (39, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (41, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (44, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (45, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (46, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (48, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (49, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (50, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (52, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (53, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (54, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (56, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (57, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (58, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (60, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (61, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (62, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (64, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (65, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (66, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (73, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (74, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (75, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (77, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (78, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (79, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (80, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (82, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (83, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (90, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (92, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (93, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (94, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (97, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (98, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (102, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (103, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (104, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (105, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (106, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (107, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (108, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (109, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (110, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (111, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (112, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (113, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (114, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (116, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (117, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (120, 1);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (1, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (2, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (6, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (7, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (9, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (10, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (11, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (14, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (15, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (19, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (21, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (22, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (23, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (24, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (27, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (30, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (33, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (34, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (36, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (80, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (82, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (83, 2);
INSERT INTO `sys_roles_menus`(`menu_id`, `role_id`)
VALUES (116, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_jobs_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_role`;
CREATE TABLE `sys_job_role` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `job_id`  BIGINT(20)          NOT NULL COMMENT '岗位ID',
    `role_id` BIGINT(20)          NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_job_role`(`job_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='系统岗位/角色关系表';

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id`             BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `dept_id`        BIGINT(20)   DEFAULT NULL COMMENT '部门ID',
    `job_id`         BIGINT(20)   DEFAULT NULL COMMENT '岗位ID',
    `username`       VARCHAR(255) DEFAULT NULL COMMENT '用户名',
    `nickname`       VARCHAR(255) DEFAULT NULL COMMENT '昵称',
    `gender`         VARCHAR(2)   DEFAULT NULL COMMENT '性别',
    `phone`          VARCHAR(255) DEFAULT NULL COMMENT '手机号码',
    `email`          VARCHAR(255) DEFAULT NULL COMMENT '邮箱',
    `avatar`         VARCHAR(255) DEFAULT NULL COMMENT '头像地址',
    `password`       VARCHAR(255) DEFAULT NULL COMMENT '密码',
    `is_admin`       BIT(1)       DEFAULT b'0' COMMENT '是否为admin账号',
    `enabled`        BIGINT(20)   DEFAULT NULL COMMENT '状态：1启用、0禁用',
    `create_by`      VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`      VARCHAR(255) DEFAULT NULL COMMENT '更新着',
    `pwd_reset_time` DATETIME     DEFAULT NULL COMMENT '修改密码的时间',
    `create_time`    DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uniq_email`(`email`) USING BTREE,
    UNIQUE KEY `uniq_username`(`username`) USING BTREE,
    KEY `key_dept_id`(`dept_id`) USING BTREE,
    KEY `key_avatar`(`avatar`) USING BTREE,
    KEY `key_enabled`(`enabled`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 3
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user`
VALUES (1, 2, 1, 'admin', '管理员', '男', '18888888888', '201507802@qq.com',
        'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png',
        '$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa', b'1', 1, NULL, 'admin', '2020-05-03 16:38:31',
        '2018-08-23 09:11:56', '2020-09-05 10:43:31');
INSERT INTO `sys_user`
VALUES (2, 2, 2, 'test', '测试', '男', '19999999999', '231@qq.com',
        'http://dunwu.test.upcdn.net/common/logo/dunwu-logo.png',
        '$2a$10$4XcyudOYTSz6fue6KFNMHeUQnCX5jbBQypLEnGk1PmekXt5c95JcK', b'0', 1, 'admin', 'admin', NULL,
        '2020-05-05 11:15:49', '2020-09-05 10:43:38');
COMMIT;

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
DROP TABLE IF EXISTS `sys_users_roles`;
CREATE TABLE `sys_users_roles` (
    `id`      BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT(20)          NOT NULL COMMENT '用户ID',
    `role_id` BIGINT(20)          NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_sys_user_role`(`user_id`, `role_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='用户角色关联';

-- ----------------------------
-- Records of sys_users_roles
-- ----------------------------
BEGIN;
INSERT INTO `sys_users_roles`(`user_id`, `role_id`)
VALUES (1, 1);
INSERT INTO `sys_users_roles`(`user_id`, `role_id`)
VALUES (2, 2);
COMMIT;

-- ----------------------------
-- Table structure for sys_quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_job`;
CREATE TABLE `sys_quartz_job` (
    `job_id`              BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bean_name`           VARCHAR(255) DEFAULT NULL COMMENT 'Spring Bean名称',
    `cron_expression`     VARCHAR(255) DEFAULT NULL COMMENT 'cron 表达式',
    `is_pause`            BIT(1)       DEFAULT NULL COMMENT '状态：1暂停、0启用',
    `job_name`            VARCHAR(255) DEFAULT NULL COMMENT '任务名称',
    `method_name`         VARCHAR(255) DEFAULT NULL COMMENT '方法名称',
    `params`              VARCHAR(255) DEFAULT NULL COMMENT '参数',
    `description`         VARCHAR(255) DEFAULT NULL COMMENT '备注',
    `person_in_charge`    VARCHAR(100) DEFAULT NULL COMMENT '负责人',
    `email`               VARCHAR(100) DEFAULT NULL COMMENT '报警邮箱',
    `sub_task`            VARCHAR(100) DEFAULT NULL COMMENT '子任务ID',
    `pause_after_failure` BIT(1)       DEFAULT NULL COMMENT '任务失败后是否暂停',
    `create_by`           VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`           VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time`         DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`job_id`) USING BTREE,
    KEY `key_is_pause`(`is_pause`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 7
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='定时任务';

-- ----------------------------
-- Records of sys_quartz_job
-- ----------------------------
-- @formatter:off
BEGIN;
INSERT INTO `sys_quartz_job` VALUES (2, 'testTask', '0/5 * * * * ?', b'1', '测试1', 'run1', 'test', '带参测试，多参使用json', '测试', NULL, NULL, NULL, NULL, 'admin', '2019-08-22 14:08:29', '2020-05-24 13:58:33');
INSERT INTO `sys_quartz_job` VALUES (3, 'testTask', '0/5 * * * * ?', b'1', '测试', 'run', '', '不带参测试', 'Zheng Jie', '', '5,6', b'1', NULL, 'admin', '2019-09-26 16:44:39', '2020-05-24 14:48:12');
INSERT INTO `sys_quartz_job` VALUES (5, 'Test', '0/5 * * * * ?', b'1', '任务告警测试', 'run', NULL, '测试', 'test', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:32:41', '2020-05-05 20:36:13');
INSERT INTO `sys_quartz_job` VALUES (6, 'testTask', '0/5 * * * * ?', b'1', '测试3', 'run2', NULL, '测试3', 'Zheng Jie', '', NULL, b'1', 'admin', 'admin', '2020-05-05 20:35:41', '2020-05-05 20:36:07');
COMMIT;
-- @formatter:on

-- ----------------------------
-- Table structure for sys_quartz_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartz_log`;
CREATE TABLE `sys_quartz_log` (
    `log_id`           BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bean_name`        VARCHAR(255) DEFAULT NULL,
    `create_time`      DATETIME     DEFAULT NULL,
    `cron_expression`  VARCHAR(255) DEFAULT NULL,
    `exception_detail` TEXT         DEFAULT NULL,
    `is_success`       BIT(1)       DEFAULT NULL,
    `job_name`         VARCHAR(255) DEFAULT NULL,
    `method_name`      VARCHAR(255) DEFAULT NULL,
    `params`           VARCHAR(255) DEFAULT NULL,
    `time`             BIGINT(20)   DEFAULT NULL,
    PRIMARY KEY (`log_id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 151
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='定时任务日志';


-- ----------------------------
-- Table structure for tool_alipay_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_alipay_config`;
CREATE TABLE `tool_alipay_config` (
    `config_id`               BIGINT(20) NOT NULL COMMENT 'ID',
    `app_id`                  VARCHAR(255) DEFAULT NULL COMMENT '应用ID',
    `charset`                 VARCHAR(255) DEFAULT NULL COMMENT '编码',
    `format`                  VARCHAR(255) DEFAULT NULL COMMENT '类型 固定格式json',
    `gateway_url`             VARCHAR(255) DEFAULT NULL COMMENT '网关地址',
    `notify_url`              VARCHAR(255) DEFAULT NULL COMMENT '异步回调',
    `private_key`             TEXT         DEFAULT NULL COMMENT '私钥',
    `public_key`              TEXT         DEFAULT NULL COMMENT '公钥',
    `return_url`              VARCHAR(255) DEFAULT NULL COMMENT '回调地址',
    `sign_type`               VARCHAR(255) DEFAULT NULL COMMENT '签名方式',
    `sys_service_provider_id` VARCHAR(255) DEFAULT NULL COMMENT '商户号',
    PRIMARY KEY (`config_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='支付宝配置类';

-- ----------------------------
-- Records of tool_alipay_config
-- ----------------------------
BEGIN;
INSERT INTO `tool_alipay_config`
VALUES (1, '2016091700532697', 'utf-8', 'JSON', 'https://openapi.alipaydev.com/gateway.do',
        'http://api.auauz.net/api/aliPay/notify',
        'MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQC5js8sInU10AJ0cAQ8UMMyXrQ+oHZEkVt5lBwsStmTJ7YikVYgbskx1YYEXTojRsWCb+SH/kDmDU4pK/u91SJ4KFCRMF2411piYuXU/jF96zKrADznYh/zAraqT6hvAIVtQAlMHN53nx16rLzZ/8jDEkaSwT7+HvHiS+7sxSojnu/3oV7BtgISoUNstmSe8WpWHOaWv19xyS+Mce9MY4BfseFhzTICUymUQdd/8hXA28/H6osUfAgsnxAKv7Wil3aJSgaJczWuflYOve0dJ3InZkhw5Cvr0atwpk8YKBQjy5CdkoHqvkOcIB+cYHXJKzOE5tqU7inSwVbHzOLQ3XbnAgMBAAECggEAVJp5eT0Ixg1eYSqFs9568WdetUNCSUchNxDBu6wxAbhUgfRUGZuJnnAll63OCTGGck+EGkFh48JjRcBpGoeoHLL88QXlZZbC/iLrea6gcDIhuvfzzOffe1RcZtDFEj9hlotg8dQj1tS0gy9pN9g4+EBH7zeu+fyv+qb2e/v1l6FkISXUjpkD7RLQr3ykjiiEw9BpeKb7j5s7Kdx1NNIzhkcQKNqlk8JrTGDNInbDM6inZfwwIO2R1DHinwdfKWkvOTODTYa2MoAvVMFT9Bec9FbLpoWp7ogv1JMV9svgrcF9XLzANZ/OQvkbe9TV9GWYvIbxN6qwQioKCWO4GPnCAQKBgQDgW5MgfhX8yjXqoaUy/d1VjI8dHeIyw8d+OBAYwaxRSlCfyQ+tieWcR2HdTzPca0T0GkWcKZm0ei5xRURgxt4DUDLXNh26HG0qObbtLJdu/AuBUuCqgOiLqJ2f1uIbrz6OZUHns+bT/jGW2Ws8+C13zTCZkZt9CaQsrp3QOGDx5wKBgQDTul39hp3ZPwGNFeZdkGoUoViOSd5Lhowd5wYMGAEXWRLlU8z+smT5v0POz9JnIbCRchIY2FAPKRdVTICzmPk2EPJFxYTcwaNbVqL6lN7J2IlXXMiit5QbiLauo55w7plwV6LQmKm9KV7JsZs5XwqF7CEovI7GevFzyD3w+uizAQKBgC3LY1eRhOlpWOIAhpjG6qOoohmeXOphvdmMlfSHq6WYFqbWwmV4rS5d/6LNpNdL6fItXqIGd8I34jzql49taCmi+A2nlR/E559j0mvM20gjGDIYeZUz5MOE8k+K6/IcrhcgofgqZ2ZED1ksHdB/E8DNWCswZl16V1FrfvjeWSNnAoGAMrBplCrIW5xz+J0Hm9rZKrs+AkK5D4fUv8vxbK/KgxZ2KaUYbNm0xv39c+PZUYuFRCz1HDGdaSPDTE6WeWjkMQd5mS6ikl9hhpqFRkyh0d0fdGToO9yLftQKOGE/q3XUEktI1XvXF0xyPwNgUCnq0QkpHyGVZPtGFxwXiDvpvgECgYA5PoB+nY8iDiRaJNko9w0hL4AeKogwf+4TbCw+KWVEn6jhuJa4LFTdSqp89PktQaoVpwv92el/AhYjWOl/jVCm122f9b7GyoelbjMNolToDwe5pF5RnSpEuDdLy9MfE8LnE3PlbE7E5BipQ3UjSebkgNboLHH/lNZA5qvEtvbfvQ==',
        'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAut9evKRuHJ/2QNfDlLwvN/S8l9hRAgPbb0u61bm4AtzaTGsLeMtScetxTWJnVvAVpMS9luhEJjt+Sbk5TNLArsgzzwARgaTKOLMT1TvWAK5EbHyI+eSrc3s7Awe1VYGwcubRFWDm16eQLv0k7iqiw+4mweHSz/wWyvBJVgwLoQ02btVtAQErCfSJCOmt0Q/oJQjj08YNRV4EKzB19+f5A+HQVAKy72dSybTzAK+3FPtTtNen/+b5wGeat7c32dhYHnGorPkPeXLtsqqUTp1su5fMfd4lElNdZaoCI7osZxWWUo17vBCZnyeXc9fk0qwD9mK6yRAxNbrY72Xx5VqIqwIDAQAB',
        'http://api.auauz.net/api/aliPay/return', 'RSA2', '2088102176044281');
COMMIT;

-- ----------------------------
-- Table structure for tool_email_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_email_config`;
CREATE TABLE `tool_email_config` (
    `config_id` BIGINT(20) NOT NULL COMMENT 'ID',
    `from_user` VARCHAR(255) DEFAULT NULL COMMENT '收件人',
    `host`      VARCHAR(255) DEFAULT NULL COMMENT '邮件服务器SMTP地址',
    `pass`      VARCHAR(255) DEFAULT NULL COMMENT '密码',
    `port`      VARCHAR(255) DEFAULT NULL COMMENT '端口',
    `user`      VARCHAR(255) DEFAULT NULL COMMENT '发件者用户名',
    PRIMARY KEY (`config_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='邮箱配置';

-- ----------------------------
-- Table structure for tool_local_storage
-- ----------------------------
DROP TABLE IF EXISTS `tool_local_storage`;
CREATE TABLE `tool_local_storage` (
    `storage_id`  BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `real_name`   VARCHAR(255) DEFAULT NULL COMMENT '文件真实的名称',
    `name`        VARCHAR(255) DEFAULT NULL COMMENT '文件名',
    `suffix`      VARCHAR(255) DEFAULT NULL COMMENT '后缀',
    `path`        VARCHAR(255) DEFAULT NULL COMMENT '路径',
    `type`        VARCHAR(255) DEFAULT NULL COMMENT '类型',
    `size`        VARCHAR(100) DEFAULT NULL COMMENT '大小',
    `create_by`   VARCHAR(255) DEFAULT NULL COMMENT '创建者',
    `update_by`   VARCHAR(255) DEFAULT NULL COMMENT '更新者',
    `create_time` DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`storage_id`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 10
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='本地存储';

-- ----------------------------
-- Table structure for tool_qiniu_config
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_config`;
CREATE TABLE `tool_qiniu_config` (
    `config_id`  BIGINT(20)   NOT NULL COMMENT 'ID',
    `access_key` TEXT         DEFAULT NULL COMMENT 'accessKey',
    `bucket`     VARCHAR(255) DEFAULT NULL COMMENT 'Bucket 识别符',
    `host`       VARCHAR(255) NOT NULL COMMENT '外链域名',
    `secret_key` TEXT         DEFAULT NULL COMMENT 'secretKey',
    `type`       VARCHAR(255) DEFAULT NULL COMMENT '空间类型',
    `zone`       VARCHAR(255) DEFAULT NULL COMMENT '机房',
    PRIMARY KEY (`config_id`) USING BTREE
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='七牛云配置';

-- ----------------------------
-- Table structure for tool_qiniu_content
-- ----------------------------
DROP TABLE IF EXISTS `tool_qiniu_content`;
CREATE TABLE `tool_qiniu_content` (
    `content_id`  BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `bucket`      VARCHAR(255) DEFAULT NULL COMMENT 'Bucket 识别符',
    `name`        VARCHAR(255) DEFAULT NULL COMMENT '文件名称',
    `size`        VARCHAR(255) DEFAULT NULL COMMENT '文件大小',
    `type`        VARCHAR(255) DEFAULT NULL COMMENT '文件类型：私有或公开',
    `url`         VARCHAR(255) DEFAULT NULL COMMENT '文件url',
    `suffix`      VARCHAR(255) DEFAULT NULL COMMENT '文件后缀',
    `update_time` DATETIME     DEFAULT NULL COMMENT '上传或同步的时间',
    PRIMARY KEY (`content_id`) USING BTREE,
    UNIQUE KEY `uniq_name`(`name`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='七牛云文件存储';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- 用于测试的样例表
-- ----------------------------
CREATE TABLE `file_record` (
    `id`           BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `filename`     VARCHAR(255) NOT NULL COMMENT '文件名',
    `namespace`    VARCHAR(255)        DEFAULT '' COMMENT '命名空间',
    `tag`          VARCHAR(255)        DEFAULT '' COMMENT '标签',
    `origin_name`  VARCHAR(255) NOT NULL COMMENT '源文件名',
    `size`         BIGINT(20) UNSIGNED DEFAULT 0 COMMENT '文件大小',
    `extension`    VARCHAR(255)        DEFAULT '' COMMENT '文件扩展名',
    `content_type` VARCHAR(255)        DEFAULT '' COMMENT '文件实际类型',
    `store_type`   VARCHAR(255) NOT NULL COMMENT '文件存储类型',
    `url`          VARCHAR(255) NOT NULL COMMENT '文件存储路径',
    `create_by`    VARCHAR(255)        DEFAULT NULL COMMENT '创建者',
    `update_by`    VARCHAR(255)        DEFAULT NULL COMMENT '更新者',
    `create_time`  DATETIME            DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  DATETIME            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_filename`(`filename`),
    UNIQUE KEY `uniq_keys`(`origin_name`, `tag`, `namespace`),
    KEY `key_code`(`url`)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT
    COMMENT ='文件记录';


DROP TABLE IF EXISTS `hello`;
CREATE TABLE `hello` (
    `id`          BIGINT(20)   NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `name`        VARCHAR(255) NOT NULL COMMENT '名字',
    `age`         SMALLINT(3)  NOT NULL COMMENT '年龄',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `uk_name`(`name`) USING BTREE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 4
    DEFAULT CHARSET = `utf8`
    ROW_FORMAT = COMPACT COMMENT ='测试';

