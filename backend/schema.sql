-- 创建用户表
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    username VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(128) NOT NULL,
    password VARCHAR(255) NOT NULL,

    nickname VARCHAR(20),
    avatar VARCHAR(36) COMMENT '头像 UUID',

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建吧表
CREATE TABLE boards (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(14) NOT NULL UNIQUE,
    description VARCHAR(200),
    manager_id BIGINT NOT NULL COMMENT "吧主id",

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 吧成员表
CREATE TABLE board_members (
    board_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,

    role VARCHAR(10) DEFAULT 'member' COMMENT 'owner / member / moderator',

    joined_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    PRIMARY KEY (board_id, user_id)
);

-- 帖子表
CREATE TABLE post(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    board_id BIGINT NOT NULL,
    visible TINYINT(1) NOT NULL DEFAULT 1,

    title VARCHAR(30) NOT NULL,
    content TEXT NOT NULL,
    like_count INT DEFAULT 0,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE post_likes (
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    status TINYINT(1) NOT NULL DEFAULT 1 COMMENT '1=liked, 0=unliked',
    PRIMARY KEY (post_id, user_id)
);

ALTER TABLE post DROP COLUMN visible;
ALTER TABLE post
ADD COLUMN status TINYINT DEFAULT 0
COMMENT  '0: 正常, 1: 删除, 2: 隐藏';

-- 应该是 012 来着……

CREATE TABLE comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL ,
    content VARCHAR(500) NOT NULL ,
    post_id BIGINT NOT NULL,
    root_id BIGINT NOT NULL,
    parent_id BIGINT,
    status TINYINT NOT NULL DEFAULT 0 COMMENT '0: 正常, 1: 删除',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE users
MODIFY COLUMN username VARCHAR(39) NOT NULL;

-- root_id 可以不要设置 NOT NULL，一级评论需要将 root_id 设置为 id，本质插入之后是 NOT NULL
ALTER TABLE comment
MODIFY COLUMN root_id BIGINT NULL;