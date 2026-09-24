CREATE TABLE next_of_kin (
    id UUID PRIMARY KEY,
    full_name VARCHAR(120) NOT NULL,
    relationship VARCHAR(50) NOT NULL,
    phone_number VARCHAR(30) NOT NULL,
    email VARCHAR(100),
    address VARCHAR(255) NOT NULL,
    staff_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_next_of_kin_staff
        FOREIGN KEY (staff_id) REFERENCES staff(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE INDEX idx_next_of_kin_staff_id ON next_of_kin(staff_id);
