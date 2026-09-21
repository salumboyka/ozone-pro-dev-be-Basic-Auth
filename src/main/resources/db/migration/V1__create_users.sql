CREATE TABLE users (
    id UUID PRIMARY KEY,
    email VARCHAR(50) NOT NULL,
    username VARCHAR(120),
    fname VARCHAR(120) NOT NULL,
    mname VARCHAR(120) NOT NULL,
    lname VARCHAR(120) NOT NULL,
    gender VARCHAR(120) NOT NULL,
    age INTEGER NOT NULL,
    address VARCHAR(120) NOT NULL,
    CONSTRAINT users_email_key UNIQUE (email),
    CONSTRAINT users_username_key UNIQUE (username)
);
