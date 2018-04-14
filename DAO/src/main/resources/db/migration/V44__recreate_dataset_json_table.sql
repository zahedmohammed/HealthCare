
-- author Mohammed Shoukath Ali



-- Table: data_set

DROP TABLE  IF EXISTS data_set;
DROP TABLE  IF EXISTS data_record;

CREATE TABLE data_set
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  name character varying(255),
  org_id character varying(255),
  project_id character varying(255),
  CONSTRAINT data_set_pkey PRIMARY KEY (id),
  CONSTRAINT fk6dm1leyf64g52hj50328lo5qt FOREIGN KEY (project_id)
      REFERENCES project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkrb5k700mbpkqfbvsa4sp6gi4q FOREIGN KEY (org_id)
      REFERENCES org (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


-- Table: data_record

-- DROP TABLE data_record;

CREATE TABLE data_record
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  data_set character varying(255),
  record character varying(255),
  org_id character varying(255),
  project_id character varying(255),
  CONSTRAINT data_record_pkey PRIMARY KEY (id),
  CONSTRAINT fk2y4jlgy7arkjpuwqk1uyc720a FOREIGN KEY (project_id)
      REFERENCES project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fkl609hsrkgg5gogjjxd4o7yikk FOREIGN KEY (org_id)
      REFERENCES org (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


