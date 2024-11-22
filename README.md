# popsicle

基于 DDD(领域驱动设计)的 Spring Boot 项目脚手架。

## 项目介绍

本项目采用 DDD 分层架构（六边形架构），旨在提供一个清晰、可扩展的项目基础框架，适用于中大型项目开发。

1. **接口定义 - popsicle-api**: 提供微服务中 RPC 的接口描述信息，调用方需要引入该 Jar 包以依赖接口定义。

2. **应用封装 - popsicle-app**: 负责应用启动和配置，包括 AOP 切面、配置管理和镜像打包，专为启动服务而设计。

3. **领域封装 - popsicle-domain**: 领域模型服务模块，包含细分的领域服务，每个服务包中包括模型、仓库和服务。

4. **仓储服务 - popsicle-infrastructure**: 基础层实现领域层定义的仓储接口，体现依赖倒置设计原则。

5. **领域封装 - popsicle-trigger**: 触发器层，提供接口实现、消息接收和任务执行功能。

6. **类型定义 - popsicle-types**: 定义通用类型，包括响应、常量和枚举，供其他层引用使用。

7. **领域编排【可选】 - popsicle-case**: 用于复杂项目的领域编排，封装和组合领域逻辑以提供通用服务。

> [!TIP]
> 领域编排层，本项目暂未使用，由于项目体量暂时校小，对于需要进行领域编排放在触发层实现。

## 功能特性

集成了完善的权限管理功能，包括用户认证、角色授权、资源权限等核心特性。

## 技术栈

- Spring Boot 2.7.12
- MyBatis-Plus 3.5.7
- MySQL 8.0.22
- JJWT 0.9.1
- Spring Security 5.7.8

## 项目结构

```bash
├── docs                                          # 项目文档目录
│   └── dev-ops                                   # 运维相关配置
│       ├── app                                   # 应用部署配置
│       │   ├── start.sh                          # 启动脚本
│       │   └── stop.sh                           # 停止脚本
│       ├── docker-compose-app.yml                # 应用服务编排配置
│       ├── docker-compose-environment-aliyun.yml # 阿里云环境编排配置
│       ├── docker-compose-environment.yml        # 本地环境编排配置
│       └── mysql                                 # MySQL 相关配置
│           └── sql                               # SQL 脚本录
│               ├── popsicle.sql                  # 数据库初始化脚本
│── http                                          # HTTP 接口测试目录
│   ├── http-client.env.json                      # 环境变量配置
│   └── popsicle.http                             # 接口测试用例
├── pom.xml                                       # 项目依赖管理文件
├── popsicle-api                                  # API 模块（RPC 接口定义，最终提供对外 jar ）
│   └── src/main/java/run/threedog/api
│       ├── dto                                   # 数据传输对象
│       ├── response                              # 统一响应对象
├── popsicle-app                                  # 应用启动模块（启动层）
│   ├── src/main/java/run/threedog
│   │   ├── Application.java                      # 应用启动类
│   │   └── config                                # 配置类目录
│   │       ├── GuavaConfig.java                  # Guava 配置
│   │       ├── IgnoreUrlsConfig.java             # 安全白名单配置
│   │       ├── JsonConfig.java                   # JSON 序列化配置
│   │       ├── SecurityConfig.java               # 全配置
│   │       └── ThreadPoolConfig.java             # 线程池配置
│   └── src/main/resources                        # 资源文件目录
│       ├── application-*.yml                     # 各环境配置文件
│       ├── logback-spring.xml                    # 日志配置
│       └── mybatis                               # MyBatis 配置目录
├── popsicle-domain                               # 领域模块（领域层）
│   └── src/main/java/run/threedog/domain
│       ├── xxx                                   # xxx 领域
│       │   ├── adapter                           # 适配器接口
│       │   │   ├── port                          # 端口适配器（调用外部接口）
│       │   │   ├── event                         # 领域事件
│       │   │   └── repository                    # 仓储适配器
│       │   ├── model                             # 领域模型
│       │   │   ├── aggregate                     # 聚合
│       │   │   ├── entity                        # 实体
│       │   │   └── valobj                        # 值对象
│       │   └── service                           # 领域服务
│       └── yyy                                   # 扩展领域示例
├── popsicle-infrastructure                       # 基础设施模块（基础设置层）
│   └── src/main/java/run/threedog/infrastructure
│       ├── adapter                               # 适配器实现
│       │   ├── port                              # 端口适配器实现
│       │   └── repository                        # 仓储适配器实现
│       ├── dao                                   # 数据访问对象
│       │   ├── po                                # 持久化对象
│       │   └─ converter                          # 对象转换器
│       ├── gateway                               # 外部服务网关
│       └── redis                                 # Redis 配置实现
├── popsicle-trigger                              # 触发器模块（接口层）
│       ├── http                                  # HTTP 接口控制器
│       │   ├── assembler                         # 对象装配器
│       │   ├── dto                               # 接口传输对象
│       │   └── xxxController                     # API 中接口的实现
│       ├── job                                   # 定时任务
│       └── listener                              # 事件监听器
└── popsicle-types                                # 通用类型模块（类型层）
    └── src/main/java/run/threedog/types
        ├── annotation                            # 自定义注解
        ├── assembler                             # 对象映射接口
        ├── common                                # 通用常量定义
        ├── enums                                 # 枚举类定义
        ├── event                                 # 事件基类定义
        ├── exception                             # 异常类定义
        ├── handler                               # 全局处理器
        ├── model                                 # 通用模型对象（如：统一响应、分页）
        ├── util                                  # 工具类
        └── validator                             # 校验器
```

## 分层调用链路

![](./docs/assert/image/road-map-ddd-05.png)

## 数据库表设计

RBAC 权限管理相关库表设计详细设计：

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`
(
    `menu_id`        bigint       NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `menu_name`      varchar(100) NOT NULL COMMENT '菜单名称',
    `parent_id`      bigint       DEFAULT NULL COMMENT '父级菜单ID',
    `menu_type`      int          DEFAULT NULL COMMENT '菜单类型：1目录，2菜单，3按钮',
    `icon`           varchar(100) DEFAULT NULL COMMENT '图标',
    `sort`           int          DEFAULT NULL COMMENT '菜单排序',
    `route_name`     varchar(30)  DEFAULT NULL COMMENT '路由名称',
    `route_path`     varchar(64)  DEFAULT NULL COMMENT '路由地址',
    `component_path` varchar(64)  DEFAULT NULL COMMENT '组件地址',
    `permission`     varchar(100) DEFAULT NULL COMMENT '权限标识',
    `remark`         varchar(200) DEFAULT NULL COMMENT '备注',
    `frame_flag`     tinyint      DEFAULT NULL COMMENT '外链状态：0否，1是',
    `frame_url`      varchar(100) DEFAULT NULL COMMENT '外链地址',
    `disabled_flag`  tinyint      DEFAULT NULL COMMENT '禁用状态：0正常，1禁用',
    `deleted_flag`   tinyint      DEFAULT '0' COMMENT '删除状态：0否，1是',
    `create_time`    datetime     DEFAULT (now()) COMMENT '创建时间',
    `create_by`      varchar(30)  DEFAULT NULL COMMENT '创建人',
    `update_time`    datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`      varchar(30)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 26
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='菜单表';

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`
(
    `role_id`     bigint       NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_name`   varchar(100) NOT NULL COMMENT '名称',
    `role_code`   varchar(100) DEFAULT NULL COMMENT '角色编码',
    `role_desc`   varchar(100) DEFAULT NULL COMMENT '描述',
    `sort`        int          DEFAULT '0' COMMENT '排序',
    `create_time` datetime     DEFAULT (now()) COMMENT '创建时间',
    `create_by`   varchar(30)  DEFAULT NULL COMMENT '创建人',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`   varchar(30)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 6
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色表';

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '角色菜单ID',
    `role_id`     bigint   DEFAULT NULL COMMENT '角色ID',
    `menu_id`     bigint   DEFAULT NULL COMMENT '菜单ID',
    `create_time` datetime DEFAULT (now()) COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='角色菜单表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    `user_id`       bigint      NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`      varchar(30) NOT NULL COMMENT '用户名',
    `password`      varchar(30) NOT NULL COMMENT '密码',
    `real_name`     varchar(30)  DEFAULT NULL COMMENT '昵称',
    `gender`        int          DEFAULT '1' COMMENT '性别：1男，2女，3未知',
    `avatar`        varchar(100) DEFAULT NULL COMMENT '头像',
    `email`         varchar(50)  DEFAULT NULL COMMENT '邮箱',
    `phone_number`  varchar(11)  DEFAULT NULL COMMENT '电话号码',
    `disabled_flag` tinyint COMMENT '禁用状态：0正常，1禁用',
    `deleted_flag`  tinyint COMMENT '删除状态：0否，1是',
    `remark`        varchar(200) DEFAULT NULL COMMENT '备注',
    `create_time`   datetime     DEFAULT (now()) COMMENT '创建时间',
    `create_by`     varchar(30)  DEFAULT NULL COMMENT '创建人',
    `update_time`   datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `update_by`     varchar(30)  DEFAULT NULL COMMENT '更新人',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 19
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='后台用户表';

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '用户角色ID',
    `user_id`     bigint   DEFAULT NULL COMMENT '用户ID',
    `role_id`     bigint   DEFAULT NULL COMMENT '角色ID',
    `create_time` datetime DEFAULT (now()) COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 32
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = DYNAMIC COMMENT ='用户角色表';

SET FOREIGN_KEY_CHECKS = 1;
```

## 领域设计

### 1. 用户管理领域（User Domain）

#### 聚合根：用户（UserAggregate）

- 实体
    - UserAggregate：用户聚合根
      ```java
      public class UserAggregate {
          private Long userId;        // 用户ID
          private String username;    // 用户名
          private String realName;    // 真实姓名
          private GenderVO gender;    // 性别枚举
          private String avatar;      // 头像URL
          private String email;       // 邮箱
          private String phoneNumber; // 手机号
          private UserStatusVO status;  // 用户状态
          private Set<UserRoleVO> userRoles; // 用户角色关联(值对象)
          
          // 用户状态变更
          public void disable() {
              this.status = UserStatusVO.DISABLED;
              DomainEventPublisher.publish(new UserStatusChangedEvent(this.userId, UserStatusVO.DISABLED));
          }
          
          public void enable() {
              this.status = UserStatusVO.ENABLED;
              DomainEventPublisher.publish(new UserStatusChangedEvent(this.userId, UserStatusVO.ENABLED));
          }
          
          // 角色管理
          public void assignRoles(Set<UserRoleVO> roles) {
              this.userRoles = new HashSet<>(roles);
              DomainEventPublisher.publish(new UserRolesChangedEvent(this.userId, 
                  roles.stream().map(UserRoleVO::getRoleId).collect(Collectors.toSet())));
          }
          
          public void addRole(UserRoleVO role) {
              if (userRoles == null) {
                  userRoles = new HashSet<>();
              }
              userRoles.add(role);
              DomainEventPublisher.publish(new UserRoleAddedEvent(this.userId, role.getRoleId()));
          }
          
          public void removeRole(Long roleId) {
              if (userRoles != null) {
                  userRoles.removeIf(role -> role.getRoleId().equals(roleId));
                  DomainEventPublisher.publish(new UserRoleRemovedEvent(this.userId, roleId));
              }
          }
      }
      ```

- 值对象
    - UserStatusVO：用户状态值对象
      ```java
      public enum UserStatusVO {
          ENABLED("正常"),
          DISABLED("禁用"),
          DELETED("已删除");
          
          private final String description;
          
          UserStatusVO(String description) {
              this.description = description;
          }
          
          public String getDescription() {
              return description;
          }
          
          public boolean isAvailable() {
              return this == ENABLED;
          }
          
          public boolean isDisabled() {
              return this == DISABLED;
          }
          
          public boolean isDeleted() {
              return this == DELETED;
          }
      }
      ```

    - UserRoleVO：用户角色关联值对象
      ```java
      public class UserRoleVO {
          private final Long roleId;
          private final String roleName;  // 冗余字段，提高查询效率
          private final String roleCode;  // 冗余字段，便于权限判断
          private final LocalDateTime grantTime;  // 授权时间
          
          public UserRoleVO(Long roleId, String roleName, String roleCode) {
              this.roleId = roleId;
              this.roleName = roleName;
              this.roleCode = roleCode;
              this.grantTime = LocalDateTime.now();
          }
          
          // 值对象相等性比较
          @Override
          public boolean equals(Object o) {
              if (this == o) return true;
              if (!(o instanceof UserRoleVO)) return false;
              UserRoleVO that = (UserRoleVO) o;
              return Objects.equals(roleId, that.roleId);
          }
          
          @Override
          public int hashCode() {
              return Objects.hash(roleId);
          }
      }
      ```

#### 领域服务

- UserDomainService：用户领域服务
  ```java
  public interface UserDomainService {
      // 创建用户
      UserAggregate createUser(UserCreationCommand command);
      
      // 更新用户信息
      void updateUser(UserUpdateCommand command);
      
      // 修改密码
      void changePassword(Long userId, PasswordChangeCommand command);
      
      // 禁用用户
      void disableUser(Long userId);
      
      // 启用用户
      void enableUser(Long userId);
      
      // 删除用户
      void deleteUser(Long userId);
  }
  ```

- UserRoleDomainService：用户角色领域服务
  ```java
  @Service
  public class UserRoleDomainService {
      @Autowired
      private UserRepository userRepository;
      @Autowired
      private RoleRepository roleRepository;
      
      @Transactional
      public void assignUserRoles(Long userId, List<Long> roleIds) {
          // 1. 获取用户聚合根
          UserAggregate user = userRepository.findById(userId)
              .orElseThrow(() -> new EntityNotFoundException("User not found"));
              
          // 2. 获取角色信息并构建值对象
          Set<UserRoleVO> userRoles = roleIds.stream()
              .map(roleId -> {
                  RoleAggregate role = roleRepository.findById(roleId)
                      .orElseThrow(() -> new EntityNotFoundException("Role not found"));
                  return new UserRoleVO(
                      role.getRoleId(),
                      role.getRoleName(),
                      role.getRoleCode().getCode()
                  );
              })
              .collect(Collectors.toSet());
              
          // 3. 更新用户角色
          user.assignRoles(userRoles);
          
          // 4. 保存用户
          userRepository.save(user);
      }
  }
  ```

#### 仓储接口

```java
public interface UserRepository {
    
    Optional<UserAggregate> findById(Long userId);
    
    Optional<UserAggregate> findByUsername(String username);
    
    UserAggregate save(UserAggregate user);
    
    void delete(Long userId);
    
    // 状态查询
    List<UserAggregate> findByStatus(UserStatusVO status);
    
    // 角色相关查询
    List<UserAggregate> findByRoleId(Long roleId);
    
    Set<UserRoleVO> findUserRoles(Long userId);
    
    // 批量操作
    List<UserAggregate> findByIds(List<Long> userIds);
    
    void batchUpdateStatus(List<Long> userIds, UserStatusVO status);
}
```

#### 领域事件

```java
// 用户角色变更事件
public class UserRolesChangedEvent extends BaseEvent<Set<Long>> {
    
    private final Long userId;
    
    public UserRolesChangedEvent(Long userId, Set<Long> roleIds) {
        super(roleIds);
        this.userId = userId;
    }
    
    @Override
    public String topic() {
        return "user.roles.changed";
    }
}

// 用户状态变更事件
public class UserStatusChangedEvent extends BaseEvent<UserStatusVO> {
    
    private final Long userId;
    
    @Override
    public String topic() {
        return "user.status.changed";
    }
}
```

### 2. 认证授权领域（Auth Domain）

#### 聚合根：认证上下文（AuthContextAggregate）

- 实体
    - AuthContextAggregate：认证上下文聚合根
      ```java
      public class AuthContextAggregate {
          private Long userId;
          private String username;
          private String password;    // 密码(加密存储)
          private AuthStatusVO status;
          private Integer loginFailCount;  // 登录失败次数
          private String lastLoginIp;
          private LocalDateTime lastLoginTime;
          
          // 认证相关
          public AuthResultVO authenticate(LoginCredentialVO credential, PasswordEncoder encoder) {
              if (isLocked()) {
                  throw new AccountLockedException("Account is locked");
              }
              
              if (!encoder.matches(credential.getPassword(), this.password)) {
                  recordLoginFail();
                  throw new BadCredentialsException("Invalid password");
              }
              
              recordLoginSuccess(credential.getLoginIp());
              return new AuthResultVO(this.userId, this.username);
          }
          
          // 密码管理
          public void changePassword(String newPassword, String oldPassword, PasswordEncoder encoder) {
              if (!encoder.matches(oldPassword, this.password)) {
                  throw new BadCredentialsException("Invalid old password");
              }
              this.password = encoder.encode(newPassword);
              DomainEventPublisher.publish(new PasswordChangedEvent(this.userId));
          }
          
          // 状态管理
          private boolean isLocked() {
              return loginFailCount >= 5;
          }
          
          private void recordLoginFail() {
              this.loginFailCount++;
              if (isLocked()) {
                  this.status = AuthStatusVO.LOCKED;
                  DomainEventPublisher.publish(new AccountLockedEvent(this.userId));
              }
          }
          
          private void recordLoginSuccess(String loginIp) {
              this.loginFailCount = 0;
              this.status = AuthStatusVO.ACTIVE;
              this.lastLoginIp = loginIp;
              this.lastLoginTime = LocalDateTime.now();
              DomainEventPublisher.publish(new LoginSuccessEvent(this.userId));
          }
      }
      ```

- 值对象
    - AuthStatusVO：认证状态
      ```java
      public enum AuthStatusVO {
          ACTIVE("活跃"),    
          EXPIRED("过期"),   
          LOCKED("锁定"),    
          LOGOUT("登出");
          
          private final String description;
          
          AuthStatusVO(String description) {
              this.description = description;
          }
          
          public String getDescription() {
              return description;
          }
      }
      ```
    - LoginCredentialVO：登录凭证
      ```java
      public class LoginCredentialVO {
          private final String username;
          private final String password;
          private final String deviceId;
          private final String loginIp;
          private final String userAgent;
          
          public LoginCredentialVO(String username, String password, String deviceId, 
                                 String loginIp, String userAgent) {
              this.username = username;
              this.password = password;
              this.deviceId = deviceId;
              this.loginIp = loginIp;
              this.userAgent = userAgent;
          }
      }
      ```

#### 领域服务

- AuthenticationDomainService：认证领域服务
  ```java
  public interface AuthenticationDomainService {
      // 用户认证
      AuthResultVO authenticate(LoginCredentialVO credential);
      
      // 修改密码
      void changePassword(Long userId, PasswordChangeCommand command);
      
      // 重置密码
      void resetPassword(Long userId);
  }
  ```

- AuthorizationDomainService：授权领域服务
  ```java
  public interface AuthorizationDomainService {
      // 验证用户是否有指定权限
      boolean hasPermission(Long userId, String permission);
      
      // 获取用户所有权限
      Set<String> getUserPermissions(Long userId);
      
      // 验证用户是否有指定角色
      boolean hasRole(Long userId, String roleCode);
  }
  ```

#### 领域事件

```java
// 登录成功事件
public class LoginSuccessEvent extends BaseEvent<AuthenticationEntity> {
    
    @Override
    public String topic() {
        return "auth.login.success";
    }
}

// 登录失败事件
public class LoginFailedEvent extends BaseEvent<LoginCredentialVO> {
    
    private final String failReason;
    
    @Override
    public String topic() {
        return "auth.login.failed";
    }
}

// 账号锁定事件
public class AccountLockedEvent extends BaseEvent<Long> {
    
    private final String lockReason;
    
    @Override
    public String topic() {
        return "auth.account.locked";
    }
}
```

### 3. 权限管理领域（Permission Domain）

#### 聚合根：角色（RoleAggregate）

- 实体
    - RoleAggregate：角色聚合根
      ```java
      public class RoleAggregate {
          private Long roleId;      // 角色ID
          private String roleName;  // 角色名称
          private RoleCodeVO roleCode;  // 角色编码(值对象)
          private String roleDesc;  // 角色描述
          private Integer sort;     // 排序号
          private Set<RoleMenuVO> roleMenus;  // 角色菜单关联(值对象)
          
          @Override
          public Long getAggregateId() {
              return roleId;
          }
          
          // 分配菜单权限
          public void assignMenu(RoleMenuVO roleMenu) {
              if (roleMenus == null) {
                  roleMenus = new HashSet<>();
              }
              roleMenus.add(roleMenu);
              // 发布权限变更事件
              DomainEventPublisher.publish(new RolePermissionChangedEvent(this.roleId, roleMenu.getMenuId()));
          }
          
          // 批量分配菜单
          public void assignMenus(Set<RoleMenuVO> newRoleMenus) {
              this.roleMenus = new HashSet<>(newRoleMenus);
              // 发布批量权限变更事件
              DomainEventPublisher.publish(new RoleBatchPermissionChangedEvent(this.roleId, 
                  newRoleMenus.stream().map(RoleMenuVO::getMenuId).collect(Collectors.toSet())));
          }
      }
      ```

- 值对象
    - RoleCodeVO：角色编码值对象
      ```java
      public class RoleCodeVO {
          private final String code;
          
          public RoleCodeVO(String code) {
              validateRoleCode(code);
              this.code = code;
          }
          
          private void validateRoleCode(String code) {
              // 角色编码验证逻辑
          }
      }
      ```

    - RoleMenuVO：角色菜单关联值对象
      ```java
      public class RoleMenuVO {
          private final Long menuId;
          private final String menuName;  // 冗余字段，提高查询效率
          private final String permission;  // 权限标识
          private final DataScopeVO dataScope;  // 数据权限范围
          private final LocalDateTime grantTime;  // 授权时间
          
          public RoleMenuVO(Long menuId, String menuName, String permission, DataScopeVO dataScope) {
              this.menuId = menuId;
              this.menuName = menuName;
              this.permission = permission;
              this.dataScope = dataScope;
              this.grantTime = LocalDateTime.now();
          }
          
          // 值对象相等性比较
          @Override
          public boolean equals(Object o) {
              if (this == o) return true;
              if (!(o instanceof RoleMenuVO)) return false;
              RoleMenuVO that = (RoleMenuVO) o;
              return Objects.equals(menuId, that.menuId) &&
                     Objects.equals(dataScope, that.dataScope);
          }
          
          @Override
          public int hashCode() {
              return Objects.hash(menuId, dataScope);
          }
      }
      ```

#### 领域服务

- RoleService：角色管理服务
  ```java
  public interface RoleService {
      // 创建角色
      RoleAggregate createRole(RoleAggregate role);
      
      // 更新角色
      void updateRole(RoleAggregate role);
      
      // 删除角色
      void deleteRole(Long roleId);
      
      // 分配菜单权限
      void assignMenus(Long roleId, List<MenuAssignmentVO> assignments);
  }
  ```

- RolePermissionDomainService：角色权限领域服务实现
  ```java
  @Service
  public class RolePermissionDomainService {
      @Autowired
      private RoleRepository roleRepository;
      @Autowired
      private MenuRepository menuRepository;
      
      @Transactional
      public void assignMenus(Long roleId, List<MenuAssignmentVO> assignments) {
          // 1. 获取角色聚合根
          RoleAggregate role = roleRepository.findById(roleId)
              .orElseThrow(() -> new EntityNotFoundException("Role not found"));
              
          // 2. 构建角色菜单值对象
          Set<RoleMenuVO> roleMenus = assignments.stream()
              .map(assignment -> {
                  MenuEntity menu = menuRepository.findById(assignment.getMenuId())
                      .orElseThrow(() -> new EntityNotFoundException("Menu not found"));
                      
                  return new RoleMenuVO(
                      menu.getMenuId(),
                      menu.getMenuName(),
                      menu.getPermission(),
                      assignment.getDataScope()
                  );
              })
              .collect(Collectors.toSet());
              
          // 3. 更新角色的菜单权限
          role.assignMenus(roleMenus);
          
          // 4. 保存角色
          roleRepository.save(role);
      }
  }
  ```

### 4. 菜单资源领域（Menu Domain）

核心职责：负责系统菜单和资源管理。

#### 聚合根：菜单（MenuAggregate）

- 实体
    - MenuAggregate：菜单聚合根
      ```java
      public class MenuAggregate {
          private Long menuId;          // 菜单ID
          private String menuName;      // 菜单名称
          private Long parentId;        // 父级菜单ID
          private MenuTypeVO menuType;  // 菜单类型
          private String icon;          // 图标
          private Integer sort;         // 排序
          private RouteInfoVO routeInfo;// 路由信息
          private String permission;    // 权限标识
          private MenuStatusVO status;  // 菜单状态
          private FrameConfigVO frame;  // 外链配置
          
          @Override
          public Long getAggregateId() {
              return menuId;
          }
          
          public boolean isButton() {
              return MenuType.BUTTON.equals(this.menuType);
          }
          
          public boolean isMenu() {
              return MenuType.MENU.equals(this.menuType);
          }
          
          public boolean isDirectory() {
              return MenuType.DIRECTORY.equals(this.menuType);
          }
      }
      ```

- 值对象
    - MenuType：菜单类型
      ```java
      public enum MenuType {
          DIRECTORY(1, "目录"),
          MENU(2, "菜单"),
          BUTTON(3, "按钮");
      }
      ```
    - RouteInfo：路由信息
      ```java
      public class RouteInfo {
          private String routeName;     // 路由名称
          private String routePath;     // 路由路径
          private String componentPath; // 组件路径
          
          public boolean isValidRoute() {
              // 路由有效性验证
          }
      }
      ```

#### 领域服务

- MenuService：菜单管理服务
  ```java
  public interface MenuService {
      // 创建菜单
      MenuAggregate createMenu(MenuAggregate menu);
      
      // 更新菜单
      void updateMenu(MenuAggregate menu);
      
      // 删除菜单
      void deleteMenu(Long menuId);
      
      // 获取菜单树
      List<MenuAggregate> getMenuTree();
      
      // 获取用户菜单权限
      Set<String> getUserPermissions(Long userId);
  }
  ```

### 领域间的交互

#### 1. 用户认证流程

```java
public class AuthenticationFlow {
    
    @Autowired
    private UserAuthService userAuthService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private MenuService menuService;
    
    public LoginResult authenticate(Credential credential) {
        // 1. 用户认证
        LoginResult result = userAuthService.login(credential);
        
        // 2. 获取用户角色
        List<RoleAggregate> roles = roleService.getUserRoles(result.getUserId());
        
        // 3. 获取权限菜单
        Set<String> permissions = menuService.getUserPermissions(result.getUserId());
        
        // 4. 构建认证结果
        result.setRoles(roles);
        result.setPermissions(permissions);
        
        return result;
    }
}
```

#### 2. 权限验证流程

```java
public class PermissionValidation {
    
    public boolean hasPermission(Long userId, String permission) {
        // 1. 获取用户角色
        List<RoleAggregate> roles = roleService.getUserRoles(userId);
        
        // 2. 获取角色关联的菜单权限
        Set<String> permissions = new HashSet<>();
        for (RoleAggregate role : roles) {
            permissions.addAll(menuService.getRolePermissions(role.getRoleId()));
        }
        
        // 3. 验证权限
        return permissions.contains(permission);
    }
}
```

### 领域防腐层

为了防止外部系统的变化影响核心领域模型，我们需要设计防腐层：

```java
public interface UserRepository {
    
    UserEntity findByUsername(String username);
    
    UserEntity save(UserEntity user);
    
    void updateStatus(Long userId, UserStatus status);
}

public interface RoleRepository {
    
    List<RoleAggregate> findByUserId(Long userId);
    
    void saveUserRoles(Long userId, List<Long> roleIds);
}

public interface MenuRepository {
    
    List<MenuAggregate> findByRoleId(Long roleId);
    
    List<MenuAggregate> findAll();
    
    MenuAggregate save(MenuAggregate menu);
}
```

