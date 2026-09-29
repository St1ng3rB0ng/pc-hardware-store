ALTER TABLE addresses
ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE COMMENT 'for Soft Delete implementation';

create table
    orders (
        order_id BIGINT unsigned not null auto_increment primary key,
        customer_id BIGINT unsigned not null,
        shipping_address_id BIGINT unsigned not null,
        status varchar(20) not null default 'CREATED',
        created_at TIMESTAMP not null default current_timestamp,
        constraint chk_order_status check (
            status in (
                'CREATED',
                'PROCESSING',
                'SHIPPED',
                'DELIVERED',
                'PAID',
                'CANCELLED'
            )
        ),
        constraint fk_orders_customer foreign key (customer_id) references customers (customer_id) on delete restrict,
        constraint fk_orders_address foreign key (shipping_address_id) references addresses (address_id) on delete restrict,
        index idx_orders_customer (customer_id),
        index idx_orders_created_at (created_at)
    );