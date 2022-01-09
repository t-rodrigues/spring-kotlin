create table tb_customer_roles (
    customer_id bigint,
    role        varchar(100),
    foreign key (customer_id) references tb_customer (id)
);
