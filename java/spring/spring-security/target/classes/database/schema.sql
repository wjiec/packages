create table `user` (
    id identity not null primary key,
    username varchar not null default '' unique,
    password varchar not null default '',
    email varchar not null default '' unique,
    created_at date not null default current_time,
    updated_at date not null default current_date
);
