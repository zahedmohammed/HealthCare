-- Author: Mohammed Luqman Shareef

update job_issue_tracker set account  = (select id from account where account_type = 'FX_Issues' limit 1)
where name like '%Dev-IssueTracker';

