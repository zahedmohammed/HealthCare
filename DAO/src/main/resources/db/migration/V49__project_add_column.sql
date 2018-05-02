--
-- Name: add columns to project
-- author Mohammed Shoukath Ali
--



ALTER TABLE project DROP COLUMN if EXISTS cloud_account_id;
ALTER TABLE ONLY project ADD COLUMN cloud_account_id character varying(255);

