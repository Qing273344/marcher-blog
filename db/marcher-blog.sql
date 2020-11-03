-- 博客文章 blog_article
CREATE TABLE `blog_article` (
  `article_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章编号',
  `type_id` bigint(20) NOT NULL COMMENT '类型',
  `title` varchar(255) DEFAULT NULL COMMENT '文章标题',
  `summary` varchar(300) DEFAULT NULL COMMENT '摘要，最多200字',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `is_top` tinyint(1) DEFAULT '0' COMMENT '是否置顶',
  `is_comment` tinyint(1) DEFAULT '1' COMMENT '是否开启评论',
  `liked_count` INT(11) NOT NULL DEFAULT 1 COMMENT '点赞数量',
  `views_count` INT(11) NOT NULL DEFAULT 1 COMMENT '浏览数量',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='博客文章';


-- 博客文章内容 blog_article_content
CREATE TABLE `blog_article_content` (
  `article_id` bigint(20) NOT NULL COMMENT '文章编号',
  `content_md` longtext COMMENT 'markdown版的文章内容',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博客文章内容';


-- 用户浏览文章 blog_article_browse
CREATE TABLE `blog_article_browse` (
  `browse_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章浏览ID',
  `article_id` bigint(20) NOT NULL COMMENT '文章ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '已登录用户ID',
  `user_ip` varchar(50) DEFAULT NULL COMMENT '用户IP',
  `browse_time` bigint(20) DEFAULT NULL COMMENT '浏览时间',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`browse_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户浏览文章';


-- 博客类型 blog_type
CREATE TABLE `blog_type` (
  `type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '类型id',
--   `pid` bigint(20) DEFAULT '0' COMMENT '父类型id,顶级为0',
  `name` varchar(50) DEFAULT NULL COMMENT '文章类型名',
  `description` varchar(200) DEFAULT NULL COMMENT '类型介绍',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `available` tinyint(1) DEFAULT '1' COMMENT '是否可用',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博客类型';


-- 博客文章类型管理 blog_article_type
CREATE TABLE `blog_article_type` (
  `article_type_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章类型id',
  `article_id` bigint(20) NOT NULL COMMENT '文章编号',
  `type_id` bigint(20) NOT NULL COMMENT '类型id',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`article_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博客文章类型关联';


-- 标签 blog_tag
CREATE TABLE `blog_tag` (
  `tag_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(50) NOT NULL COMMENT '标签名',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='标签';


-- 博客文章标签管理 blog_article_tag
CREATE TABLE `blog_article_tag` (
  `article_tag_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文章标签id',
  `article_id` bigint(20) NOT NULL COMMENT '文章编号',
  `tag_id` bigint(20) NOT NULL COMMENT '类型id',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`article_tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='博客文章标签关联';


-- 博客用户 blog_user
CREATE TABLE `blog_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(32) NOT NULL COMMENT '密码',
  `nickname` varchar(32) DEFAULT '' COMMENT '昵称',
  `avatar` varchar(256) DEFAULT NULL COMMENT '头像',
  `user_type` tinyint(2) NOT NULL COMMENT '用户类型, 1:本帅, 2:大神',
  `source` tinyint(4) NOT NULL COMMENT '用户来源, 1:PC, 2:web移动',
  `is_locked` tinyint(1) DEFAULT NULL COMMENT '状态(0:锁定,1:正常)',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='博客用户';


-- 博客资源 blog_resources
CREATE TABLE `blog_resource` (
  `resource_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源编号',
  `name` varchar(64) NOT NULL COMMENT '资源名称',
  `permission` varchar(128) DEFAULT NULL COMMENT '授权 栗子 user:save',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='博客资源';

-- 用户类型-资源 blog_user_resource
CREATE TABLE `blog_user_resource` (
  `user_resource_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_type` tinyint(2) NOT NULL COMMENT '用户类型',
  `resource_id` bigint(20) NOT NULL COMMENT '资源编号',

  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除(0:正常,1:删除)',
  PRIMARY KEY (`user_resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户类型-资源';