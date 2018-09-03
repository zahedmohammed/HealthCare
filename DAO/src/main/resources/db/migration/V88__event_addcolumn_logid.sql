-- Author: Mohammed  Shoukath Ali

ALTER TABLE event DROP COLUMN IF EXISTS log_id;

ALTER TABLE  event ADD COLUMN log_id character varying(255);