create table if not exists `users` (
    `id` int primary key,
    `username` varchar(64) not null default '',
    `password` varchar(64) not null default '',
    `created_at` timestamp default current_timestamp,
    `updated_at` timestamp default current_timestamp
);
