create table if not exists `admin_users` (
    `id` identity primary auto_increment,
    `username` varchar(64) not null default '',
    `password` varchar(64) not null default '',
    `disabled` bit not null default 0,
    `created_at` date not null default current_date,
    `updated_at` date not null default current_date
)