create table
    addresses (
        address_id BIGINT unsigned auto_increment primary key,
        customer_id BIGINT unsigned not null,
        city varchar(100) not null,
        street varchar(200) not null,
        postal_code varchar(20) not null,
        constraint fk_addresses_customer foreign key (customer_id) references customers (customer_id) on delete cascade
    );