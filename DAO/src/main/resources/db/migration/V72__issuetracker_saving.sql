
insert into system_setting (id, created_date, created_by, inactive, key, label, value) values ( '4028b881620688c001620689a3210012', current_timestamp, 'anonymousUser', false, 'HOURLY_BUG_VALIDATION', ' Time to validate bug per hour (in hours) ', '5');
insert into system_setting (id, created_date, created_by, inactive, key, label, value) values ( '4028b881620688c001620689a3210413', current_timestamp, 'anonymousUser', false, 'HOURLY_BUG_RATE', 'Hourly bug rate', '50');

alter table test_case_response add column run_id character varying(255);