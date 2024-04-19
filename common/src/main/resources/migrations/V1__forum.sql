create table _category
(
    child_count          integer default 0,
    creation_date        timestamp(6),
    last_moderation_date timestamp(6),
    author               varchar(255),
    id                   varchar(255) not null
        primary key,
    name                 varchar(255),
    parent_category      varchar(255)
);

alter table _category
    owner to postgres;

create table _message
(
    creation_date        timestamp(6),
    last_moderation_date timestamp(6),
    topic                uuid,
    author               varchar(255),
    category             varchar(255),
    id                   varchar(255) not null
        primary key,
    text                 varchar(255)
);

alter table _message
    owner to postgres;

create table _topic
(
    creation_date        timestamp(6),
    last_moderation_date timestamp(6),
    author               varchar(255),
    id                   varchar(255) not null
        primary key,
    name                 varchar(255),
    parent_category      varchar(255)
);

alter table _topic
    owner to postgres;





