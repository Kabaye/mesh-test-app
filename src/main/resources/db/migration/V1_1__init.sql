create table user_table
(
    id            bigint
        constraint user_pk
            primary key,
    name          varchar(500),
    date_of_birth date,
    password      varchar(500) not null
        constraint check_min_length check ( char_length(password) >= 8 )
);

create table account
(
    id          bigint
        constraint account_pk
            primary key,
    user_id     bigint
        unique
        constraint account_user_id_fk
            references user_table (id),
    balance     decimal
        constraint check_balance_non_negative check ( balance >= 0.0 ),
    max_balance decimal,
    constraint check_max_balance check (balance <= account.max_balance)

);

create table email_data
(
    id      bigint
        constraint email_data_pk
            primary key,
    user_id bigint
        constraint email_data_user_id_fk
            references user_table (id),
    email   varchar(200) unique
);

create table phone_data
(
    id      bigint
        constraint phone_data_pk
            primary key,
    user_id bigint
        constraint phone_data_user_id_fk
            references user_table (id),
    phone   varchar(13) unique
);
