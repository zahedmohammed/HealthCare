-- Author: Mohammed Luqman Shareef
-- Author: Mohammed Shoukath Ali


-- Table: public.auto_code_config

-- DROP TABLE auto_code_config;

CREATE TABLE auto_code_config
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  gen_policy character varying(255),
  open_api_spec_uri character varying(255),
  project_id character varying(255),
  CONSTRAINT auto_code_config_pkey PRIMARY KEY (id),
  CONSTRAINT fki15sj32rdiw0fmgcjnac4fo0o FOREIGN KEY (project_id)
      REFERENCES project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: auto_code_generator

-- DROP TABLE auto_code_generator;

CREATE TABLE auto_code_generator
(
  id character varying(255) NOT NULL,
  description character varying(255),
  inactive boolean NOT NULL,
  name character varying(255),
  version character varying(255),
  generator_inactive boolean,
  severity integer,
  type character varying(255),
  CONSTRAINT auto_code_generator_pkey PRIMARY KEY (id)
);

-- Table: auto_code_config_generators

-- DROP TABLE auto_code_config_generators;

CREATE TABLE auto_code_config_generators
(
  auto_code_config_id character varying(255) NOT NULL,
  generators_id character varying(255) NOT NULL,
  CONSTRAINT fk682tbim5ar2ehvctb7ery2qj3 FOREIGN KEY (auto_code_config_id)
      REFERENCES auto_code_config (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fks135ap7c8k6ka8jwl5gnyalko FOREIGN KEY (generators_id)
      REFERENCES auto_code_generator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_b7ve2gj8a0y8w5ecl5rokgvsf UNIQUE (generators_id)
);


-- Table: auto_code_generator_assertions

-- DROP TABLE auto_code_generator_assertions;

CREATE TABLE auto_code_generator_assertions
(
  auto_code_generator_id character varying(255) NOT NULL,
  assertions character varying(255),
  CONSTRAINT fk6vscqpqwhbc64rm9ex4pooe3v FOREIGN KEY (auto_code_generator_id)
      REFERENCES auto_code_generator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: auto_code_generator_matches

-- DROP TABLE auto_code_generator_matches;

CREATE TABLE auto_code_generator_matches
(
  id character varying(255) NOT NULL,
  body_properties character varying(255),
  deny_roles character varying(255),
  methods character varying(255),
  name character varying(255),
  path_patterns character varying(255),
  query_params character varying(255),
  resource_samples character varying(255),
  value character varying(255),
  CONSTRAINT auto_code_generator_matches_pkey PRIMARY KEY (id)
);

-- Table: auto_code_generator_auto_code_generator_matches

-- DROP TABLE auto_code_generator_auto_code_generator_matches;

CREATE TABLE auto_code_generator_auto_code_generator_matches
(
  auto_code_generator_id character varying(255) NOT NULL,
  auto_code_generator_matches_id character varying(255) NOT NULL,
  CONSTRAINT fk6pkex9ec5c03dg6q2lm4wj5ju FOREIGN KEY (auto_code_generator_matches_id)
      REFERENCES auto_code_generator_matches (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkmwbki4ke0sferxb2mu3jmxx0l FOREIGN KEY (auto_code_generator_id)
      REFERENCES auto_code_generator (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uk_atv7dh4gvitfhrkyfon8fvfhr UNIQUE (auto_code_generator_matches_id)
);



