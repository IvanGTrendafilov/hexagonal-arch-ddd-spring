CREATE TABLE greetings (
    id UUID PRIMARY KEY,
    message VARCHAR(500) NOT NULL,
    recipient VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_greetings_created_at ON greetings(created_at);


