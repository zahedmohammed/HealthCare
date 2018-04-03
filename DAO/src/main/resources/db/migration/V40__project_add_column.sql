--
-- Name: project add column gen_policy open_api_spec_uri
--


ALTER TABLE ONLY project
ADD COLUMN gen_policy character varying(255);

ALTER TABLE ONLY project
ADD COLUMN open_api_spec_uri character varying(255);

UPDATE project set gen_policy = 'None';
