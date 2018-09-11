-- Author: Mohammed Luqman Shareef

alter table test_case_response_issue_tracker add column run_id character varying(255);

alter table run add column issues_logged bigint;

alter table run add column issues_closed bigint;

alter table run add column issues_reopen bigint;

