create table if not exists administrator (
    `id` identity not null primary key auto_increment,
    `username` varchar(32) not null default '',
    `password` varchar(64) not null default '',
    `created_at` datetime not null default now(),
    `updated_at` datetime not null default now(),

    unique (`username`)
);
