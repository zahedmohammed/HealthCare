--
-- Name: project add fk
--


DELETE FROM data_set;

DELETE FROM job_tags;
DELETE FROM job_notifications;
DELETE FROM job;

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

DELETE FROM test_cases;

DELETE FROM run_attributes;
DELETE FROM run_stats;
DELETE FROM run;

DELETE FROM project_licenses;
DELETE FROM project_users;
DELETE FROM project_imports;
DELETE FROM project_imports_map;

DELETE FROM project;


ALTER TABLE ONLY notification
  ADD CONSTRAINT notification_pkey PRIMARY KEY (id);

ALTER TABLE ONLY account
  ADD CONSTRAINT account_pkey PRIMARY KEY (id);

ALTER TABLE ONLY project
  ADD CONSTRAINT project_account_fk FOREIGN KEY (account_id) REFERENCES account(id);