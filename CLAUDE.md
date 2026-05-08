# tieba — 贴吧项目

## 背景
- Java 实习求职项目，功能不求全，能面试聊开即可
- 一次给的信息不要多，逐步增量

## 技术栈
- Spring Boot 4.0.6, Java 26, MyBatis 4.0.1, MySQL 8.x
- JWT (jjwt 0.12.6), Spring Security, Lombok
- 端口 4000, 数据库 tieba, 用户 root/1234

## 当前进度

### 已完成
- 用户注册/登录 (JWT, BCrypt)
- JWT 认证过滤器 → AuthenticatedUser 写入 SecurityContext
- SecurityUtil 从 SecurityContext 取当前用户
- 统一响应体 ApiResponse<T>(code, message, data)
- 全局异常处理 (BusinessException, 参数校验, 兜底)
- 创建贴吧 + 加入贴吧
- 统一错误码 ErrorCodeConstants

### 待修 bug
- AuthService L41: 字符串 "RESOURCE_ALREADY_EXSITS" 拼写错，应改用 ErrorCodeConstants.RESOURCE_ALREADY_EXISTS
- User.java 字段 createdAt（驼峰）vs DB created_at（蛇形），需加 mybatis.configuration.map-underscore-to-camel-case: true
- BoardService setCreated_at 调了两次
- AuthController 里有未使用的 record Result
- CreateBoardRequest 导入了未使用的 LocalDateTime

### 待做功能
- 帖子 CRUD（Post 模型还是残的，缺 Mapper/Service/Controller）
- 帖子点赞/取消点赞（post_likes + posts.like_count 冗余同步）
- 帖子管理（吧主撤回/恢复 visible=0/1）
- 贴吧列表/详情查询
- 单元测试（至少写几个核心的）

## 数据库（schema.sql 为准）

```sql
users: id, username, email, password, nickname, avatar, created_at
boards: id, name, description, manager_id, created_at
board_members: board_id, user_id, role(member/owner/moderator), joined_at
post: id, user_id, board_id, visible, title, content, like_count, created_at, updated_at
post_likes: post_id, user_id, status(1=liked 0=unliked)
```

注：CLAUDE.md 旧版设计有 post_moderation 表和 board_members.status，但 schema.sql 没建，已简化掉。帖子撤回直接改 visible + 在代码层记日志即可。

## 设计决策（面试话题）
- **不用外键**：省事
- **点赞冗余 like_count**：post_likes 记真实状态，posts.like_count 纯展示，插入/改状态时同步维护。面试点："冗余字段保证读性能"
- **点赞不删数据**：取消点赞改 status=0，再点改回1
- **帖子撤回**：吧务设 visible=0；后续可扩展 moderation_log 表
- **ApiResponse record**：Java 17+ record 特性，不可变，Jackson 自动序列化

## 工作计划

已完成

- [x] 登录，注册，创建吧，加入吧

- [x] 发帖，点赞，删除，帖子列表 (post, put?, delete, get)
- [ ] 编辑，评论，退吧
- [ ] 头像上传，资料编辑

## 对话风格
- 省 token，每次只给一小段信息
- 每段对话先从 Java 代码开始
