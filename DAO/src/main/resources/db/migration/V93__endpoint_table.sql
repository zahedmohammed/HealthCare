-- Author: Mohammed Luqman Shareef
drop table if exists project_api_endpoints;

drop table if exists endpoint;

CREATE TABLE endpoint
(
  id character varying(255) NOT NULL,
  project_id character varying(255),
    endpoint character varying(255),
  method character varying(255),
  CONSTRAINT project_ep_pkey PRIMARY KEY (id)
);

