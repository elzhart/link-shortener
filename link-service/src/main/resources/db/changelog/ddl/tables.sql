CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL
        CONSTRAINT user_pk PRIMARY KEY,
    name     TEXT UNIQUE NOT NULL,
    email    TEXT UNIQUE NOT NULL,
    created  TIMESTAMP   NOT NULL,
    password TEXT UNIQUE NOT NULL
);

-- column indices
CREATE INDEX users_id_idx ON users (id);
CREATE INDEX users_name_idx ON users (name);

CREATE TABLE IF NOT EXISTS links
(
    id      BIGSERIAL
        CONSTRAINT link_pk PRIMARY KEY,
    url     TEXT      NOT NULL UNIQUE,
    user_id BIGINT,
    created TIMESTAMP NOT NULL,
    expired TIMESTAMP
);

ALTER TABLE links
    ADD CONSTRAINT FK_user_id FOREIGN KEY (user_id) REFERENCES users;

CREATE INDEX link_id_idx ON users (id);
CREATE INDEX link_url_idx ON links (url);
CREATE INDEX created_idx ON links (created);