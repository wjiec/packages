--
-- Read the docs tables structure
--


create table if not exists `users` (
  `uid` serial not null primary key auto_increment,
  `name` varchar(32) not null,
  `nickname` varchar(128) default null,
  `password` varchar(256) not null,
  `role_id` int not null,
  `group_id` int not null
);


create table if not exists `group` (
  `gid` serial not null primary key auto_increment,
  `name` varchar(64) not null
);


create table if not exists `roles` (
  `rid` serial not null primary key auto_increment,
  `name` varchar(32) not null,
  `privilege_level` tinyint not null
);


create table if not exists `actions` (
  `aid` serial not null primary key auto_increment,
  `name` varchar(32) not null,
  `privilege_level` tinyint not null
);


create table if not exists `docs` (
  `cid` serial not null primary key auto_increment,
  `name` varchar(64) not null,
  `path` varchar(256) not null,
  `owner_id` int DEFAULT 0,
  `group_id` int DEFAULT 0,
  `min_privilege_level` tinyint DEFAULT 101
) ENGINE=InnoDB DEFAULT CHARSET=latin1 auto_increment=1 ;



--
-- Default user for Admin
--
insert into `roles` (`rid`, `name`, `privilege_level`) values (1, 'admin', 100);
insert into `group` (`gid`, `name`) values (1, 'admin');
insert into `users` (`uid`, `name`, `nickname`, `password`, `role_id`, `group_id`) values
  (1, 'admin', 'Admin', '$2y$10$IbYOfp3KjfimRImL21yB6ep3KVjZ68gGjHGCSHVB.HXW7dnFJaZZC', 1, 1);

--
-- All actions
--
insert into `actions`(`name`, `privilege_level`) values
  ('GetDocs', 0),           -- get allowed docs
  ('GetDocTree', 0),        -- get documents tree
  ('GetDocContent', 0),     -- get documents contents
  ('AddUser', 100),         -- add user operator [admin]
  ('DelUser', 100),         -- delete user operator
  ('Login', 0),             -- login operator
  ('Logout', 0),            -- logout operator
  ('Init', 0)               -- Init Information
