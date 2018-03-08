--
-- Name: test_case_response
--

ALTER TABLE ONLY test_case_response
    ADD COLUMN issue_id character varying(255);

--
-- Name: test_case_response
--

ALTER TABLE ONLY test_case_response
ADD COLUMN result character varying(255);

ALTER TABLE ONLY test_case_response
ADD COLUMN endpoint_eval character varying(255);


ALTER TABLE ONLY test_case_response
ADD COLUMN it_key character varying(255);

ALTER TABLE ONLY test_case_response
ADD COLUMN headers character varying(255);

ALTER TABLE ONLY test_case_response
ADD COLUMN time character varying(255);

ALTER TABLE ONLY test_case_response
ADD COLUMN size bigint;

ALTER TABLE ONLY test_case_response
ADD COLUMN request_eval character varying(255);
