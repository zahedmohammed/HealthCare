
--
-- Name: test_case_response; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_case_response
    DROP COLUMN IF EXISTS skill_type;

ALTER TABLE ONLY test_case_response
    DROP COLUMN IF EXISTS testcase;

ALTER TABLE ONLY test_case_response
    ALTER COLUMN test_case DROP NOT NULL;

ALTER TABLE ONLY test_case_response
    DROP COLUMN IF EXISTS endpointeval;

ALTER TABLE ONLY test_case_response
    ALTER COLUMN endpoint_eval TYPE text;

ALTER TABLE ONLY test_case_response
    DROP COLUMN IF EXISTS requesteval;

ALTER TABLE ONLY test_case_response
    ALTER COLUMN request_eval TYPE text;

