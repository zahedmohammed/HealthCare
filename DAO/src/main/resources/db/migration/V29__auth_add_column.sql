--
-- Name: environment_auths add column OAuth 2.0
--


ALTER TABLE ONLY environment_auths
ADD COLUMN id character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN access_token_uri character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN authorization_scheme character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN client_authentication_scheme character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN token_name character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN scope character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN grant_type character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN pre_established_redirect_uri character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN user_authorization_uri character varying(255);

ALTER TABLE ONLY environment_auths
ADD COLUMN use_current_uri boolean;
