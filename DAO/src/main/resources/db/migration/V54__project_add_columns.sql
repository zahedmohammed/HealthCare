--
-- Moved GitAccount columns to Project.
--


ALTER TABLE project DROP COLUMN if EXISTS url;
ALTER TABLE ONLY project ADD COLUMN url character varying(255);

ALTER TABLE project DROP COLUMN if EXISTS branch;
ALTER TABLE ONLY project ADD COLUMN branch character varying(255);

ALTER TABLE project DROP COLUMN if EXISTS last_commit;
ALTER TABLE ONLY project ADD COLUMN last_commit character varying(255);

