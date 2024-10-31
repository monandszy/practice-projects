DELETE FROM customer WHERE 1=1;
DELETE FROM opinion WHERE 1=1;
DELETE FROM purchase WHERE 1=1;
DELETE FROM product WHERE 1=1;
DELETE FROM producer WHERE 1=1;

DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS opinion CASCADE;
DROP TABLE IF EXISTS purchase CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS producer CASCADE;

CREATE TABLE customer
(
    id            SERIAL PRIMARY KEY NOT NULL,
    user_name     TEXT UNIQUE        NOT NULL,
    email         TEXT UNIQUE        NOT NULL,
    name          TEXT,
    surname       TEXT,
    date_of_birth DATE
);

CREATE TABLE producer
(
    id            SERIAL PRIMARY KEY NOT NULL,
    producer_name TEXT UNIQUE        NOT NULL,
    address       TEXT
);

CREATE TABLE product
(
    id            SERIAL PRIMARY KEY NOT NULL,
    product_code  TEXT UNIQUE        NOT NULL,
    product_name  TEXT               NOT NULL,
    product_price NUMERIC(10, 2)     NOT NULL,
    adults_only   bool               NOT NULL,
    description   TEXT               NOT NULL,
    producer_id   INT                NOT NULL,
    CONSTRAINT fk_producer
        FOREIGN KEY (producer_id)
            REFERENCES producer (id)
);


CREATE TABLE purchase
(
    id               SERIAL PRIMARY KEY NOT NULL,
    customer_id      INT                NOT NULL,
    product_id       INT                NOT NULL,
    quantity         INT                NOT NULL,
    date_of_purchase DATE               NOT NULL,
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (id),
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
);


CREATE TABLE opinion
(
    id              SERIAL PRIMARY KEY NOT NULL,
    customer_id     INT                NOT NULL,
    product_id      INT                NOT NULL,
    stars           INTEGER            NOT NULL,
    comment         TEXT               NOT NULL,
    date_of_comment DATE               NOT NULL,
    CONSTRAINT check_types
        CHECK (stars >= 1 AND stars <= 5),
    CONSTRAINT fk_customer
        FOREIGN KEY (customer_id)
            REFERENCES customer (id),
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
);