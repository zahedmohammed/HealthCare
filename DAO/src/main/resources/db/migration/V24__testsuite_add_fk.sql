--
-- Name: test_suite add fk
--


ALTER TABLE ONLY test_suite
  ADD CONSTRAINT test_suite_project_fk FOREIGN KEY (project_id) REFERENCES project(id);