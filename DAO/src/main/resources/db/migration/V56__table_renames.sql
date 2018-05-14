--
-- Refactor table names
--


ALTER TABLE if EXISTS cloud_account RENAME TO account;
ALTER TABLE if EXISTS skill_subscription RENAME TO issue_tracker;
ALTER TABLE if EXISTS notification_account RENAME TO notification;



ALTER TABLE project RENAME COLUMN cloud_account_id TO account_id;
ALTER TABLE cluster RENAME COLUMN cloud_account_id TO account_id;
ALTER TABLE issue_tracker RENAME COLUMN cloud_account_id TO account_id;
ALTER TABLE notification RENAME COLUMN cloud_account_id TO account_id;