
alter table cluster drop column if exists node_id;
alter table cluster add column node_id character varying(255);

alter table subscription_task drop column if exists clusterId;
alter table subscription_task add column cluster_id character varying(255);

