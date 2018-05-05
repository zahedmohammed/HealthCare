--
-- Name: add columns to Test Suite Response
-- Author: Mohammed Luqman Shareef
--

alter table test_suite_response add column category  character varying(255);

alter table test_suite_response add column severity character varying(255);