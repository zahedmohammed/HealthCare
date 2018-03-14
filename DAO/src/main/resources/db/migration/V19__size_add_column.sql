--
-- Name: test_suite_response add column total_bytes
--       run add column total_bytes
-- author Intesar Shannan Mohammed
--


ALTER TABLE ONLY test_suite_response
ADD COLUMN total_bytes bigint;

ALTER TABLE ONLY run
ADD COLUMN total_bytes bigint;