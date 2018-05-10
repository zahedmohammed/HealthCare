--
-- Moved cluster columns to cluster.
--


ALTER TABLE cluster DROP COLUMN if EXISTS manual;
ALTER TABLE ONLY cluster ADD COLUMN manual boolean;

UPDATE  cluster SET manual='false';

ALTER TABLE cluster DROP COLUMN if EXISTS manual_script;
ALTER TABLE ONLY cluster ADD COLUMN manual_script character varying(255);



