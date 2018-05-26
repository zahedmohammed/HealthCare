
ALTER TABLE ONLY alert ADD COLUMN org_id character varying(255);

ALTER TABLE ONLY alert
    ADD CONSTRAINT alert_org_fk FOREIGN KEY (org_id) REFERENCES org(id);