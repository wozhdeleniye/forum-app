create table _token
(
    id    varchar(255) not null
        primary key,
    login varchar(255),
    token varchar(255)
);

alter table _token
    owner to postgres;

create table _user
(
    role          smallint
        constraint _user_role_check
            check ((role >= 0) AND (role <= 3)),
    creation_date timestamp(6),
    email_adress  varchar(255),
    id            varchar(255) not null
        primary key,
    login         varchar(255),
    name          varchar(255),
    password      varchar(255),
    phone_number  varchar(255)
);

alter table _user
    owner to postgres;