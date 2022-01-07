create table tb_book (
    id          bigserial,
    title       varchar(200) not null,
    price       decimal(10, 2) not null,
    status      varchar(100),
    customer_id bigint not null,

    constraint book_pkey primary key (id),
    foreign key (customer_id) references tb_customer (id)
);
