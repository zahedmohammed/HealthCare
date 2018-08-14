--
-- description columns to git
--


ALTER TABLE event DROP COLUMN if EXISTS task_id;
ALTER TABLE ONLY event ADD COLUMN task_id character varying(255);



