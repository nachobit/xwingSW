-- db/migration/V1__create_spaceship_table.sql

CREATE TABLE SPACESHIP (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    TYPE VARCHAR(255),
    DESCRIPTION VARCHAR(1000)
);