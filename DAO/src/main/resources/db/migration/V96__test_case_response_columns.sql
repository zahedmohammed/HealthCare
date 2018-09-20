-- Author: Luqman Shareef

alter table test_case_response drop column if exists project_id ;

alter table test_case_response add column project_id character varying(255);

