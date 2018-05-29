
-- Table: access_key

-- DROP TABLE access_key;

DROP TABLE if EXISTS access_key;
CREATE TABLE access_key
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  access_key character varying(255),
  expiration timestamp without time zone NOT NULL,
  secret_key character varying(255),
  users_id character varying(255),
  CONSTRAINT access_key_pkey PRIMARY KEY (id),
  CONSTRAINT fkijsp2xhvr5y8tsafll9atx773 FOREIGN KEY (users_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

