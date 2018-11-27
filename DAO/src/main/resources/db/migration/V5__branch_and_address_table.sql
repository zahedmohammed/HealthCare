
-- Table: branch

CREATE TABLE branch
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  access character varying(255),
  atm_at_branch character varying(255) NOT NULL,
  branch_name character varying(255) NOT NULL,
  branch_mediated_service_name character varying(255),
  branch_type character varying(255),
  customer_segment character varying(255),
  fax_number character varying(255),


   CONSTRAINT branch_pkey PRIMARY KEY (id)
 );

-- Table: address

CREATE TABLE address
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,

  lattitude character varying(255),
  longitude character varying(255),
  door_no character varying(255),
  street_name character varying(255),
  locality character varying(255),
  city character varying(255),

  CONSTRAINT address_pkey PRIMARY KEY (id)
 );











