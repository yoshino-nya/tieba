base_url = http://localhost:4000

以下省略 base_url

| url                           | body                      | response                                      | des      |
| ----------------------------- | ------------------------- | --------------------------------------------- | -------- |
| post /api/auth/login          | username, password        | token, username, email, userId                | 登录     |
| post /api/auth/register       | username, email, password | token, username, email, userId                | 注册     |
| post /api/boards              | name, description         | id, name, description, manager_id, created_at | 创建吧   |
| post /api/boards/{id}/members | -                         | code(SUCCESS), message                        | 加入贴吧 |
|                               |                           |                                               |          |

