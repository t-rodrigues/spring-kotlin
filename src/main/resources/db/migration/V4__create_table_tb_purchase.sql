create table tb_purchase (
    id          bigserial,
    nfe         varchar(100),
    price       decimal(10, 2) not null,
    customer_id bigint not null,
    created_at  timestamp without time zone not null,

    constraint purchase_pkey primary key (id),
    foreign key (customer_id) references tb_customer (id)
);

create table tb_purchase_book (
    purchase_id bigint not null,
    book_id     bigint not null,

    primary key (purchase_id, book_id),
    foreign key (purchase_id) references tb_purchase (id),
    foreign key (book_id) references tb_book (id)
);
