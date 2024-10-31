CREATE SCHEMA web_mvc;

CREATE TABLE web_mvc.cat_model
(
    cat_id    SERIAL PRIMARY KEY NOT NULL,
    name      VARCHAR(20),
    salary    numeric(7, 2) NOT NULL,
    happy_day TIMESTAMP WITH TIME ZONE
);