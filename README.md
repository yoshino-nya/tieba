# 贴吧 (Tieba) — 仿百度贴吧论坛后端

Spring Boot 论坛系统，支持用户注册登录、创建贴吧、发帖、点赞、评论。

## 技术栈

| 类别 | 技术 |
|------|------|
| 框架 | Spring Boot 4.0.6 |
| 语言 | Java 26 |
| ORM | MyBatis 4.0.1（注解 + XML 混合） |
| 认证 | Spring Security + JWT (jjwt 0.12.6) |
| 数据库 | MySQL 8.x |
| 工具 | Lombok, BCrypt |

## 功能

- 用户注册/登录（JWT 认证，BCrypt 密码加密）
- 创建贴吧、加入/退出贴吧、查看贴吧列表
- 在贴吧内发帖、编辑帖子、删除帖子（软删除）
- 帖子点赞/取消点赞（冗余计数）
- 评论（支持回复嵌套）

## 快速启动

```bash
# 1. 创建数据库
mysql -u root -p -e "CREATE DATABASE tieba;"
mysql -u root -p tieba < schema.sql

# 2. 修改 application.yml 中的数据库密码（默认 root/1234）

# 3. 启动
./mvnw spring-boot:run

# 服务运行在 http://localhost:4000
```

## API 概览

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/register` | 注册 |
| POST | `/api/auth/login` | 登录 |
| POST | `/api/boards` | 创建贴吧 |
| GET | `/api/boards` | 贴吧列表 |
| POST | `/api/boards/{id}/members` | 加入贴吧 |
| DELETE | `/api/boards/{id}/members` | 退出贴吧 |
| GET | `/api/boards/{id}/members` | 贴吧成员 |
| POST | `/api/boards/{boardId}/posts` | 发帖 |
| GET | `/api/boards/{boardId}/posts` | 贴吧帖子列表 |
| GET | `/api/users/{userId}/posts` | 用户帖子列表 |
| PATCH | `/api/posts/{postId}` | 编辑帖子 |
| DELETE | `/api/posts/{postId}` | 删除帖子 |
| POST | `/api/posts/{postId}/like` | 点赞 |
| POST | `/api/posts/{postId}/unlike` | 取消点赞 |
| POST | `/api/posts/{postId}/comments` | 发评论 |
| GET | `/api/posts/{postId}/comments` | 评论列表 |
| DELETE | `/api/comments/{commentId}` | 删除评论 |

> 除注册/登录外，所有接口需在 Header 中携带 `Authorization: Bearer <token>`

## 统一响应

```json
{
  "code": "SUCCESS",
  "message": null,
  "data": { ... }
}
```

错误码定义在 `ErrorCodeConstants` 中，全局异常处理器统一拦截。

## 项目结构

```
src/main/java/org/example/tieba/
├── config/          # SecurityConfig, JwtAuthFilter, CorsConfig, AuthenticatedUser
├── constants/       # ErrorCodeConstants
├── controller/      # REST 控制器
├── dto/             # 请求/响应 DTO + 统一 ApiResponse
├── exception/       # BusinessException + GlobalExceptionHandler
├── mapper/          # MyBatis Mapper 接口
├── model/           # 数据库实体
├── service/         # 业务逻辑
├── util/            # JwtUtil, SecurityUtil
└── validation/      # 自定义校验注解
```

## 设计要点

- **无外键**：简化表结构，约束在代码层保证
- **点赞冗余 `like_count`**：`post_likes` 记录真实状态，`posts.like_count` 冗余展示。插入/更新状态时同步维护，用空间换读取性能
- **软删除**：帖子和评论删除只标记 `status`，不物理删除。取消点赞改为 `status=0` 而非删记录
- **ApiResponse record**：Java 17+ record 实现不可变响应体，Jackson 自动序列化
- **自定义校验**：`@AtLeastOneNotBlank` 注解，编辑帖子时确保标题和内容至少一个非空
