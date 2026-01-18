CREATE TABLE recipients (
    id UUID PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    address VARCHAR(255),
    gender VARCHAR(20),
    age INTEGER
);

ALTER TABLE greetings
DROP COLUMN recipient,
ADD COLUMN recipient_id UUID REFERENCES recipients(id);