--
-- Name: test_case_response add column
-- author Mohammed Shoukath Ali
--

ALTER TABLE ONLY test_case_response
    ADD COLUMN job_id character varying(255);


ALTER TABLE ONLY test_case_response
ADD COLUMN status_code character varying(255);