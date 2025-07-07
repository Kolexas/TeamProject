-- liquibase formatted sql



create table if not exists rule
(
    id   serial,
    product_id  uuid,
    product_name varchar(255),
    product_text text,
    rule bytea[]
    );

-- changeset rbilalov:2
create table if not exists stats
(
    id   serial,
    rule_id  bigint,
    count bigint
    );
-- changeset rbilalov:3

drop table if exists stats;
create table if not exists stats
(
    rule_id serial,
    count bigint
);

-- changeset rbilalov:4
drop table if exists stats;
create table if not exists stats
(
    id serial,
    rule_id bigint,
    count bigint
);

-- changeset Kolexas:5
ALTER TABLE stats
ADD CONSTRAINT FK_STATS_RULE
FOREIGN KEY (rule_id)
REFERENCES rule(id);
