-- liquibase formatted sql

-- changeset devices:1
CREATE SEQUENCE devices_seq START 1 INCREMENT 50;
CREATE TABLE IF NOT EXISTS devices (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    brand VARCHAR(255),
    state VARCHAR(255),
    creation_time TIME DEFAULT CURRENT_TIME
);
