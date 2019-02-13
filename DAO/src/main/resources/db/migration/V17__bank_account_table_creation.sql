-- author Mohammed Zahed

-- Table: secondary account

CREATE TABLE bank_Account
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
   user_id character varying(255),
   account_number character varying(255) NOT NULL,
--   account_type character varying(255) NOT NULL,
     account_balance bigint NOT NULL,
  CONSTRAINT secondary_account_pkey PRIMARY KEY (id),
  CONSTRAINT fkcombytcpeogaqi2012phvvvhy FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);











