-- Author: Mohammed Luqman Shareef
-- Table: public.auto_suggestion

-- DROP TABLE public.auto_suggestion;

CREATE TABLE public.auto_suggestion
(
  id character varying(255) NOT NULL,
  created_by character varying(255),
  created_date timestamp without time zone NOT NULL,
  inactive boolean NOT NULL,
  modified_by character varying(255),
  modified_date timestamp without time zone,
  category character varying(255),
  end_point character varying(255),
  estimates character varying(255),
  issue_desc character varying(255),
  method character varying(255),
  project_id character varying(255),
  region character varying(255),
  resp_status_code character varying(255),
  severity character varying(255),
  status character varying(255),
  suggestion character varying(255),
  suggestion_id character varying(255),
  test_case_number character varying(255),
  test_suite_id character varying(255),
  test_suite_name character varying(255),
  CONSTRAINT auto_suggestion_pkey PRIMARY KEY (id)
);

