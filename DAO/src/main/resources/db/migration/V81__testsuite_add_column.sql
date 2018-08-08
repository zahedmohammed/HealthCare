--
-- description columns to git
--


ALTER TABLE test_suite DROP COLUMN if EXISTS parent;
ALTER TABLE ONLY test_suite ADD COLUMN parent character varying(255);



