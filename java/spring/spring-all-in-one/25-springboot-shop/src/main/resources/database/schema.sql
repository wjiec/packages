drop table if exists spring_item;
create table if not exists spring_item (
    id identity primary key auto_increment,
    title varchar not null default '',
    price int not null default 0,
    created_at datetime not null default now(),
    updated_at datetime not null default now()
);

drop table if exists spring_order;
create table if not exists spring_order (
    id identity primary key auto_increment,
    name varchar not null default '',
    address varchar not null default '',
    cc_number varchar not null default '',
    cc_cvv varchar not null default '',
    created_at datetime not null default now(),
    updated_at datetime not null default now()
);
