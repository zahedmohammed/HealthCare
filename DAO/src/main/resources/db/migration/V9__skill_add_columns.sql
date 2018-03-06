--
-- Name: Skill
--

ALTER TABLE ONLY skill
    ADD COLUMN access_key character varying(255) NOT NULL;

ALTER TABLE ONLY skill
    ADD COLUMN secret_key character varying(255) NOT NULL;

ALTER TABLE ONLY skill
    ADD COLUMN host character varying(255) NOT NULL;

