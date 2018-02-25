--
-- Name: test_cases; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE test_cases (
    test_suite_id character varying(255) NOT NULL,
    id integer NOT NULL,
    body text NOT NULL,
    inactive boolean
);

--
-- Name: test_cases test_cases_test_suite_id; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY test_cases
    ADD CONSTRAINT test_cases_test_suite_id FOREIGN KEY (test_suite_id) REFERENCES test_suite(id);
