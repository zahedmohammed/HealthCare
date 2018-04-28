--
-- Name: add columns to Account
-- author Mohammed Shoukath Ali
--

ALTER TABLE cloud_account DROP COLUMN if EXISTS account_type;
ALTER TABLE ONLY cloud_account ADD COLUMN account_type character varying(255);

ALTER TABLE notification_account DROP COLUMN if EXISTS cloud_account_id;
ALTER TABLE ONLY notification_account ADD COLUMN cloud_account_id character varying(255);

ALTER TABLE skill_subscription DROP COLUMN if EXISTS cloud_account_id;
ALTER TABLE ONLY skill_subscription ADD COLUMN cloud_account_id character varying(255);

ALTER TABLE ONLY skill_subscription DROP CONSTRAINT IF EXISTS skill_subscription_skill_fk;