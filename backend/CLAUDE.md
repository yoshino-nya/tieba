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
- 统一错误码 ErrorCodeConstants
- 创建贴吧 + 加入贴吧 + 退出贴吧
- 贴吧列表查询
- 帖子 CRUD（发帖、列表、编辑、软删除）
- 帖子点赞/取消点赞（post_likes + posts.like_count 冗余同步）
- 评论 CRUD（发评论、列表、软删除，支持回复嵌套）
- 自定义校验 @AtLeastOneNotBlank

### 已修 bug（2026-05-09）
- ✅ AuthService: 已改用 ErrorCodeConstants 常量
- ✅ mybatis map-underscore-to-camel-case: true 已加
- ✅ BoardService setCreated_at 重复调用已修
- ✅ AuthController 未使用 record Result 已删
- ✅ CreateBoardRequest 未使用 import 已删
- ✅ BoardController.leave() 补充了 service 调用
- ✅ BoardMemberMapper.remove() 补了 board_id 条件
- ✅ PostService.updatePost() 加了存在性校验和权限校验

### 待做功能
- 帖子管理（吧主撤回/恢复 status=2）
- 头像上传
- 用户资料编辑
- 列表分页
- 单元测试（目前仅一个空的上下文加载测试）

## 数据库（schema.sql 为准）

```sql
users: id, username, email, password, nickname, avatar, created_at
boards: id, name, description, manager_id, created_at
board_members: board_id, user_id, role(member/owner/moderator), joined_at
post: id, user_id, board_id, title, content, like_count, status, created_at, updated_at
post_likes: post_id, user_id, status(1=liked 0=unliked)
comment: id, user_id, content, post_id, root_id, parent_id, status, created_at
```

## 设计决策（面试话题）
- **不用外键**：省事
- **点赞冗余 like_count**：post_likes 记真实状态，posts.like_count 纯展示，插入/改状态时同步维护。面试点："冗余字段保证读性能"
- **点赞不删数据**：取消点赞改 status=0，再点改回1
- **帖子撤回**：吧务设 status=2；当前仅用了 0(正常) 和 1(删除)
- **ApiResponse record**：Java 17+ record 特性，不可变，Jackson 自动序列化

## 工作计划

已完成

- [x] 登录，注册，创建吧，加入吧
- [x] 发帖，点赞，删除，帖子列表，编辑帖子
- [x] 评论，退吧
- [ ] 头像上传，资料编辑
- [ ] 单元测试

## 文档
- README.md: 项目介绍，API 概览，快速启动
- 简历-项目描述.md: 简历用项目描述 + 技术亮点
- 待补充列表.md: 待修/待改进项 + 面试预判问题
- doc.md: API 测试 curl 命令（仅前半段，待补全）

## 对话风格
- 省 token，每次只给一小段信息
- 每段对话先从 Java 代码开始
