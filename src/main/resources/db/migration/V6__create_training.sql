CREATE TABLE training (
    id UUID PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description VARCHAR(500),
    provider VARCHAR(150) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    staff_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE,
    CONSTRAINT fk_training_staff
        FOREIGN KEY (staff_id) REFERENCES staff(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE INDEX idx_training_staff_id ON training(staff_id);