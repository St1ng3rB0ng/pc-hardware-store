create table if NOT EXISTS categories(
    id BIGINT unsigned auto_increment PRIMARY KEY,
    -- added "unsigned"
    name VARCHAR(255) NOT NULL
);

create table if NOT EXISTS products(
    id BIGINT unsigned auto_increment PRIMARY KEY,
    category_id BIGINT,
    sku varchar(40) not null unique,
    name varchar(200) not null,
    price DECIMAL(12, 2) not null,
    stock_quantity INT unsigned not null default 0,
    is_active BOOLEAN not null default true,
    constraint chk_product_price check(price > 0),
    constraint chk_products_name_not_blank check (TRIM(name) <> ''),
    constraint fk_products_category foreign key (category_id) references categories(category_id) on delete restrict,
    index idx_products_category (category_id)
);