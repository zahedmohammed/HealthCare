-- author Mohammed Shoukath Ali

-- Table: primary account

CREATE TABLE primary_account
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
   user_id character varying(255),
   account_number character varying(255) NOT NULL,
  account_balance bigint NOT NULL,
  CONSTRAINT priaccount_pkey PRIMARY KEY (id),
  CONSTRAINT fkcombytcpeogaqi2012phvvvhy FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: primary transaction

CREATE TABLE primary_transaction
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  user_id character varying(255),
  description character varying(255) NOT NULL,
  type character varying(255) NOT NULL,
  status character varying(255) NOT NULL,
  amount bigint NOT NULL,
  available_balance bigint NOT NULL,

  CONSTRAINT pritransactions_pkey PRIMARY KEY (id),
  CONSTRAINT fkcombytcpeogaqi2012phvvvhy FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);











