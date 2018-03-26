--
-- Name: environment_auths add column OAuth 2.0
--


ALTER TABLE ONLY environment_auths
ADD COLUMN client_id character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN client_secret character varying(255);
