-- Delete all entries from qrtz table.

delete from qrtz_simple_triggers;
delete from qrtz_triggers;
delete from qrtz_job_details;
delete from qrtz_fired_triggers;
delete from qrtz_locks;
delete from qrtz_scheduler_state;