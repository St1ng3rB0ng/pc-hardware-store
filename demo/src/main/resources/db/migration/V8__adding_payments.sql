create table
    payments (
        payment_id BIGINT unsigned not NULL auto_increment primary key,
        order_id BIGINT unsigned not NULL,
        amount DECIMAL(12, 2) not null,
        status varchar(20) not null,
        paid_at timestamp null,
        constraint chk_payment_amount check (amount > 0),
        constraint chk_payment_status check (
            status in ('PENDING', 'SUCCESSFUL', 'FAILED', 'REFUNDED')
        ),
        constraint fk_payments_order foreign key (order_id) references orders (order_id) on delete restrict,
        index idx_payment_orders (order_id)
    );