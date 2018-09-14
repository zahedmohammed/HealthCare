-- Author: Mohammed Luqman Shareef

alter table project_api_endpoints drop column if exists  api_endpoints;

alter table project_api_endpoints add column endpoint character varying(255);
alter table project_api_endpoints add column method character varying(255);