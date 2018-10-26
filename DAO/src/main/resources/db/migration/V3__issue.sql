-- author Mohammed Shoukath Ali

-- Table: public.issue

CREATE TABLE public.issue
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  assertions text,
  description text,
  endpoint character varying(255),
  failed_assertions text,
  method character varying(255),
  request_body text,
  response_body text,
  response_headers character varying(255),
  result character varying(255),
  status_code character varying(255),
  project_id character varying(255),
  CONSTRAINT issue_pkey PRIMARY KEY (id),
  CONSTRAINT fkcombytcpeogaqi2012phvvvhy FOREIGN KEY (project_id)
      REFERENCES public.project (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);


  -- Table: public.issue_headers
  CREATE TABLE issue_headers
  (
    issue_id character varying(255) NOT NULL,
    headers character varying(255),
    CONSTRAINT fkrj8af4umhejtafp0dfyk39o6w FOREIGN KEY (issue_id)
        REFERENCES issue (id) MATCH SIMPLE
        ON UPDATE NO ACTION ON DELETE NO ACTION
  );







