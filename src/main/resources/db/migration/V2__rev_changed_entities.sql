CREATE TABLE IF NOT EXISTS rev_changed_entities (
    rev BIGINT NOT NULL,
    entity_name TEXT NOT NULL,
    entity_id TEXT,
    change_type VARCHAR(10),
    PRIMARY KEY (rev, entity_name, entity_id)
);

ALTER TABLE rev_changed_entities
    ADD CONSTRAINT fk_rev_changed_rev FOREIGN KEY (rev) REFERENCES revinfo(rev) ON DELETE CASCADE;
