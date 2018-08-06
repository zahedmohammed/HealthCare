--
-- Add Event Table
-- author Mohammed Shoukath ali
--


-- Table: event

-- DROP TABLE public.event;

CREATE TABLE event
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  entity_id character varying(255),
  entity_type character varying(255),
  event_type character varying(255),
  link character varying(255),
  name character varying(255),
  status character varying(255),
  user_id character varying(255),
  org_id character varying(255),
  CONSTRAINT event_pkey PRIMARY KEY (id),
  CONSTRAINT fk9pxuii6b8aaxt6rlk7v1hqxnm FOREIGN KEY (org_id)
      REFERENCES org (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


