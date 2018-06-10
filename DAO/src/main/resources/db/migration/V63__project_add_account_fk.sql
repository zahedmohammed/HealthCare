--
-- Name: project add fk
--


DELETE FROM data_set;

DELETE FROM test_cases;

DELETE FROM test_suite_tags;
DELETE FROM test_suite_assertions;
DELETE FROM test_suite_headers;
DELETE FROM test_suite_authors;
DELETE FROM test_suite_cleanup;
DELETE FROM test_suite_init;
DELETE FROM test_suite_tags;
DELETE FROM test_suite_assertions;
DELETE FROM test_suite_headers;
DELETE FROM test_suite_authors;
DELETE FROM test_suite;

DELETE FROM run_attributes;
DELETE FROM run_stats;
DELETE FROM run;

DELETE FROM project_licenses;
DELETE FROM project_users;

DELETE FROM project_imports_map;
DELETE FROM project_imports;

DELETE FROM job_tags;
DELETE FROM job_notifications;
DELETE FROM job;

DELETE FROM environment_auths;
DELETE FROM environment;

DELETE FROM project;


ALTER TABLE notification DROP CONSTRAINT IF EXISTS notification_pkey;
ALTER TABLE ONLY notification
  ADD CONSTRAINT notification_pkey PRIMARY KEY (id);

ALTER TABLE account DROP CONSTRAINT IF EXISTS account_pkey CASCADE;
ALTER TABLE ONLY account
  ADD CONSTRAINT account_pkey PRIMARY KEY (id);

ALTER TABLE environment DROP CONSTRAINT IF EXISTS environment_project_fk;
ALTER TABLE ONLY environment
  ADD CONSTRAINT environment_project_fk FOREIGN KEY (project_id) REFERENCES project(id);

ALTER TABLE project DROP CONSTRAINT IF EXISTS project_account_fk;
ALTER TABLE ONLY project
  ADD CONSTRAINT project_account_fk FOREIGN KEY (account_id) REFERENCES account(id);