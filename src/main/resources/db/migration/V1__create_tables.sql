-- Expected types:
--   Product type: Enum of integers
--     0 - phone
--     1 - subscription

CREATE TABLE products
(
    id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    type  TINYINT      NOT NULL, -- Type: Product type
    price FLOAT        NOT NULL,
    store VARCHAR(255) NOT NULL
);

CREATE TABLE properties
(
    product_id BIGINT       NOT NULL,
    key        VARCHAR(255) NOT NULL,
    value      VARCHAR(255),

    PRIMARY KEY (product_id, key),
    FOREIGN KEY (product_id) REFERENCES products (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);
