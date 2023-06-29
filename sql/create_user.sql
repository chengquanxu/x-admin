-- auto-generated definition
create table x_user
(
    id       int auto_increment
        primary key,
    username varchar(50)      not null,
    password varchar(100)     null,
    email    varchar(50)      null,
    phone    varchar(20)      null,
    status   int(1)           null,
    avatar   varchar(200)     null,
    deleted  int(1) default 0 null
);