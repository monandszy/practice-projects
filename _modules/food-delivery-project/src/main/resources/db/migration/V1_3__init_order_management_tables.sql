CREATE TABLE food_delivery.client_order
(
    id            SERIAL      NOT NULL,
    status        VARCHAR(20) NOT NULL,
    client_id     INT         NOT NULL,
    seller_id     INT         NOT NULL,
    address_id    INT,
    restaurant_id INT         NOT NULL,
    time_of_order timestamptz NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT status_in CHECK (
        client_order.status IN
        (
         'IN_PROGRESS',
         'TRAVELLING',
         'COMPLETED',
         'CANCELLED'
            )),
    CONSTRAINT fk_client_account
        FOREIGN KEY (client_id)
            REFERENCES food_delivery.account (id),
    CONSTRAINT fk_seller_account
        FOREIGN KEY (seller_id)
            REFERENCES food_delivery.account (id),
    CONSTRAINT fk_delivery_address
        FOREIGN KEY (address_id)
            REFERENCES food_delivery.address (id),
    CONSTRAINT fk_order_restaurant
        FOREIGN KEY (restaurant_id)
            REFERENCES food_delivery.restaurant (id)
);

CREATE TABLE food_delivery.order_position
(
    id               SERIAL NOT NULL,
    order_id         INT    NOT NULL,
    menu_position_id INT    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_menu
        FOREIGN KEY (menu_position_id)
            REFERENCES food_delivery.menu_position (id),
    CONSTRAINT fk_position_order
        FOREIGN KEY (order_id)
            REFERENCES food_delivery.client_order (id)
);