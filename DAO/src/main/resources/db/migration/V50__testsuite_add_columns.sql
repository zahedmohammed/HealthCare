--
-- Name: add columns to Test Suite
-- Author: Mohammed Luqman Shareef
--

alter table test_suite add column category  character varying(255);

alter table test_suite add column severity character varying(255);


