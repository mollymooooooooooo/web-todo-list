CREATE TABLE IF NOT EXISTS "user"
(
    id            UUID         NOT NULL,
    email         VARCHAR(200) NOT NULL,
    password_hash VARCHAR(200) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);