-- Author: Mohammed Luqman Shareef

ALTER TABLE JOB_ISSUE_TRACKER DROP COLUMN IF EXISTS ACCOUNT_TYPE;

ALTER TABLE JOB_ISSUE_TRACKER ADD COLUMN ACCOUNT_TYPE CHARACTER VARYING(255);