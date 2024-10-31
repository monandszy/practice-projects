CREATE SCHEMA emp;
CREATE TABLE emp.employee
(
    employee_id SERIAL PRIMARY KEY NOT NULL,
    name        VARCHAR(20)        NOT NULL,
    surname     VARCHAR(20)        NOT NULL,
    salary      NUMERIC(7, 2)      NOT NULL
);