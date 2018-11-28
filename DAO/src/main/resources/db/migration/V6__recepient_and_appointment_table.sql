-- author: Atifa Fahmeen

-- Table: recepient

CREATE TABLE recepient
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  user_id character varying(255),
  name character varying(255) NOT NULL,
  email character varying(255),
  phone character varying(255),
  account_number character varying(255) NOT NULL,
  description character varying(255),
  CONSTRAINT recepient_pkey PRIMARY KEY (id),
  CONSTRAINT fkcombytcpeogaqi2012phvvvhy FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-- Table: appointment

CREATE TABLE appointment
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  user_id character varying(255),
  date timestamp without time zone NOT NULL,
  location character varying(255),
  confirmed boolean NOT NULL,
  description character varying(255),
  CONSTRAINT appointment_pkey PRIMARY KEY (id),
  CONSTRAINT fkcombytcpeogaqi2012phvvvhy FOREIGN KEY (user_id)
      REFERENCES users (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
