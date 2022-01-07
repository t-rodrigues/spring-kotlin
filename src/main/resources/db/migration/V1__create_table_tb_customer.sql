create table tb_customer (
    id    bigserial,
    name  varchar(150) not null,
    email varchar(200) not null unique,

    constraint customer_pkey primary key (id)
);
