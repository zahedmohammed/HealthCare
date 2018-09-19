-- Author: Syed Jaleel
ALTER TABLE ONLY test_case_response_issue_tracker ADD COLUMN project_id character varying(255);
ALTER TABLE ONLY test_case_response_issue_tracker ADD COLUMN job_id character varying(255);
ALTER TABLE ONLY test_case_response_issue_tracker ADD COLUMN test_suite_name character varying(255);
ALTER TABLE ONLY test_case_response_issue_tracker ADD COLUMN test_case_number character varying(255);

