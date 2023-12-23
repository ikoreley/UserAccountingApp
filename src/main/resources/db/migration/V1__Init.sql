create table if not exists users
(
    id           bigint not null
        primary key,
    created_date timestamp(6),
    description  text,
    email        varchar(255) not null
        constraint email_must_unique
            unique,
    name         varchar(255) not null,
    password     varchar(3000),
    roles        varchar(255),
    surname      varchar(255) not null,
    update_date  timestamp(6),
    username     varchar(255) not null
        constraint username_must_unique
            unique
);

create table if not exists events
(
    id          bigint not null
        primary key,
    action      varchar(255) not null,
    action_date timestamp(6),
    user_id     bigint not null
        constraint user_id_refer_on_users
            references users
);

create index idx_owner
    on events (user_id);

create index idx_username
    on users (username);