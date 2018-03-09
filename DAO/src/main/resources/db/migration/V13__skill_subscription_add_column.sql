--
-- Name: skill_subscription
--

ALTER TABLE ONLY skill_subscription
    ADD COLUMN prop1 character varying(255);

ALTER TABLE ONLY skill_subscription
    ADD COLUMN prop2 character varying(255);

ALTER TABLE ONLY skill_subscription
    ADD COLUMN prop3 character varying(255);

ALTER TABLE ONLY skill_subscription
    ADD COLUMN prop4 character varying(255);

ALTER TABLE ONLY skill_subscription
    ADD COLUMN prop5 text;

ALTER TABLE ONLY skill_subscription
    ADD COLUMN visibility character varying(255);

ALTER TABLE ONLY skill_subscription
    DROP COLUMN skill_type;
--
-- Name: skill
--


ALTER TABLE ONLY skill
    ALTER COLUMN access_key DROP NOT NULL;

ALTER TABLE ONLY skill
    ALTER COLUMN secret_key DROP NOT NULL;

ALTER TABLE ONLY skill
    ALTER COLUMN host DROP NOT NULL;