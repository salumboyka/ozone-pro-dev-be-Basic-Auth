ALTER TABLE users RENAME TO staff;

ALTER TABLE staff
    RENAME CONSTRAINT users_email_key TO staff_email_key;

ALTER TABLE staff
    RENAME CONSTRAINT users_username_key TO staff_username_key;

ALTER TABLE staff
    ADD COLUMN status VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
    ADD COLUMN date_of_join DATE NOT NULL DEFAULT CURRENT_DATE,
    ADD COLUMN position VARCHAR(120) NOT NULL DEFAULT 'Developer';