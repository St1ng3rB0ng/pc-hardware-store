CREATE TABLE reviews (
    review_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT UNSIGNED NULL,
    product_id BIGINT UNSIGNED NULL,
    message TEXT NULL,
    rating TINYINT UNSIGNED NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_reviews_rating CHECK (rating BETWEEN 1 AND 5),
    CONSTRAINT fk_reviews_customer FOREIGN KEY (customer_id)
        REFERENCES customers (customer_id) ON DELETE SET NULL,
    CONSTRAINT fk_reviews_product FOREIGN KEY (product_id)
        REFERENCES products (id) ON DELETE CASCADE,

    INDEX idx_review_customers (customer_id),
    INDEX idx_review_products (product_id)
);

CREATE TABLE review_responses (
    response_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    review_id BIGINT UNSIGNED NOT NULL,
    manager_name VARCHAR(100) NOT NULL,
    response_text TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_manager_name_not_blank CHECK (TRIM(manager_name) <> ''),
    CONSTRAINT chk_response_text_not_blank CHECK (TRIM(response_text) <> ''),
    CONSTRAINT fk_responses_review FOREIGN KEY (review_id)
        REFERENCES reviews (review_id) ON DELETE CASCADE,

    INDEX idx_responses_review (review_id)
);