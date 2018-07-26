-- Author: Mohammed Shoukath Ali

alter table auto_code_generator drop column if exists severity;
alter table auto_code_generator add column severity character varying(255);

alter table auto_code_generator drop column if exists generator_inactive;
alter table auto_code_generator drop column if exists description;



