-- liquibase formatted sql

-- changeset rbilalov:1

create table if not exists rule
(
    id   serial,
    product_id  uuid,
    product_name varchar(255),
    product_name text,
    rule bytea[]
    );