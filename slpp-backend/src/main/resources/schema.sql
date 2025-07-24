-- Example schema creation (when using "spring.jpa.hibernate.ddl-auto=none")
-- If you're using "update" or "create-drop", you might not need schema.sql

CREATE TABLE IF NOT EXISTS app_user (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    full_name VARCHAR(255),
    date_of_birth DATE,
    password VARCHAR(255),
    biometric_id VARCHAR(10) UNIQUE,
    role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS petition (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    text TEXT,
    status VARCHAR(50),
    response TEXT,
    signatures INT DEFAULT 0,
    creator_id BIGINT,
    CONSTRAINT fk_creator
        FOREIGN KEY(creator_id) 
        REFERENCES app_user(id)
);

CREATE TABLE IF NOT EXISTS signature (
    id SERIAL PRIMARY KEY,
    petition_id BIGINT,
    user_id BIGINT
);
