CREATE TABLE IF NOT EXISTS revinfo (
    rev BIGSERIAL PRIMARY KEY,
    revtstmp BIGINT NOT NULL,
    username TEXT,
    ip_address TEXT,
    user_agent TEXT,
    request_id TEXT
);
