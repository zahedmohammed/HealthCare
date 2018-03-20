
--
-- Table Name: Cloud_Account
--
CREATE TABLE cloud_account (
    id character varying(255) NOT NULL,
    created_by character varying(255),
    created_date timestamp without time zone NOT NULL,
    inactive boolean NOT NULL,
    modified_by character varying(255),
    modified_date timestamp without time zone,
    cloud_type character varying(255),
    name character varying(255),
    region character varying(255),
    visibility character varying(255),
    org_id character varying(255),
    secret_key character varying(255),
    access_key character varying(255)

);