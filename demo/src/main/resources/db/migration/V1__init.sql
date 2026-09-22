create table if NOT EXISTS categories(
    id BIGINT unsigned auto_increment PRIMARY KEY,
    -- added "unsigned"
    name VARCHAR(255) NOT NULL
);

