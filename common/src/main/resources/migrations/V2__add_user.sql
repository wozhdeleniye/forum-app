create table _user
(
    role          smallint
        constraint _user_role_check
            check ((role >= 0) AND (role <= 2)),
    creation_date timestamp(6),
    id            varchar(255) not null
        primary key,
    login         varchar(255)
);

alter table _user
    owner to postgres;
