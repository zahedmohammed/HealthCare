--
-- Table Name: Notification_Account
-- Author: Mohammed Luqman Shareef

CREATE TABLE notification_account
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  type character varying(255),
  name character varying(255),
  region character varying(255),
  visibility character varying(255),
  org_id character varying(255),
  secret_key character varying(255),
  access_key character varying(255),
  token character varying(255),
  channel character varying(255)
);
