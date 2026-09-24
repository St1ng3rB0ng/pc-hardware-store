create table
    customers (
        customer_id BIGINT unsigned auto_increment primary key,
        full_name varchar(150) not null,
        email varchar(254) not null unique,
        created_at timestamp not null default CURRENT_TIMESTAMP,
        constraint chk_customers_email_not_blank check (TRIM(email) <> ''),
        constraint chk_customers_full_name_not_blank check (TRIM(full_name) <> '')
    );