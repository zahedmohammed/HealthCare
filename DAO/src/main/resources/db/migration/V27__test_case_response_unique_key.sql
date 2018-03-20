
--
-- Name: test_case_response; Type: Unique key CONSTRAINT; Schema: public; Owner: -
--
alter table test_suite add constraint test_suite_unique_key unique  (project_id, name);

