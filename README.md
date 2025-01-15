# POPSICLE

基于 DDD（领域驱动设计）架构的现代 Spring Boot 权限管理脚手架。

## 项目架构

### 模块结构

```
popsicle
├── popsicle-api            # RPC 接口定义
├── popsicle-application    # 应用层
├── popsicle-boot           # 启动模块和配置
├── popsicle-domain         # 领域层
├── popsicle-infrastructure # 基础设施层
├── popsicle-trigger        # 触发层（HTTP、定时任务、事件）
└── popsicle-types          # 核心类型和异常定义
```

- **popsicle-api**: 定义对外提供的 RPC 服务接口，包含 DTO 对象和接口定义

- **popsicle-application**: 负责业务流程的编排，调用领域层完成具体业务逻辑

- **popsicle-boot**: 包含应用启动类和各种配置，如数据源、缓存、消息队列等配置

- **popsicle-domain**: 核心业务逻辑层，包含领域模型、领域服务和领域事件

- **popsicle-infrastructure**: 提供技术实现，如数据库访问、缓存、消息等基础设施

- **popsicle-trigger**: 处理外部请求，包括 REST API、定时任务、消息监听等

- **popsicle-types**: 定义共用的数据类型、枚举、异常等基础类型

## 技术栈

- Spring Boot
- MySQL
- Redis
- MyBatis
- JWT
- Fastjson
- Guava

## 功能特性

- 基于 RBAC 的权限管理
- JWT 认证
- 操作日志记录
- 数据权限控制
- 多租户支持
- RESTful API

## 快速开始

### 环境要求

- JDK 8+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+

### 本地开发

1. 克隆项目

```bash
git clone https://github.com/HogskinKitty/popsicle.git
```

2. 初始化数据库

```bash
mysql -u root -p < docs/sql/popsicle.sql
```

3. 修改配置

```
修改 popsicle-boot/src/main/resources/application-*.yml 中的数据库和 Redis 配置
```

4. 启动项目

```bash
cd popsicle
mvn clean package
java -jar popsicle-boot/target/popsicle.jar
```

## 环境配置

### 开发环境 (dev)

```
-Xms1G -Xmx1G -server -XX:MaxPermSize=256M -Xss256K
```

### 测试环境 (test)

```
-Xms1G -Xmx1G -server -XX:MaxPermSize=256M -Xss256K
```

### 生产环境 (prod)

```
-Xms6G -Xmx6G -server -XX:MaxPermSize=256M -Xss256K
```

## 贡献指南

欢迎提交 Issue 和 Pull Request

## 开源协议

本项目采用 [MIT 协议](LICENSE)
