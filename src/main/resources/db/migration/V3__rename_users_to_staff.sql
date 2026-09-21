ALTER TABLE users RENAME TO staff;

ALTER TABLE staff
    RENAME CONSTRAINT users_email_key TO staff_email_key;

ALTER TABLE staff
    RENAME CONSTRAINT users_username_key TO staff_username_key;