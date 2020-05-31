
create table public."foo" (
    "id" serial4 not null primary key,
    "name" varchar(32) not null default '',
    "age" int not null default 0,
    "desc" text not null default ''
);

insert into public.foo ("name", "age", "desc") values
    ('Ada', 18, 'hello'), ('Bob', 23, 'world'), ('Candy', 16, 'from'), ('David', 22, 'jdbc');

select * from public.foo;

delete from public.foo;

drop table public.foo;
