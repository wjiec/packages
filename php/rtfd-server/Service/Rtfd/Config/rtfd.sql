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


create table if not exists `groups` (
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


create table if not exists `share` (
  `sid` serial not null primary key auto_increment,
  `code` varchar(32) not null,
  `paths` text not null,
  `created` int not null,
  `expired` int not null,
  unique key (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 auto_increment=1 ;


--
-- Default user for Admin
--
insert into `roles` (`rid`, `name`, `privilege_level`) values (1, 'admin', 100);
insert into `groups` (`gid`, `name`) values (1, 'admin');
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
  ('Init', 0),              -- Init Information
  ('GetUsers', 100),        -- Get users list
  ('UpdateUser', 100),      -- Update user
  ('AddRole', 100),         -- Add new role
  ('UpdateRole', 100),      -- Update role attr
  ('AddDoc', 100),          -- Add doc
  ('UpdateDoc', 100),       -- Update doc
  ('DelDoc', 100),          -- Del doc
  ('UpdateProfile', 10),    -- Update self profile
  ('GetProfile', 10),       -- Get profile
  ('Logout', 10),           -- Logout
  ('GenerateShare', 0),     -- Generate share code
  ('GetShareList', 100),    -- Get share list
  ('RemoveShare', 100)      -- Remove share
