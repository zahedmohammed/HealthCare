-- Author: Mohammed Luqman Shareef


-- DROP TABLE auto_code_config;

CREATE TABLE auto_code_config
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  project_id character varying(255),
  gen_policy character varying(255),
  open_api_spec_uri character varying(255),
  CONSTRAINT auto_code_pkey PRIMARY KEY (id),
  CONSTRAINT auto_code_proj_fkey FOREIGN KEY (project_id)
      REFERENCES project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- DROP TABLE auto_code_generator;

CREATE TABLE auto_code_generator
(
  id character varying(255) NOT NULL,
  auto_code_config_id character varying(255),
  type character varying(255),
  assertions text,
  severity character varying(255),
  generator_inactive boolean NOT NULL,
  database character varying(255),
  CONSTRAINT auto_code_gen_pkey PRIMARY KEY (id),
  CONSTRAINT auto_code_gen_fkey FOREIGN KEY (auto_code_config_id )
      REFERENCES auto_code_config (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- DROP TABLE auto_code_generator_matches;

CREATE TABLE auto_code_generator_matches
(
  id character varying(255) NOT NULL,
  auto_code_generator_id character varying(255),
  name character varying(255),
  value character varying(255),
  methods character varying(255),
  deny_roles text,
  path_patterns text,
  resource_samples text,
  query_params text,
  body_properties text,
  database character varying(255),
  CONSTRAINT auto_code_gen_matches_pkey PRIMARY KEY (id),
  CONSTRAINT auto_code_gen_matches_fkey FOREIGN KEY (auto_code_generator_id )
      REFERENCES auto_code_generator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- DROP TABLE resource_samples;

CREATE TABLE resource_samples
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  project_id character varying(255),
  resource character varying(255),
  sample character varying(255),
  CONSTRAINT resource_samples_pkey PRIMARY KEY (id),
  CONSTRAINT resource_samples_proj_fkey FOREIGN KEY (project_id)
      REFERENCES project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);



