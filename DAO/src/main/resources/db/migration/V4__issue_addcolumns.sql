--
-- Name: add columns to Table Issue
-- author Atifa Fahmeen
--

ALTER TABLE ONLY issue
    ADD COLUMN issue_name character varying(255);

ALTER TABLE ONLY issue
    ADD COLUMN issue_status character varying(255);

ALTER TABLE ONLY issue
    ADD COLUMN issue_type character varying(255);

ALTER TABLE ONLY issue
    ADD COLUMN assigned_to character varying(255);