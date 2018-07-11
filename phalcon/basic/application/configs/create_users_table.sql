create table if not exists `users` (
    `id` serial not null primary key auto_increment,
    `name` varchar(64) not null,
    `password` varchar(64) not null,

    unique(`name`)
) engine=innodb default charset=utf8mb4;
