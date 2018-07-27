-- Author: Mohammed Shoukath Ali

alter table job drop  column if exists environment;
alter table job add column job_environment_id character varying(255);



